package bgu.spl.net.srv.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;

public class BGRSMessageEncoderDecoder  implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1<<10];
    private int len = 0;
    private short OPCODE = -1;
    private short numberOfSegments = -1;



    @Override
    public String decodeNextByte(byte nextByte) {
        bytes[len++] = nextByte;
        if (len >= 2) {
            if (len == 2)
                return setNumberOfSegments();
            if (numberOfSegments == -2) {
                if (len == 4)
                    return popString();
            }
            else if (nextByte == '\0') {
                numberOfSegments--;
                if (numberOfSegments == 0)
                    return popString();
            }
        }

            return null;
        }


    @Override
    public byte[] encode(String message){
        //System.out.println((message)); //todo remove
        return (message).getBytes(StandardCharsets.UTF_8);
    }

    private String popString(){
        String str = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len=0;
        numberOfSegments=-1;
        return str;
    }

    private String setNumberOfSegments(){
        byte[] bytesArr = new byte[2];
        bytesArr[0]=bytes[0];
        bytesArr[1]=bytes[1];
        short OPCODE = BGRSMessagingProtocol.twoBytesArrToShort(bytesArr);
        String output = null;
        switch(OPCODE){
            case 4:
            case 11: numberOfSegments = 0;
            output= popString();
            break;
            case 1:
            case 2:
            case 3: numberOfSegments=2;
            break;
            case 8: numberOfSegments=1;
            break;
            case 5:
            case 6:
            case 7:
            case 9:
            case 10: numberOfSegments=-2;
            break;
        }
        return output;
    }
}
