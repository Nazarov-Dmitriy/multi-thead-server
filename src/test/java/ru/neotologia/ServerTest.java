package ru.neotologia;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.apache.commons.net.telnet.TelnetClient;
import java.io.*;

class ServerTest {
    @BeforeAll
    public static void init() throws IOException {
        Server.start();
    }


    @Test
    void Server() throws IOException {
        int port = Integer.parseInt(Server.readSettings());
        String host = "localhost";
        TelnetClient client = new TelnetClient();
        client.connect(host, port);
        Server.stop();
    }

}