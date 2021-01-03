package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;

public class LogoutFunction<T> implements BGRSCallback {
    private final static short OPCODE = 4;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        // [username + \0 + password + \0]

        if(!protocol.isUserConnected())
            return null;

        protocol.removeUser();
        protocol.Terminate();

        return "";
    }

    public short getOPCODE() {return this.OPCODE;}
}
