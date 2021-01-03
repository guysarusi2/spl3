package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;

public interface BGRSCallback  {
    public String run(BGRSMessagingProtocol protocol, byte[] msg);
    public short getOPCODE();

}
