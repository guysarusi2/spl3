package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;
import bgu.spl.net.srv.DatabaseObjects.User;

public class CourseStatusFunction implements BGRSCallback{
    private final static short OPCODE = 7;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        if(!protocol.isUserConnected())
            return null;

        short courseNumber = BGRSMessagingProtocol.twoBytesArrToShort(msg);

        return protocol.getUser().getCourseStatus(courseNumber);
    }

    public short getOPCODE() {return this.OPCODE;}
}
