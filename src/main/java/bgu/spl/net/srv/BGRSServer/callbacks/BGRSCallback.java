package bgu.spl.net.srv.BGRSServer.callbacks;

import bgu.spl.net.srv.BGRSServer.BGRSMessagingProtocol;

public interface BGRSCallback  {
     String run(BGRSMessagingProtocol protocol, byte[] msg);
     short getOPCODE();
}
