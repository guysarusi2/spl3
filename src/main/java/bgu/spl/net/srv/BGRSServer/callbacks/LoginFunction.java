package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;
import bgu.spl.net.srv.Database;

import java.nio.charset.StandardCharsets;

public class LoginFunction implements BGRSCallback {
    private final static short OPCODE = 3;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        // [username + \0 + password + \0]
        String message = new String(msg, StandardCharsets.UTF_8);

        if(protocol.isUserConnected())
            return null;
        String username= message.substring(0,message.indexOf('\0'));
        if(Database.getInstance().isUserExist(username))
            return null;
        String password = message.substring(message.indexOf('\0')+1);
        if(Database.getInstance().getPassword(username) != password)
            return null;

        protocol.setUser(Database.getInstance().getUser(username));

        return "";
    }

    public short getOPCODE() {return this.OPCODE;}
}
