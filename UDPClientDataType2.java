import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UDPClientDataType2 {
    private static final String SERVER_HOST = "203.162.10.109";
    private static final int SERVER_PORT = 2207;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
            InetAddress serverAddress = Inet4Address.getByName(SERVER_HOST);

            clientSocket.setSoTimeout(10000);

            String studentCode = "B19DCCN534";
            String qCode = "wwywBCBd";
            String message = String.format(";%s;%s", studentCode, qCode);

            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    serverAddress,
                    SERVER_PORT);

            System.out.println("Sending message: " + message);
            System.out.println(sendData.length);
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[BUFFER_SIZE];
            System.out.println(clientSocket.isConnected());
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length, serverAddress,
                    SERVER_PORT);

            System.out.println("Waiting for server response...");
            clientSocket.receive(receivePacket);

            String response = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength()).trim();

            System.out.println("Received from server: " + response);

            String[] parts = response.split(";");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid response format");
            }

            String requestId = parts[0];
            List<Integer> numbers = Arrays.stream(parts[1].split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            int max = findSecondLargest(numbers);
            int min = findSecondSmallest(numbers);

            String reply = String.format("%s;%d,%d", requestId, max, min);
            byte[] replyData = reply.getBytes();

            DatagramPacket replyPacket = new DatagramPacket(
                    replyData,
                    replyData.length,
                    serverAddress,
                    SERVER_PORT);

            System.out.println("Sending reply: " + reply);
            clientSocket.send(replyPacket);

        } catch (SocketTimeoutException e) {
            System.err.println("Timeout waiting for server response");
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + e.getMessage());
        } catch (SocketException e) {
            e.printStackTrace();
            System.err.println("Socket error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                System.out.println("Closing connection...");
                clientSocket.close();
            }
        }
    }

    private static int findSecondLargest(List<Integer> numbers) {
        int max = Integer.MIN_VALUE;
        int second_max = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.size(); i++) {
            int num = numbers.get(i);
            if (num > max) {
                second_max = max;
                max = num;
            } else if (num > second_max && num < max) {
                second_max = num;
            }
        }
        return second_max;
    }

    private static int findSecondSmallest(List<Integer> numbers) {
        int min = Integer.MAX_VALUE;
        int second_min = Integer.MAX_VALUE;
        for (int i = 0; i < numbers.size(); i++) {
            int num = numbers.get(i);
            if (num < min) {
                second_min = min;
                min = num;
            } else if (num < second_min && num > min) {
                second_min = num;
            }
        }
        return second_min;
    }
}
