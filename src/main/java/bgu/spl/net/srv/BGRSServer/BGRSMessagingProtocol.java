package bgu.spl.net.srv.BGRSServer;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.BGRSServer.callbacks.*;
import bgu.spl.net.srv.DatabaseObjects.User;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class BGRSMessagingProtocol implements MessagingProtocol<String> {
    private boolean shouldTerminate = false;
    private static final HashMap<Short, BGRSCallback> CALLBACKS = new HashMap<>(); //todo init
    static{
        CALLBACKS.put((short) 1, new RegisterNewAdminFunction());
        CALLBACKS.put((short) 2, new RegisterNewStudentFunction());
        CALLBACKS.put((short) 3, new LoginFunction());
        CALLBACKS.put((short) 4, new LogoutFunction<>());
        CALLBACKS.put((short) 5, new CourseRegisterFunction());
        CALLBACKS.put((short) 6, new KdamCheckFunction());
        CALLBACKS.put((short) 7, new CourseStatusFunction());
        CALLBACKS.put((short) 8, new StudentStatusFunction());
        CALLBACKS.put((short) 9, new IsRegisteredFunction());
        CALLBACKS.put((short) 10, new CourseUnregisterFunction());
        CALLBACKS.put((short) 11, new CheckMyCoursesFunction());
    }

    private User currentUser = null;

    @Override
    public String process(String msg){
        short msg_OPCODE= extractMsgOPCODE(msg);
        String response=CALLBACKS.get(msg_OPCODE).run(this,msg.substring(2).getBytes());
        return ((response==null) ? error(msg_OPCODE) : acknowledge(msg_OPCODE,response));
        /*
        read first 2 bytes, then create message by this OPCOD
        case opcode is 4(LOGOUT):
            if
        * */
    }

    @Override
    public boolean shouldTerminate(){ return shouldTerminate;}

    public boolean isUserConnected(){return currentUser!=null;}
    public User getUser(){return currentUser;}
    public User setUser(User user){return currentUser=user;}
    public void removeUser(){currentUser=null;}
    public void Terminate(){shouldTerminate=true;}

    private short extractMsgOPCODE(String msg){
        return 0;       // TODO impl
    }

    private String error(short msg_OPCODE){
        byte[] bytesArr = new byte[4];
        short errorOPCODE = 13;
        bytesArr[0] = (byte)((errorOPCODE >> 8) & 0xFF);
        bytesArr[1] = (byte)(errorOPCODE & 0xFF);
        bytesArr[2] = (byte)((msg_OPCODE >> 8) & 0xFF);
        bytesArr[3] = (byte)(msg_OPCODE & 0xFF);
        return new String(bytesArr, StandardCharsets.UTF_8);
    }

    private String acknowledge(short msg_OPCODE, String response){
       String msg_OPCODE_str = new String(shortToByteArray(msg_OPCODE),StandardCharsets.UTF_8);
       String acc_arr_str = new String(shortToByteArray((short) 12),StandardCharsets.UTF_8);

       return msg_OPCODE_str + acc_arr_str+response;
    }


    public static short twoBytesArrToShort(byte[] byteArr){
        if(byteArr.length!=2)
            throw new IllegalArgumentException("byte[] representing OPCODE must be 2 bytes length");

        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }

    public static byte[] shortToByteArray(short num){
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }

}
