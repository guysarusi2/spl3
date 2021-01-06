package bgu.spl.net.srv.DatabaseObjects;

import bgu.spl.net.srv.Database;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Student implements User{
    private String username;
    private String password;

    private LinkedList<Course> registeredCourses;
    private ReadWriteLock registeredCoursesRWLock;

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
        registeredCourses = new LinkedList<>();
        registeredCoursesRWLock = new ReentrantReadWriteLock();
    }

    @Override
    public boolean courseRegister(short courseNumber){
        registeredCoursesRWLock.writeLock().lock();
        boolean out= Database.getInstance().registerStudent(this, courseNumber);
        registeredCoursesRWLock.writeLock().unlock();
        return out;
    }

    @Override
    public String kdamCheck(short courseNumber){return Database.getInstance().kdamCheck(courseNumber);}

    public boolean courseUnregister(short courseNumber){
        registeredCoursesRWLock.writeLock().lock();
        boolean out = Database.getInstance().unregisterStudent(this, courseNumber);
        registeredCoursesRWLock.writeLock().unlock();
        return out;
    }

    public boolean removeCourse(Course course){
        registeredCoursesRWLock.writeLock().lock();
        boolean out = registeredCourses.remove(course);
        registeredCoursesRWLock.writeLock().unlock();
        return out;
    }
    public boolean addCourse(Course course){
        registeredCoursesRWLock.writeLock().lock();
        boolean out = registeredCourses.add(course);
        registeredCoursesRWLock.writeLock().unlock();
        return out;
    }

    @Override
    public String isRegisteredTo(short courseNumber){
        Course course = Database.getInstance().getCourse(courseNumber);
        if(course==null)
            return null;
        registeredCoursesRWLock.readLock().lock();
        String out= (registeredCourses.contains(course)) ? "REGISTERED" : "NOT REGISTERED";
        registeredCoursesRWLock.readLock().unlock();
        return out;
    }

    @Override
    public String getCoursesString(){
        String output = "[";
        registeredCoursesRWLock.writeLock().lock();
        registeredCourses.sort((Course c1, Course c2)->{
            return c1.getSerialNumber()- c2.getSerialNumber(); });
        registeredCoursesRWLock.writeLock().unlock();

        registeredCoursesRWLock.readLock().lock();
        for (Course c:registeredCourses){
            output+=c.getCourseNumber();
            if(c!=registeredCourses.getLast())
                output+=",";
        }
        registeredCoursesRWLock.readLock().unlock();

        return output+"]";
    }

    @Override
    public String getStatus(){
        return String.format("Student: %s\nCourses: %s", username,getCoursesString()) ;
    }
    public String getPassword(){return password;}
    public String getUsername(){return username;}

}
