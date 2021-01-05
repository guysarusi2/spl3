package bgu.spl.net.srv.BGRSServer;

import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.DatabaseObjects.Admin;
import bgu.spl.net.srv.DatabaseObjects.Student;
import bgu.spl.net.srv.Reactor;
import bgu.spl.net.srv.Server;

import java.io.IOException;

public class TPCMain {
    public static void main(String[] args){
        //int port = Integer.parseInt(args[0]);

/*            Database.getInstance().initialize("Courses.txt");
            Student s1 = new Student("a","1");
            Student s2 = new Student("b","2");          //todo remove all
            Student s3 = new Student("c","3");
            Student s4 = new Student("d","4");

            Admin a1 = new Admin("sh","12");

            Database.getInstance().addUser(s1);
            Database.getInstance().addUser(s2);
            Database.getInstance().addUser(s3);
            Database.getInstance().addUser(s4);
            Database.getInstance().addUser(a1);


            s1.courseRegister((short)100);
            s1.courseRegister((short)101);
            s1.courseRegister((short)102);
            s1.courseRegister((short)103);
            s1.courseRegister((short)110);
            s2.courseRegister((short)100);

            s2.courseRegister((short)100);

            s2.courseRegister((short)100);*/
            Server.threadPerClient(7777,()->new BGRSMessagingProtocol(),()->new BGRSMessageEncoderDecoder()).serve();





    }
}
