package bgu.spl.net.srv.DatabaseObjects;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Course implements Comparable<Course>{

    private int serialNumber;
    private short courseNumber;
    private String courseName;
    private int numOfMaxStudents;
    private Course[] kdamCoursesList;
    private LinkedList<Student> studentsList;
    private ReadWriteLock studentsListRWLock;

    public Course(short courseNumber, String courseName){
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        studentsList = new LinkedList();
        studentsListRWLock= new ReentrantReadWriteLock();
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
    public int getNumberOfRegisteredStudents(){
        studentsListRWLock.readLock().lock();
        int out = studentsList.size();
        studentsListRWLock.readLock().unlock();
        return out;
    }
    public String setCourseName(){return this.courseName;}
    public Course[] getKdamCoursesList() {
        return kdamCoursesList;
    }

    public boolean registerStudent(Student toAdd){
        studentsListRWLock.writeLock().lock();
        boolean out = studentsList.add(toAdd);
        studentsListRWLock.writeLock().unlock();
        return out;
    }

    public boolean unregisterStudent(Student toRemove){
        studentsListRWLock.writeLock().lock();
        boolean out = studentsList.remove(toRemove);
        studentsListRWLock.writeLock().unlock();
        return out;    }

    public boolean verifyKdams(Student toVerify){
        for(Course neededKdam: this.kdamCoursesList)
            if (toVerify.isRegisteredTo(neededKdam.getCourseNumber())=="NOT REGISTERED")
                return false;

        return true;
    }
    public int getNumOfMaxStudents(){return this.numOfMaxStudents;}
    public int getAvailableSeats(){return numOfMaxStudents-studentsList.size();}

    public boolean checkForStudent(Student st){
        studentsListRWLock.readLock().lock();
        boolean out = studentsList.contains(st);
        studentsListRWLock.readLock().unlock();
        return out;
    }

    public String getStudentsListString(){
        studentsListRWLock.writeLock().lock();
        String output = "[";
        studentsList.sort((Student s1, Student s2)-> {
            return s1.getUsername().compareTo(s2.getUsername());
        });
        studentsListRWLock.writeLock().unlock();

        studentsListRWLock.readLock().lock();
        for (Student s:studentsList){
            output+=s.getUsername();
            if(s!=studentsList.getLast())
                output+=",";
        }
        studentsListRWLock.readLock().unlock();

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

//    public List<Student> getStudentsList(){
//        studentsListRWLock.readLock().lock();
//        List<Student> cl =(List<Student>) this.studentsList.clone();
//        studentsListRWLock.readLock().unlock();
//        return cl;
//    }


    public boolean isFullyBooked(){return this.numOfMaxStudents==this.getNumberOfRegisteredStudents();}

    @Override
    public int compareTo(Course other) {
        return this.getSerialNumber()-other.getSerialNumber();
    }
}
