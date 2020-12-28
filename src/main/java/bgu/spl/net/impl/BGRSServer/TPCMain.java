package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Database;

import java.io.IOException;

public class TPCMain {
    public static void main(String[] args){

        try {
            Database.getInstance().initialize("Courses.txt");
        }

        catch (IOException e) {}

    }
}
