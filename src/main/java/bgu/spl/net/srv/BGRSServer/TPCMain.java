package bgu.spl.net.srv.BGRSServer;

import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Reactor;
import bgu.spl.net.srv.Server;

import java.io.IOException;

public class TPCMain {
    public static void main(String[] args){

        try {
            Database.getInstance().initialize("Courses.txt");
            Server.reactor(3,7777,()->new BGRSMessagingProtocol(),()->new BGRSMessageEncoderDecoder()).serve();
        }

        catch (IOException e) {}



    }
}
