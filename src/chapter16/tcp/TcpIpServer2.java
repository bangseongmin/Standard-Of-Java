package chapter16.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpIpServer2 {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(7777);
            System.out.println(getTime() + " ready server");
        }catch (IOException e) {
            e.printStackTrace();;
        }


        while (true) {
            try {
                System.out.println(getTime() + " waiting for connection");
                Socket socket = serverSocket.accept();

                System.out.println(getTime() + "request from " + socket.getInetAddress());

                System.out.println("getPort() : " + socket.getPort());
                System.out.println("getLocalPort() : " + socket.getLocalPort());

                // 소켓의 출력스트림을 얻는다.
                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);

                // 원격 소켓(remote socket)에 데이터를 보낸다.
                dos.writeUTF("[Notice] Test Message1 from Server");
                System.out.println(getTime() + " sent data");

                // 스트림과 소켓을 닫아준다.
                dos.close();
                socket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }
}
