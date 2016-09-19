import java.io.*;
import java.net.Socket;

/**
 * Created by pavel on 15/09/16.
 */
public class ThreadServer extends Thread {
    private Socket skRequest;

    public ThreadServer(Socket socket) {
        skRequest = socket;
    }

    public void run() {
        try {
            String inputCommand = SocketUtils.receiveMessage(skRequest);
            System.out.println("inputCommand = " + inputCommand);
            Thread.sleep(2000);
            System.out.println(SocketUtils.executeCommand(inputCommand, skRequest));
            System.out.println("Command executed");
            SocketUtils.sendMessage(skRequest,inputCommand.toUpperCase());
            skRequest.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
