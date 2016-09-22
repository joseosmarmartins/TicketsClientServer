package com.jose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        try {
            System.out.println("Inicializando conexão");
            final Socket socket = new Socket("localhost", 3331);
            System.out.println("Cliente conectado ao servidor!");
            new Thread() {
                @Override
                public void run() {
                    String mensagem;
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        while ((mensagem = bufferedReader.readLine()) != null) {
                            System.out.println(mensagem);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Escolha o lote desejado para o show: ");

            while (true) {
                Scanner scanner = new Scanner(System.in);
                String mensagem = scanner.nextLine();
                if (mensagem.equals("sair")) {
                    printWriter.println("close");
                    scanner.close();
                    System.exit(0);
                }
                printWriter.println(mensagem);
            }
        } catch (IOException e) {
            System.out.println("Falha na conexão!");
            e.printStackTrace();
        }
    }
}
