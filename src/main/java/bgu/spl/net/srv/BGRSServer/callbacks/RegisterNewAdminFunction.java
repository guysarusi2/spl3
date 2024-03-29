package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.DatabaseObjects.Admin;
import bgu.spl.net.srv.DatabaseObjects.Student;
import bgu.spl.net.srv.DatabaseObjects.User;

import java.nio.charset.StandardCharsets;

public class RegisterNewAdminFunction<T> implements BGRSCallback {
    private final static short OPCODE = 1;
    @Override
    public String run(BGRSMessagingProtocol protocol, byte[] msg) {
        String message = new String(msg, StandardCharsets.UTF_8);

        if(protocol.isUserConnected())
            return null;
        String username= message.substring(0,message.indexOf('\0'));
        if(Database.getInstance().isUserExist(username))
            return null;
        String password = message.substring(message.indexOf('\0')+1,message.length()-1);
        boolean out =Database.getInstance().addUser(new Admin(username,password));

        return  (!out)? "" : null;
    }

    public short getOPCODE() {return this.OPCODE;}
}
