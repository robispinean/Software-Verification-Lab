import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;

import utils.FileUtils;

public class WebServer extends Thread {
    protected static Socket clientSocket;
    static final int PORT = 8088;

    private FileUtils fileUtils = new FileUtils();
    public static String STATUS = "STOPPED";

    public static void main(String[] args) throws IOException{
        Thread CLI = new Thread(() -> CLIConfig());
        CLI.start();
    }

    public static void CLIConfig() {
        System.out.println("0: STOP SERVER\n" + "1: MAINTENANCE\n" + "2: RUN\n" + "3: CLOSE\n");
        System.out.println("SERVER STATUS: " + STATUS);
        Scanner input = new Scanner(System.in);

        switch(input.nextLine()) {
            case "0": {
                STATUS = "STOPPED";
                CLIConfig();
                break;
            }
            case "1": {
                    STATUS = "MAINTENANCE";
                    break;
            }
            case "2": {
                    STATUS ="RUNNING";
                    break;
            }
            case "3": {
                    STATUS = "EXIT";
                    break;
            }
            default : CLIConfig();
        }
    }

    WebServer(Socket clientSoc) throws IOException {
        System.out.println("Status = " + STATUS);
        clientSocket = clientSoc;
        if(STATUS.equals("RUNNING")) StartServer();
        if(STATUS.equals("MAINTENANCE")) Maintenance();
        if(STATUS.equals("STOPPED")) StopServer();
        if(STATUS.equals("EXIT")) ExitP();
    }

    public void run() {
        System.out.println("New Communication Thread Started " + new Date());
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server: " + inputLine);
                out.println(inputLine);

                if (inputLine.trim().equals(""))
                    break;
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }

    public void StartServer(){
        System.out.println("starting server.");
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Connection Socket Created");
            try {
                while (true) {
                    System.out.println("Waiting for Connection");
                    new WebServer(serverSocket.accept());
                }
            } catch (IOException e) {
                System.err.println("Accept failed.");
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+PORT+".");
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port: "+PORT+".");
            }
        }
    }

    public void Maintenance(){
        System.out.println("Maintenance");
    }

    public void StopServer(){
        System.out.println("Server Stopped");
    }

    public int ExitP() throws IOException{
        System.out.println("Exiting program");
        return 0;
    }
}