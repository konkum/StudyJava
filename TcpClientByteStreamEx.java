
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TcpClientByteStreamEx {
    private static final String SERVER_IP = "203.162.10.109";
    private static final int SERVER_PORT = 2207;
    private static final int TIMEOUT = 5000;

    public static void main(String[] args) {
        System.out.println("Connecting to server " + SERVER_IP + ":" + SERVER_PORT + "...");

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT), TIMEOUT);

            socket.setSoTimeout(TIMEOUT);

            System.out.println("Connected successfully!");

            DataInputStream din = new DataInputStream(socket.getInputStream());
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            String message = "B19DCCN534;hnHwFgYM";
            dout.writeUTF(message);
            dout.flush();
            System.out.println("Sent to server: " + message);

            int a = din.readInt();
            int b = din.readInt();
            System.out.println("Received numbers from server: a=" + a + ", b=" + b);

            int sum = a + b;
            long product = (long) a * b;

            dout.writeInt(sum);
            dout.flush();
            System.out.println("Sent sum to server: " + sum);

            dout.writeLong(product);
            dout.flush();
            System.out.println("Sent product to server: " + product);

            System.out.println("Closing connection...");

        } catch (SocketTimeoutException e) {
            System.err.println("Connection timed out: " + e.getMessage());
        } catch (ConnectException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error during communication: " + e.getMessage());
        }
    }
}
