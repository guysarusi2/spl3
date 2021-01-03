package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.DatabaseObjects.Student;
import bgu.spl.net.srv.DatabaseObjects.User;

public class CourseRegisterFunction implements BGRSCallback{

    private final static short OPCODE = 5;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        if(!protocol.isUserConnected())
            return null;

        short courseNumber = BGRSMessagingProtocol.twoBytesArrToShort(msg);

        User user = protocol.getUser();
        boolean success = user.courseRegister(courseNumber);

        return (success) ? "" : null;
    }

    public short getOPCODE() {return this.OPCODE;}


}
