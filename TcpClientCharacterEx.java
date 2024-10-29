import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TcpClientCharacterEx {
    private static final String SERVER_IP = "203.162.10.109";
    private static final int SERVER_PORT = 2208;
    private static final int TIMEOUT = 5000;

    public static void main(String[] args) {
        System.out.println("Connecting to server " + SERVER_IP + ":" + SERVER_PORT + "...");

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT), TIMEOUT);

            socket.setSoTimeout(TIMEOUT);

            System.out.println("Connected successfully!");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            String message = "B19DCCN534;rJwWTu6E";
            writer.write(message);
            writer.newLine();
            writer.flush();
            System.out.println("Sent to server: " + message);

            String domainList = reader.readLine();
            if (domainList == null || domainList.trim().isEmpty()) {
                throw new IOException("Received empty response from server");
            }
            System.out.println("Received from server: " + domainList);

            String eduDomains = findEduDomains(domainList);
            writer.write(eduDomains);
            writer.newLine();
            writer.flush();
            System.out.println("Sent .edu domains to server: " + eduDomains);

            System.out.println("Closing connection...");

        } catch (SocketTimeoutException e) {
            System.err.println("Connection timed out: " + e.getMessage());
        } catch (ConnectException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error during communication: " + e.getMessage());
        }
    }

    private static String findEduDomains(String domainList) {
        // Split the domain list by commas and spaces
        String[] domains = domainList.trim().split(",\\s*");

        // Filter .edu domains and join them with commas
        return Arrays.stream(domains)
                .filter(domain -> domain.endsWith(".edu"))
                .collect(Collectors.joining(", "));
    }
}
