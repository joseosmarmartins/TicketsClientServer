package com.jose;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static ServerSocket serverSocket = null;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(3331);
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                new Controle(socket);
            }
        } catch (IOException e) {
            System.out.println("Conexão não estabelecida!");
            System.exit(0);
        }
    }

    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void setServerSocket(ServerSocket serverSocket) {
        Servidor.serverSocket = serverSocket;
    }
}
