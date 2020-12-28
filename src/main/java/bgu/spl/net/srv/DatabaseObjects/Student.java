package bgu.spl.net.srv.DatabaseObjects;

import java.util.LinkedList;
import java.util.List;

public class Student extends User{

    private LinkedList<Course> registeredCourses;


    public Student(String username, String password) {
        super(username, password);
        registeredCourses = new LinkedList<>();
    }

    public boolean register(Course course){
        for(Course neededKdam: course.getKdamCoursesList())
            if(!registeredCourses.contains(neededKdam))
                return false;

        registeredCourses.add(course);
        return true;
    }

    public boolean unregister(Course course){
        return registeredCourses.remove(course);
    }

    public List<Course> getRegisteredCourses(){return registeredCourses;}
}
