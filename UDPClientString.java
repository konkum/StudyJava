import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UDPClientString {
    private static final String SERVER_IP = "203.162.10.109";
    private static final int SERVER_PORT = 2208;
    private static final int BUFFER_SIZE = 1024;
    private static final int TIMEOUT = 1000;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT);
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);

            String message = ";B19DCCN534;oPsvm9v4";
            byte[] sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    serverAddress,
                    SERVER_PORT);

            socket.send(sendPacket);
            System.out.println("Sent to server: " + message);

            byte[] receiveData = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(receivePacket);
            String receivedMessage = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength());

            System.out.println("Received from server: " + receivedMessage);

            String[] parts = receivedMessage.split(";", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid message format received");
            }

            String requestId = parts[0];
            String data = parts[1];

            String normalizedString = normalizeString(data);
            String response = requestId + ";" + normalizedString;

            byte[] responseData = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                    responseData,
                    responseData.length,
                    serverAddress,
                    SERVER_PORT);

            socket.send(responsePacket);
            System.out.println("Sent normalized string: " + response);

            System.out.println("Closing connection...");

        } catch (SocketTimeoutException e) {
            System.err.println("Connection timed out: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error during communication: " + e.getMessage());
        }
    }

    private static String normalizeString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        // Split the string into words
        String[] words = input.toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();

        // Process each word
        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                // Capitalize first character and append rest of the word
                result.append(Character.toUpperCase(words[i].charAt(0)))
                        .append(words[i].substring(1));

                // Add space between words
                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
        }

        return result.toString();
    }
}
