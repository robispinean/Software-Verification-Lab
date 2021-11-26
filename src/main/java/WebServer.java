import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;

import utils.FileUtils;

public class WebServer extends Thread {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    //private Socket clientSocket;
    static final int PORT = 8088;

    private static FileUtils fileUtils = new FileUtils();
    private static String page = "src/main/TestSite/a.html";
    private static String maintenancePage = "src/main/TestSite/b.html";

    protected static String STATUS = "RUNNING";

    public static void main(String[] args) throws IOException{

        WebServer webServer = null;
        Thread CLI = new Thread(() -> CLIConfig());
        CLI.start();

        if(STATUS.equals("RUNNING"))
            StartServer();
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
            PrintStream os = new PrintStream(clientSocket.getOutputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String path;

            while ((path = fileUtils.checkFilePath(in.readLine())) != null) {
                File file = new File(path);
                if(file.exists()){
                    try {
                        System.out.println(fileUtils.reply(os, file, (int) file.length()));
                    } catch (Exception e) {
                        System.out.println("Can't Read " + path);
                    }
                } else System.out.println("error finding file");
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
        }
    }

    public static void StartServer(){
        System.out.println("starting server.");
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Connection Socket Created");
            try {
                while (STATUS.equals("RUNNING")) {
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

    public static void Maintenance(){
        System.out.println("Server in Maintenance");
        try {
            serverSocket = new ServerSocket(8888);
            clientSocket = serverSocket.accept();
            PrintStream os = new PrintStream(clientSocket.getOutputStream());
            File file = new File(maintenancePage);
            try {
                System.out.println(fileUtils.reply(os, file, (int) file.length()));
            } catch (Exception e) {
                System.err.println("Maintenance html file cannot be read.");
            }
            os.flush();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
        }
    }

    public void StopServer(){
        System.out.println("Server Stopped");
    }

    public void ExitP() throws IOException{
        System.out.println("Exiting program");
    }
}