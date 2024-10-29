import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class UDPClientDataType {
    private static final String SERVER_IP = "203.162.10.109";
    private static final int SERVER_PORT = 2207;
    private static final int BUFFER_SIZE = 1024;

    // Helper method to find maximum value in array
    private static int findMax(String[] numbers) {
        return Arrays.stream(numbers)
                .mapToInt(Integer::parseInt)
                .max()
                .orElseThrow();
    }

    // Helper method to find minimum value in array
    private static int findMin(String[] numbers) {
        return Arrays.stream(numbers)
                .mapToInt(Integer::parseInt)
                .min()
                .orElseThrow();
    }

    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            // Create UDP socket
            clientSocket.setSoTimeout(1000);
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);

            String studentCode = "B19DCCN534";
            String qCode = "HmHWmPPp";
            String message = ";" + studentCode + ";" + qCode;

            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    serverAddress,
                    SERVER_PORT);

            System.out.println("Sending initial message: " + message);
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length, serverAddress,
                    SERVER_PORT);

            clientSocket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received from server: " + response);

            String[] parts = response.split(";");
            String requestId = parts[0];
            String[] numbers = parts[1].split(",");

            int maxVal = findMax(numbers);
            int minVal = findMin(numbers);

            String resultMessage = requestId + ";" + maxVal + "," + minVal;
            byte[] resultData = resultMessage.getBytes();

            DatagramPacket resultPacket = new DatagramPacket(
                    resultData,
                    resultData.length,
                    serverAddress,
                    SERVER_PORT);

            System.out.println("Sending result: " + resultMessage);
            clientSocket.send(resultPacket);

            if (clientSocket != null && !clientSocket.isClosed()) {
                System.out.println("Closing connection...");
                clientSocket.close();
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + SERVER_IP);
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            System.err.println("Socket Time out: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
