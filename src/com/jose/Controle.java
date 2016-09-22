package com.jose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Controle extends Thread {

    private Socket socket;

    private static final List<Socket> sockets = new ArrayList<>();

    public Controle(Socket socket) {
        sockets.add(socket);
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("Escolha seu ingresso: \n\n" +
                    "Digite 1 para comprar: Pearl Jam - Lote 1 - R$ 150,00\n" +
                    "Digite 2 para comprar: Pearl Jam - Lote 2 - R$ 250,00\n" +
                    "Digite 3 para comprar: Pearl Jam - Lote 3 - R$ 350,00");
            String mensagem;
            while ((mensagem = bufferedReader.readLine()) != null) {
                if (mensagem.equals("close")) {
                    printWriter.println("Conexão finalizada\n");
                    socket.close();
                } else if (mensagem.equals("close server")) {
                    printWriter.println("Conexão finalizada");
                    for (Socket cliente : sockets) {
                        cliente.close();
                    }
                    Servidor.getServerSocket().close();
                } else {
                    if (mensagem.equals("1") || mensagem.equals("2") || mensagem.equals("3")) {
                        printWriter.println("Parabéns pela compra do lote " + mensagem + " do show do Pearl Jam!");
                        socket.close();
                    } else {
                        printWriter.println("Digite: 1, 2 ou 3 para escolher um lote a ser comprado");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Não foi possível manter uma conexão");
        }
    }
}
