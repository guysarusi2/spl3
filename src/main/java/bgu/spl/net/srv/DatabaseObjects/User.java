package bgu.spl.net.srv.DatabaseObjects;

public interface User extends Comparable<User> {

    public String getPassword();

    public String getUsername();

    default public boolean courseRegister(short courseNumber) {
        return false;
    }

    default public boolean courseUnregister(short courseNumber) {
        return false;
    }

    default public String kdamCheck(short courseNumber) {
        return null;
    }

    default public String isRegisteredTo(short courseNumber) {
        return null;
    }

    default public String getCourseStatus(short courseNumber) {return null;}
    default public String getStudentStatus(String username) {return null;}
    default public String getCoursesString() {
        return null;
    }
    default public String getStatus(){return null;}
    default public int compareTo(User var1){return getUsername().compareTo(var1.getUsername());}
}
