//package bgu.spl.net;
//
//import bgu.spl.net.impl.echo.EchoProtocol;
//import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;
//import bgu.spl.net.srv.Reactor;
//import bgu.spl.net.srv.Server;
//
//public class main {
//    public static void main(String[] args){
//        //Server<String> server = Server.reactor(2, 7777, () -> new EchoProtocol(), () -> new LineMessageEncoderDecoder());
////        Server<String> server=Server.threadPerClient(7777, () -> new EchoProtocol(), () -> new LineMessageEncoderDecoder());
////        server.serve();
//
////        String s="guysa";
////        String a="guy\0sa";
////        System.out.println(s);
////        System.out.println(a);
////        System.out.println((int)a.charAt(3));
//
//    }
//}
