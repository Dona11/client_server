package com.example;

import java.io.*;
import java.net.*;

public class Client {
    
    String nomeServer = "localhost";   //indirizzo del server
    int portaServer = 5000;            //porta del server
    Socket mioSocket;
    BufferedReader tastiera;           //buffer per l'input da tastiera
    String stringaUtente;              //stringa inserita da utente
    String stringaRicevutaDalServer;   //stringa ricevuta dal server
    DataOutputStream outVersoServer;   //Stream di output
    BufferedReader inDalServer;        //stream di input



    protected Socket connetti() throws IOException{

        System.out.println("Il CLIENT è in esecuzione");

        try {
            //per input da tastiera
            tastiera = new BufferedReader(new InputStreamReader(System.in));

            //creo un socket
            mioSocket = new Socket(nomeServer, portaServer);


            outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));

        } catch (UnknownHostException e) {
            System.out.println("Host sconosciuto");
        }
        catch(Exception e){

            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }

        return mioSocket;
    }

    public void comunica(){

        try {
            //leggo la riga da inviare al server
            System.out.println("Inserisci la stringa da trasmettere al server" + '\n');
            stringaUtente = tastiera.readLine();
            //invio la riga al server e poi attendo
            System.out.println("Invio la riga al server e attendo...");
            outVersoServer.writeBytes(stringaUtente + '\n');
            //leggo la risposta dal server
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println("Ecco la risposta del server: " + stringaRicevutaDalServer + '\n');
            //chiudo la connessione
            System.out.println("Elaborazione finita, chiusura connessione...");
            mioSocket.close();


        } catch (Exception e) {
            
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }
        
    }

    public static void main( String[] args ) throws IOException
    {

        Client cliente = new Client();
        cliente.connetti();
        cliente.comunica();
    }

}
