import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        DatagramSocket socket;
        Scanner sc = new Scanner(System.in);
        String s = "";

        while (!s.equals("exit")) {
            System.out.println("Inserisci OP1 OPERATION OP2: ");
            s = sc.nextLine();
            if(s.equals("exit")) {
                return;
            }
            System.out.println(s);

            InetAddress address;
            try {
                address = InetAddress.getByName("127.0.0.1");
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            DatagramPacket packet = new DatagramPacket(s.getBytes(), s.getBytes().length, address, 9000);

            try {
                socket = new DatagramSocket();
                socket.send(packet);

                byte[] buf = new byte[1024];
                DatagramPacket packetResponse = new DatagramPacket(buf, 1024);

                socket.receive(packetResponse);

                String rispostaServer = new String(packetResponse.getData(), 0, packetResponse.getLength());
                System.out.println(rispostaServer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
