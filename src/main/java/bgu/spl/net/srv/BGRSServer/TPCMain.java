package bgu.spl.net.srv.BGRSServer;

import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.DatabaseObjects.Admin;
import bgu.spl.net.srv.DatabaseObjects.Student;
import bgu.spl.net.srv.Reactor;
import bgu.spl.net.srv.Server;

import java.io.IOException;

public class TPCMain {
    public static void main(String[] args){
        int port = Integer.parseInt(args[0]);

        try {
            Database.getInstance().initialize("Courses.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Server.reactor(3,port,()->new BGRSMessagingProtocol(),()->new BGRSMessageEncoderDecoder()).serve();
    }
}
