package bgu.spl.net.srv.BGRSServer;


public class RunClientTests {

        public static void main(String[] args) {
                new Thread(new Tests()).start();
        }
}