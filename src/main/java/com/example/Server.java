package com.example;

import java.io.*;
import java.net.*;

public class Server {
    
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public Socket attendi(){

        try {
            
            System.out.println("Il SERVER è entrato in esecuzione");
            //creo un server  sulla porta 5000
            server = new ServerSocket(5000);
            //rimane in attesa di un client
            client = server.accept();
            //chiudo il server por inibire altri client, anche se non si chiude quasi mai
            server.close();
            //associo due oggetti al socket del client per effettuare la scrittura e la lettura
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());

        } catch (Exception e) {
            
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server!");
            System.exit(1);
        }

        return client;
    }

    public void comunica(){

        try {
            
            //rimango in attesa dell stringa trasmessa dal client
            System.out.println("Benvenuto client, scrivi una frase e la trasformo in maiuscolo. Attendo...");
            stringaRicevuta = inDalClient.readLine();
            System.out.println("Stringa ricevuta dal client: " + stringaRicevuta);
            //la modifico e la rimando al client
            stringaModificata = stringaRicevuta.toUpperCase();
            System.out.println("Invio la stringa modifica al client");
            outVersoClient.writeBytes(stringaModificata + '\n');
            //termina l'elaborazione sul serve: chiudo la connessione dal client
            System.out.println("SERVER: fine elaborazione...PASSO E CHIUDO!");
            client.close();

        } catch (Exception e) {
            
        }
    }

    public static void main( String[] args ) throws IOException
    {

        Server server = new Server();
        server.attendi();
        server.comunica();
    }

}
