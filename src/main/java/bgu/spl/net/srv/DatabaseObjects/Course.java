package bgu.spl.net.srv.DatabaseObjects;

import java.util.LinkedList;
import java.util.List;

public class Course implements Comparable<Course>{

    private int serialNumber;
    private short courseNumber;
    private String courseName;
    private int numOfMaxStudents;
    private Course[] kdamCoursesList;
    private LinkedList<Student> studentsList;

    public Course(short courseNumber, String courseName){
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        studentsList = new LinkedList();
    }

/*    public Course(int courseNum, String courseName,Course[] KdamCoursesList, int numOfMaxStudents, int serialNumber){
        courseNum = courseNum;
        courseName = courseName;
        KdamCoursesList = KdamCoursesList;
        numOfMaxStudents = numOfMaxStudents;
        serialNumber = serialNumber;
        serialNumber = serialNumber;
        serialNumber = serialNumber;
        serialNumber = serialNumber;
    }*/                 //todo: NEED?

    public void update(Course[] KdamCoursesList, int numOfMaxStudents){
        this.kdamCoursesList = KdamCoursesList;
        this.numOfMaxStudents = numOfMaxStudents;
    }

    public int getSerialNumber(){return serialNumber;}
    public void setSerialNumber(int serialNumber){this.serialNumber = serialNumber;}
    public short getCourseNumber(){return this.courseNumber;}
    public String getCourseName(){return this.courseName;}
    public int getNumberOfRegisteredStudents(){return studentsList.size();}
    public String setCourseName(){return this.courseName;}
    public Course[] getKdamCoursesList() {
        return kdamCoursesList;
    }

    public boolean registerStudent(Student toAdd){
        return studentsList.add(toAdd);
    }

    public boolean unregisterStudent(Student toRemove){
        return studentsList.remove(toRemove);
    }

    public boolean verifyKdams(Student toVerify){
        for(Course neededKdam: this.kdamCoursesList)
            if (!toVerify.getRegisteredCourses().contains(neededKdam))
                return false;

        return true;
    }
    public int getNumOfMaxStudents(){return this.numOfMaxStudents;}
    public int getAvailableSeats(){return numOfMaxStudents-studentsList.size();}

    public String getStudentsListString(){
        String output = "[";
        studentsList.sort((Student s1, Student s2)-> {
            return s1.getUsername().compareTo(s2.getUsername());
        });

        for (Student s:studentsList){
            output+=s.getUsername();
            if(s!=studentsList.getLast())
                output+=",";
        }

        return output+"]";
    }

    public String getKdamsString(){
        String output = "[";
        for (int i = 0; i < kdamCoursesList.length-1; i++)
            output+=kdamCoursesList[i].getCourseNumber()+",";

        if (kdamCoursesList.length>0)
            output+=kdamCoursesList[kdamCoursesList.length-1].getCourseNumber();

        return output+"]";
    }

    public List<Student> getStudentsList(){return this.studentsList;}


    public boolean isFullyBooked(){return this.numOfMaxStudents==this.getNumberOfRegisteredStudents();}

    @Override
    public int compareTo(Course other) {
        return this.getSerialNumber()-other.getSerialNumber();
    }
}
