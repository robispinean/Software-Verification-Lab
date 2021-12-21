import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

import utils.FileUtils;

public class WebServer extends Thread {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    static int PORT = 8080;
    static int timeout = 100000;

    public static FileUtils fileUtils = new FileUtils();
    public static String parent = "src/main/TestSite/";
    public static String page = "src/main/TestSite/a.html";
    public static String maintenancePage = "src/main/TestSite/b.html";
    public static String notFound = "src/main/TestSite/notFound.html";

    protected static String STATUS = "RUNNING";

    public static void main(String[] args){

        Thread CLI = new Thread(() -> {
            try {
                CLIConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        CLI.start();

        if(STATUS.equals("RUNNING"))
            StartServer();
    }

    public static void CLIConfig() throws IOException {
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
        if(STATUS.equals("RUNNING") || STATUS.equals("MAINTENANCE")) clientSocket = clientSoc;
        if(STATUS.equals("MAINTENANCE")) Maintenance();
        if(STATUS.equals("STOPPED")) StopServer();
        if(STATUS.equals("EXIT")) ExitP();
    }

    public void run() {
        System.out.println("New Communication Thread Started " + new Date());
        BufferedOutputStream os = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            os = new BufferedOutputStream(clientSocket.getOutputStream());
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String path;
            String input = in.readLine();
            System.out.println("INPUT:" + input);
            if (input != null) {
                StringTokenizer parse = new StringTokenizer(input);
                String method = parse.nextToken().toUpperCase();
                path = fileUtils.checkFilePath(input);

                if (!method.equals("GET") && !method.equals("HEAD")) {
                    System.out.println("Not implemented " + method + " method.");

                    File file = new File(".", notFound);
                    long fileLength = file.length();
                    String fileType = fileUtils.fileType(file.getName());

                    byte[] fileData = fileUtils.readFileByte(file);
                    out.println("HTTP/1.1 501 Not Implemented");
                    out.println("Date :" + new Date());
                    out.println("Type: " + fileType);
                    out.println("Length: " + fileLength);
                    out.println();

                    os.write(fileData, 0, (int) fileLength);
                    os.flush();
                } else {
                    File file = null;
                    if (path.equals("")) {
                        path += page;
                        file = new File(".", path);
                    }
                    else {
                        file = new File(path);
                    }

                    if(!file.exists()){
                        System.out.println("File doesn't exit");
                        throw new FileNotFoundException();
                    }

                    long fileLength = file.length();

                    if (method.equals("GET")) {
                        byte[] fileData = fileUtils.readFileByte(file);

                        String response = fileUtils.reply(out, file, (int) fileLength);

                        os.write(fileData, 0, (int) fileLength);
                        os.flush();

                        System.out.println("Response:" + response);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            try {
                File file = new File(notFound);
                long fileLength = file.length();

                System.out.println("file location: " + file.getPath());
                byte[] fileData = fileUtils.readFileByte(file);

                String response = fileUtils.reply(out, file, (int) fileLength);

                os.write(fileData, 0, (int) fileLength);
                os.flush();

                System.out.println("Response:" + response);
            } catch (IOException ioe) {
                System.err.println("Problem with FileNotFound exception: " + ioe);
            }
        } catch (IOException e) {
            System.err.println("Problem with Communication Server: " + e);
        } finally {
            try {
                out.close();
                in.close();
            } catch (Exception e) {
                System.err.println("Error closing. " + e.getMessage());
            }
        }
    }

    public static void StartServer(){
        System.out.println("starting server.");
        try{
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(timeout);
            System.out.println("Connection Socket Created");
            try {
                while (STATUS.equals("RUNNING")) {
                    System.out.println("Waiting for Connection on port " + PORT);
                    WebServer server = new WebServer(serverSocket.accept());

                    Thread thread = new Thread(server);
                    thread.start();
                }
            } catch (IOException e) {
                System.err.println("Accept failed.");
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+PORT+".");
        }finally {
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
            PrintWriter os = new PrintWriter(clientSocket.getOutputStream());
            File file = new File(maintenancePage);
            System.out.println(fileUtils.reply(os, file, (int) file.length()));
            os.flush();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
        }
    }

    public static void StopServer(){
        System.out.println("Server Stopped");
    }

    public static void ExitP(){
        System.out.println("Exiting program");
    }
}