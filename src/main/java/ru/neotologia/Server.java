package ru.neotologia;

import ru.neotologia.FileCreator.FileCreator;

import ru.neotologia.loger.Loger;
import ru.neotologia.serverSomthing.ServerSomthing;
import ru.neotologia.story.Story;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class Server {
    public static int Port = Integer.parseInt(readSettings());
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>();
    public static Story story;
    public static ServerSocket server;
    static boolean stop = false;

    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() throws IOException {
        FileCreator.create();

        try {
            server = new ServerSocket(Port);
            Loger.write("INFO", "Server ready, port  " + server.getLocalPort());
            story = new Story();

            while (true) {
                if (stop) {
                    break;
                }
                Socket socket = server.accept();
                System.out.println("new connection");
                serverList.add(new ServerSomthing(socket));
            }
        } catch (IOException e) {
            Loger.write("ERROR", " " + e.getMessage());
        }

    }

    public static String readSettings() {
        String str;
        try (BufferedReader reader = new BufferedReader(new FileReader("settings.txt"))) {
            str = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str.replaceAll("[^\\d]", "");
    }

    public static void stop() throws IOException {
        stop = true;
        if (server != null) server.close();
    }
}
