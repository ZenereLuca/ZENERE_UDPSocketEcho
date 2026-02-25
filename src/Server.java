import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
    public static void main(String[] args) {
        DatagramSocket socket;

        try {
            socket = new DatagramSocket(9000);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, 1024);

            try {
                System.out.println("Aspetto i pacchetti...");
                socket.receive(packet);
                String s = new String(packet.getData(), 0, packet.getLength());
                System.out.println(s);

                // Separa le parti dell'operazione
                String[] parts = s.split(" ");
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[2]);
                String op = parts[1];

                double res = 0;
                if(op.equals("ADD")) {
                    res = x + y;
                }
                if(op.equals("SUB")) {
                    res = x - y;
                }
                if(op.equals("MUL")) {
                    res = x * y;
                }
                if(op.equals("DIV")) {
                    res = x / y;
                }
                if(op.equals("POW")) {
                    res = Math.pow(x, y);
                }

                String sRes = String.valueOf(res);

                DatagramPacket packetResponse = new DatagramPacket(sRes.getBytes(), sRes.length(), packet.getAddress(), packet.getPort());
                socket.send(packetResponse);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
