package chapter16.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 서버에 접속하는 클라이언트의 수가 많을 때는 쓰레드를 이용해서 클라이언트의 요청을 병렬적으로 처리하는 것이 좋다.
 * 그렇지 않으면 서버가 접속을 요청한 순서대로 처리하기 떄문에 늦게 접속을 요청한 클라이언트는 오랜 시간을 기다릴 수 있다.
 */
public class TcpIpServer4 implements Runnable {

    ServerSocket serverSocket;

    Thread[] threads;

    public static void main(String[] args) {
        // 5개의 쓰레드를 생성하는 서버를 생성
        TcpIpServer4 server = new TcpIpServer4(5);

        server.start();
    }

    public TcpIpServer4(int num) {
        try {
            // 서버소켓 생성하여 7777번 포트와 결합
            serverSocket = new ServerSocket(7777);
            System.out.println(getTime() + " 서버가 준비되었습니다.");

            threads = new Thread[num];
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        for(int i=0;i<threads.length; i++) {
            threads[i] = new Thread(this);
            threads[i].start();
        }
    }

    public static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(getTime() + "가 연결요청을 기다립니다.");

                Socket socket = serverSocket.accept();
                System.out.println(getTime() + "request connection from " + socket.getInetAddress());

                // 소켓의 출력스트림을 얻는다.
                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);

                // 원격소켓(remote socket)에 데이터를 보낸다.
                dos.writeUTF("[Notice] Test Message1 from server");
                System.out.println(getTime() + " sent Data");

                // 스트림과 소켓을 닫아준다.
                dos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
