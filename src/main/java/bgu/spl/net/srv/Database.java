package bgu.spl.net.srv;

import bgu.spl.net.srv.DatabaseObjects.Course;
import bgu.spl.net.srv.DatabaseObjects.Student;
import bgu.spl.net.srv.DatabaseObjects.User;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

    private final ConcurrentHashMap<Short, Course> allCourses;
    private final ConcurrentHashMap<String, User> allUsers;
    private final ConcurrentSkipListSet<User> connectedUsers;
    private ReadWriteLock usersRWLock;

    //to prevent user from creating new Database
    private Database() {
        allCourses = new ConcurrentHashMap<>();
        allUsers = new ConcurrentHashMap<>();
        usersRWLock = new ReentrantReadWriteLock();
        connectedUsers = new ConcurrentSkipListSet<>();
    }

    private static class SingletonHolder {
        private static Database instance = new Database();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    public void initialize(String coursesFilePath) throws IOException {
        // TODO try with resources (should close file)
        File file = new File(coursesFilePath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        for (int i = 1; (line = br.readLine()) != null; i++) {
            Course temp = createCourse(line);
            temp.setSerialNumber(i);
            allCourses.put(temp.getCourseNumber(), temp);
        }

        br = new BufferedReader(new FileReader(file));
        while ((line = br.readLine()) != null)
            updateCourse(line);
    }

    public void connectUser(String toConnect){
        connectedUsers.add(getUser(toConnect));
    }
    public void disconnectUser(String toDisconnect){
        connectedUsers.remove(getUser(toDisconnect));
    }
    public boolean isAlreadyConnected(String user){
        return connectedUsers.contains(getUser(user));
    }

    private Course createCourse(String line) {
        int curr = 0;
        int to = line.indexOf('|');
        short courseNumber = Short.parseShort(line.substring(curr, to));

        curr = to + 1;
        to = line.indexOf('|', curr);
        String courseName = line.substring(curr, to);

        return new Course(courseNumber, courseName);
    }

    private void updateCourse(String line) {
        int start = line.indexOf('[');
        int end = line.indexOf(']');

        String kdamcourses = line.substring(start, end + 1);
        Course[] c = createCourseArray(kdamcourses);
            Arrays.sort(c);

        short courseNumber = Short.parseShort(line.substring(0, line.indexOf('|')));
        int numOfMaxStudents = Integer.parseInt(line.substring(line.indexOf(']') + 2));
        allCourses.get(courseNumber).update(c, numOfMaxStudents);
    }

    private Course[] createCourseArray(String kdamCourses) {
        if (kdamCourses.length() == 2)
            return new Course[0];

        int kdamCount = stringToNumberOfCourses(kdamCourses);
        Course[] output = new Course[kdamCount];

        int count = 0;
        for (int i = 1; i < kdamCourses.length() - 1; i++) {
            String acc = "";
            while (kdamCourses.charAt(i) != ',' && kdamCourses.charAt(i) != ']') {
                acc += kdamCourses.charAt(i);
                i++;
            }
            output[count] = allCourses.get(Short.parseShort(acc));
            count++;
        }

        return output;
    }

    private int stringToNumberOfCourses(String kdamCourses) {
        if (!kdamCourses.contains(","))
            return 1;

        int counter = 1;

        for (int i = 0; i < kdamCourses.length(); i++)
            counter = (kdamCourses.charAt(i) == ',') ? counter + 1 : counter;

        return counter;
    }


    public boolean isUserExist(String username) {
        usersRWLock.readLock().lock();
        boolean output = allUsers.containsKey(username);
        usersRWLock.readLock().unlock();

        return output;
    }

    public boolean verifyPassword(String username, String password) {
        usersRWLock.readLock().lock();
        String curr = allUsers.get(username).getPassword();
        usersRWLock.readLock().unlock();

        boolean output = curr.equals(password);
        return output;
    }

    public User getUser(String username) {
        usersRWLock.readLock().lock();
        User output = allUsers.get(username);
        usersRWLock.readLock().unlock();

        return output;
    }

    public String isStudentRegisteredTo(Student toCheck, short courseNumber) {
        Course course = allCourses.get(courseNumber);
        if (course == null)
            return null;

        // ************* VARIFYING MATCHES *********************  TODO REMOVE
        boolean userWasRegisteredInStudent = toCheck.getRegisteredCourses().contains(course);
        boolean userWasRegisteredInCourse = course.getStudentsList().contains(toCheck);
        if (userWasRegisteredInStudent != userWasRegisteredInCourse)
            throw new IllegalArgumentException("בעיה אחושרמוטה 2s");

        return (userWasRegisteredInStudent) ? "REGISTERED" : "NOT REGISTERED";

    }

    public User addUser(User toAdd) {            //todo user or void?
        usersRWLock.writeLock().lock();
        toAdd = allUsers.put(toAdd.getUsername(), toAdd);
        usersRWLock.writeLock().unlock();

        return toAdd;
    }

    public String getCourseStatus(short courseNumber) {
        Course c = allCourses.get(courseNumber);
        if (c == null)
            return null;
        String list = c.getStudentsListString();

        return String.format("Course: (%d) %s\n"
                        + "Seats Available: %d / %d\n"
                        + "Students Registered: %s",
                c.getCourseNumber(), c.getCourseName(), c.getAvailableSeats(), c.getNumOfMaxStudents(), list);
    }

    public String getStudentStatus(String username) {
        usersRWLock.readLock().lock();
        User user = allUsers.get(username);
        usersRWLock.readLock().unlock();

        return user.getStatus();
    }

    public User removeUser(User toRemove) {
        usersRWLock.writeLock().lock();
        User output = allUsers.remove(toRemove);
        usersRWLock.writeLock().unlock();

        return output;
    }

    public boolean registerStudent(Student toAdd, short courseNumber) {
        Course course = allCourses.get(courseNumber);
        if (course == null || course.isFullyBooked() || !course.verifyKdams(toAdd)|| course.getStudentsList().contains(toAdd))
            return false;


        return course.registerStudent(toAdd) && toAdd.getRegisteredCourses().add(course);
    }

    public String kdamCheck(short courseNumber) {
        Course course = allCourses.get(courseNumber);
        if (course == null)
            return null;

        return course.getKdamsString();
    }


    public boolean unregisterStudent(Student toRemove, short courseNumber) {
        Course course = allCourses.get(courseNumber);
        if (course == null)
            return false;

        // ************* VARIFYING MATCHES *********************  TODO REMOVE
        boolean userWasRegisteredInStudent = toRemove.getRegisteredCourses().remove(course);
        boolean userWasRegisteredInCourse = course.unregisterStudent(toRemove);
        if (userWasRegisteredInStudent != userWasRegisteredInCourse)
            throw new IllegalArgumentException("בעיה אחושרמוטה");

        return userWasRegisteredInStudent || userWasRegisteredInCourse;
    }


}
