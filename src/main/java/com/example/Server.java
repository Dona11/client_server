package com.example;

import java.io.*;
import java.net.*;

public class Server extends Thread{
    
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public Server(Socket socket){

        this.client = socket;
    }

    public void run(){

        try {
            
            comunica();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void comunica() throws Exception{

        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        for(;;){

            stringaRicevuta = inDalClient.readLine();

            if(stringaRicevuta == null || stringaRicevuta.equals("FINE")){

                outVersoClient.writeBytes(stringaRicevuta + "Server in chiusura...\n");
                System.out.println("Echo sul sever in chiusura: " + stringaRicevuta);
                break;
            }else{

                outVersoClient.writeBytes(stringaRicevuta + "Ricevuta e trasmessa\n");
                System.out.println("echo sul server: " + stringaRicevuta);
            }

        }

        outVersoClient.close();
        inDalClient.close();
        System.out.println("Chiusura del socket");
        client.close();
    }

    public class MultiServer{

        public void start(){

            try {
                
                ServerSocket serverSocket = new ServerSocket(5000);
                for(;;){

                    System.out.println("Server in attesa...");
                    Socket socket = serverSocket.accept();
                    System.out.println("Server socket " + socket);
                    Server serverThread = new Server(socket);
                    serverThread.start();

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Errore durante l'istanza del server !");
                System.exit(1);
            }
        }

        public void main(String[] args){

            MultiServer tcpServer = new MultiServer();
            tcpServer.start();
            
        }
    }
 

}
