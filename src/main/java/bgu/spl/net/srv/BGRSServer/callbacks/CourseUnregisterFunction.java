package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;
import bgu.spl.net.srv.DatabaseObjects.User;

import java.nio.charset.StandardCharsets;

public class CourseUnregisterFunction implements BGRSCallback{
    private final static short OPCODE = 10;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        if(!protocol.isUserConnected())
            return null;

        short courseNumber = Short.parseShort(new String(msg, StandardCharsets.UTF_8));
        User user = protocol.getUser();
        boolean success = user.courseUnregister(courseNumber);
        return (success) ? "" : null;
    }

    public short getOPCODE() {return this.OPCODE;}


}
