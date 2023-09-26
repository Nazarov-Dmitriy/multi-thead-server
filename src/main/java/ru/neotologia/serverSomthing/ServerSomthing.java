package ru.neotologia.serverSomthing;

import ru.neotologia.Server;
import ru.neotologia.loger.Loger;
import ru.neotologia.story.Story;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerSomthing extends Thread {

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    Boolean story = true;
    String nickname;

    public ServerSomthing(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        start();
        Server.story.printStory(out);
    }

    @Override
    public void run() {
        String word;
        try {
            nickname = in.readLine();
            Loger.write("INFO", "Пользователь " + nickname + " присоединился к чату");
            Server.story.printStory(out);
            if(Story.isEmptyStory())StoryMessage();
            sendMessages("Пользователь " + nickname + " присоединился к чату");
            System.out.println(nickname);
            try {
                while (true) {
                    word = in.readLine();
                    System.out.println(word);
                    if (word.contains("/exit")) {
                        sendMessages("Пользователь " + nickname + " покинул чат");
                        this.downService();
                        break;
                    }
                    Loger.write("Message", word);
                    Server.story.addStoryEl("[" + getDateTime() + "] " + word);
                    sendMessages(word);
                }
            } catch (NullPointerException e) {
                Loger.write("ERROR", " " + e.getMessage());
            }

        } catch (IOException e) {
            Loger.write("ERROR", " " + e.getMessage());
            this.downService();
        }
    }

    private void sendMessages(String msg) {
        for (ServerSomthing client : Server.serverList) {
            if (client == this ) {
                continue;
            }
            client.send("[" + getDateTime() + "] " + msg);
        }
    }

    private void StoryMessage() {
        for (ServerSomthing client : Server.serverList) {
            if (client == this ) {
                client.send("");
            }
        }
    }

    private void send(String msg) {
        out.write(msg + "\n");
        out.flush();
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                Loger.write("INFO", nickname + " завершил соединение");
                Thread.sleep(500);
                socket.close();
                in.close();
                out.close();
                for (ServerSomthing client : Server.serverList) {
                    if (client.equals(this)) client.interrupt();
                    Server.serverList.remove(this);
                }
            }
        } catch (IOException e) {
            Loger.write("ERROR", " " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDateTime() {
        Date time = new Date();
        SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss");
        return dt1.format(time);
    }
}

