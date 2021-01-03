package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;

import java.nio.charset.StandardCharsets;

public class StudentStatusFunction implements BGRSCallback{
    private final static short OPCODE = 8;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        if(!protocol.isUserConnected())
            return null;

        String username = new String(msg, StandardCharsets.UTF_8);

        return protocol.getUser().getStudentStatus(username);
    }

    public short getOPCODE() {return this.OPCODE;}

}
