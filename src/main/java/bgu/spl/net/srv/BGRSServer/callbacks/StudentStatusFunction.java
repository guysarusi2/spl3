package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;

import java.nio.charset.StandardCharsets;

public class StudentStatusFunction implements BGRSCallback{
    private final static short OPCODE = 8;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        if(!protocol.isUserConnected())
            return null;

        String username = new String(msg,0,msg.length-1, StandardCharsets.UTF_8);

        String str = protocol.getUser().getStudentStatus(username);     //todo remove
        return protocol.getUser().getStudentStatus(username);
    }

    public short getOPCODE() {return this.OPCODE;}

}
