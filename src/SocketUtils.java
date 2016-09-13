import java.io.*;
import java.net.Socket;

/**
 * Created by pavel on 13/09/16.
 */
public class SocketUtils {
    /**
     * Entrada de consola
     */
    public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public static void sendMessage(Socket socket, String message) throws IOException {
        try {
            //System.out.println("Writing to output stream...");
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(message);
        } catch (IOException e) {
            System.err.println("Error: unable to write stream to client");
            throw new IOException();
        }
    }

    public static String receiveMessage(Socket socket) throws IOException {
        try {
            //System.out.println("Reading from input stream...");
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            return inputStream.readUTF();
        } catch (IOException e) {
            System.err.println("Error: unable to read stream from client");
            throw new IOException();
        }
    }
}
