package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;
import bgu.spl.net.srv.Database;

public class LogoutFunction<T> implements BGRSCallback {
    private final static short OPCODE = 4;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        if(!protocol.isUserConnected())
            return null;

        if(Database.getInstance().disconnectUser(protocol.getUser().getUsername())) {
            protocol.removeUser();
            protocol.Terminate();
            return "";
        }

        return null;
    }

    public short getOPCODE() {return this.OPCODE;}
}
