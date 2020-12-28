package bgu.spl.net.srv.DatabaseObjects;

public class Course {

    private int serialNumber;
    private int courseNum;
    private String courseName;
    private int numOfMaxStudents;
    private Course[] kdamCoursesList;
    private int registeredStudentCount;

    public Course(int courseNum, String courseName, int serialNumber){
        courseNum = courseNum;
        courseName = courseName;
        serialNumber = serialNumber;
    }

/*    public Course(int courseNum, String courseName,Course[] KdamCoursesList, int numOfMaxStudents, int serialNumber){
        courseNum = courseNum;
        courseName = courseName;
        KdamCoursesList = KdamCoursesList;
        numOfMaxStudents = numOfMaxStudents;
        serialNumber = serialNumber;
    }*/                 //todo: NEED?

    public void updateCourse(Course[] KdamCoursesList, int numOfMaxStudents){
        KdamCoursesList = KdamCoursesList;
        numOfMaxStudents = numOfMaxStudents;
    }

    public int getSerialNumber(){return serialNumber;}

    public Course[] getKdamCoursesList() {
        return kdamCoursesList;
    }
}
