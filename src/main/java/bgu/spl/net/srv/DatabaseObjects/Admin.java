package bgu.spl.net.srv.DatabaseObjects;

import bgu.spl.net.srv.Database;

import java.util.LinkedList;

public class Admin implements User {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword(){return password;}
    public String getUsername(){return username;}

    @Override
    public String getCourseStatus(short courseNumber) {
        return Database.getInstance().getCourseStatus(courseNumber);
    }

    @Override
    public String getStudentStatus(String username) {
        User toGet = Database.getInstance().getUser(username);
        if(toGet == null)
            return null;
        return toGet.getStatus();
    }

}
