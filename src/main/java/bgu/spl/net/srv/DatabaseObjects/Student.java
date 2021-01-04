package bgu.spl.net.srv.DatabaseObjects;

import bgu.spl.net.srv.Database;

import java.util.LinkedList;
import java.util.List;

public class Student implements User{
    private String username;
    private String password;

    private LinkedList<Course> registeredCourses;

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
        registeredCourses = new LinkedList<>();
    }

    @Override
    public boolean courseRegister(short courseNumber){
        return Database.getInstance().registerStudent(this, courseNumber);
    }

    //public boolean isRegisteredTo(Course course){return registeredCourses.contains(course);}

    @Override
    public String kdamCheck(short courseNumber){return Database.getInstance().kdamCheck(courseNumber);}


    public boolean courseUnregister(short courseNumber){
        return Database.getInstance().unregisterStudent(this, courseNumber);
    }
    @Override
    public String isRegisteredTo(short courseNumber){
        return Database.getInstance().isStudentRegisteredTo(this,courseNumber);
    }

    @Override
    public String getCoursesString(){
        String output = "[";
        registeredCourses.sort((Course c1, Course c2)->{
            return c1.getSerialNumber()- c2.getSerialNumber(); });

        for (Course c:registeredCourses){
            output+=c.getCourseNumber();
            if(c!=registeredCourses.getLast())
                output+=",";
        }

        return output+"]";
    }

    public List<Course> getRegisteredCourses(){return registeredCourses;}

    @Override
    public String getStatus(){
        return String.format("Student: %s\nCourses: %s", username,getCoursesString()) ;
    }

    public String getPassword(){return password;}
    public String getUsername(){return username;}


}
