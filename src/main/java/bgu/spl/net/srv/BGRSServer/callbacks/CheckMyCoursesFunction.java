package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;
import bgu.spl.net.srv.DatabaseObjects.User;

public class CheckMyCoursesFunction implements BGRSCallback{
    private final static short OPCODE = 11;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        if(!protocol.isUserConnected())
            return null;

        String str = protocol.getUser().getCoursesString();
        return protocol.getUser().getCoursesString();
    }

    public short getOPCODE() {return this.OPCODE;}

}
