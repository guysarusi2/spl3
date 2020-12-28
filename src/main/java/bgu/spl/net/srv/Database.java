package bgu.spl.net.srv;

import bgu.spl.net.srv.DatabaseObjects.Course;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

    ConcurrentHashMap<Integer,Course> courses = new ConcurrentHashMap<>();

    //to prevent user from creating new Database
    private Database() {
        // TODO: implement
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
    public boolean initialize(String coursesFilePath) throws IOException {
        //TODO: first iteration without kdams. then with.

        File file = new File(coursesFilePath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
       /* while ((line = br.readLine()) != null)
            createCourse(line);*/
        line = br.readLine();
        createCourse(line);

        return false;
    }

    private void createCourse(String line){
        int curr = 0;
        int to = line.indexOf('|');
        int courseNumber = Integer.parseInt(line.substring(curr,to));

        curr = to +1;
        to = line.indexOf('|',curr);
        String courseName = line.substring(curr,to);

        curr = to +1;
        to = line.indexOf('|',curr);
        String kdamcourses = line.substring(curr,to);
        stringToCourseArray(kdamcourses);

        curr = to+1;
        int numOfMaxStudents = Integer.parseInt(line.substring(curr));

    }

    private Course[] stringToCourseArray(String kdamCourses){
        Course[] courses;
        int kdamCount = stringToNumberOfCourses(kdamCourses);
        courses=new Course[kdamCount];


        return new Course[kdamCount];
    }

    private int stringToNumberOfCourses(String kdamCourses){

        if (kdamCourses.length()==2)
            return 0;
        else if(!kdamCourses.contains(","))
            return 1;

        int counter=1;

        for(int i=0;i<kdamCourses.length();i++)
            counter = (kdamCourses.charAt(i)==',') ? counter +1 : counter;

        return counter;
    }
}
