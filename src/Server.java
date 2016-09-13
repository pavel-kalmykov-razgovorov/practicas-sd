import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by pavel on 12/09/16.
 */
public class Server {
    /**
     * Configuramos el estado del servidor con estas dos constantes y la variable que lo controla
     * Seguiremos escuchando al cliente mientras ServerStatus tenga el estado SERVER_ON
     */
    private static final int SERVER_ON = 1;
    private static final int SERVER_OFF = 0;
    private static int ServerStatus = SERVER_OFF; //Por defecto no aceptamos

    public static void main(String[] args) {
        displayIPAdresses();
        //Obtenemos el puerto en el que queremos comenzar a escuchar
        int serverPort = -1;
        if (args.length != 1) {
            try {
                System.out.printf("Server port: ");
                serverPort = Integer.parseInt(SocketUtils.input.readLine());
            } catch (NumberFormatException e) {
                System.err.println("Error: port must be a number");
            } catch (IOException e) {
                System.err.println("Error: unable to open console input buffer");
            }
        } else {
            serverPort = Integer.parseInt(args[0]);
        }

        //Abrimos el socket y comenzamos a escuchar
        try {
            ServerSocket skServer = new ServerSocket(serverPort);
            System.out.println("Socket opened at port " + skServer.getLocalPort());

            while (true) { //Bucle. Para con ^C o ^Z
                Socket skRequest = skServer.accept();
                System.out.println("Serving to " + skRequest.getRemoteSocketAddress());
                ServerStatus = SERVER_ON;
                while (ServerStatus == SERVER_ON) {
                    String inputMessage = SocketUtils.receiveMessage(skRequest);
                    System.out.println("inputMessage = " + inputMessage);
                    SocketUtils.sendMessage(skRequest, inputMessage.toUpperCase());
                    ServerStatus = SERVER_OFF;
                }
                skRequest.close();
            }
        } catch (IOException e) {
            System.err.println("Error: connection closed with client at port" + serverPort);
        }
    }

    /**
     * Selecciona todas las IP's que tiene el equipo y las muestra (sólo en formato IPV4)
     */
    private static void displayIPAdresses() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces(); //Recibe todas las interfaces de red
            System.out.println("Available IP's at this machine");
            for (NetworkInterface network : Collections.list(networks)) {
                Enumeration<InetAddress> networkadresses = network.getInetAddresses(); //Recibe todas las direcciones de cada interfaz
                //Hacemos un filter de las IP's para quedarnos sólo con las IPV4 y las mostramos
                Collections.list(networkadresses).stream().filter(adress -> adress instanceof Inet4Address).forEach(adress -> {
                    System.out.println("\t" + adress);
                });
            }
        } catch (SocketException e) {
            System.err.println("Error: unable to display available IP's");
        }
    }
}
