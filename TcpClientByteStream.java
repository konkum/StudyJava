import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TcpClientByteStream {
    private static final String SERVER_IP = "203.162.10.109";
    private static final int SERVER_PORT = 2206;
    private static final int TIMEOUT = 5000;

    public static void main(String[] args) {
        System.out.println("Connecting to server " + SERVER_IP + ":" + SERVER_PORT + "...");

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT), TIMEOUT);

            socket.setSoTimeout(TIMEOUT);

            System.out.println("Connected successfully!");

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String message = "B19DCCN534;dTvVRpdm";
            out.write(message.getBytes());
            out.flush();
            System.out.println("Sent to server: " + message);

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);

            if (bytesRead == -1) {
                throw new IOException("Connection closed by server");
            }

            String receivedData = new String(buffer, 0, bytesRead);
            System.out.println("Received from server: " + receivedData);

            long sum = findSum(receivedData);
            String sumString = String.valueOf(sum);
            System.out.println("Calculated sum: " + sumString);

            out.write(sumString.getBytes());
            out.flush();
            System.out.println("Sent sum to server: " + sumString);

            System.out.println("Closing connection...");

        } catch (SocketTimeoutException e) {
            System.err.println("Connection timed out: " + e.getMessage());
        } catch (ConnectException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error during communication: " + e.getMessage());
        }
    }

    private static long findSum(String numberList) {
        String[] numberStrings = numberList.trim().split("\\|");

        long sum = 0;
        for (String number : numberStrings) {
            try {
                sum += Long.parseLong(number.trim());
            } catch (NumberFormatException ex) {
                System.err.println("Warning: Invalid number format in: " + number);
            }
        }

        return sum;
    }
}
