package chapter16.tcp;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * 소켓으로 데이터를 송신하는 작업과 숮신하는 작업을 별도의 쓰레드 Sender와 Receiver가 처리하도록 하여 송신과 수신이 동시에 이루어지도록 했다.
 */
public class TcpIpClient5 {

    public static void main(String args[]) {
        try {
            String serverIp = "127.0.0.1";
            // 소켓을 생성하여 연결을 요청한다.
            Socket socket = new Socket(serverIp, 7777);


            System.out.println("connected server");

            Sender sender = new Sender(socket);
            Receiver receiver = new Receiver(socket);

            sender.start();
            receiver.start();
        } catch(ConnectException ce) {
            ce.printStackTrace();
        } catch(IOException ie) {
            ie.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    } // main
}
