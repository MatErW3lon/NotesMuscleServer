package ServerBackEnd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import NotesMuscles.io.*;
import ServerBackEnd.RequestExecution.RequestExecution;
import NetWorkProtocol.NetworkProtocol;

/*
    CURRENT ASSUMPTION: the client thread cannot read and write at the same time
    THe server only writes to the user when it receives a request from the user
*/
public class ClientHandler extends Thread {
    
    MainServer mainServer;
    Socket clientSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    RequestExecution requestExecution;
    String command, username;

    public ClientHandler(Socket clientSocket, MainServer mainServer) throws IOException{
        requestExecution = new RequestExecution(this);
        this.clientSocket = clientSocket;
        this.mainServer = mainServer;
        try{
            dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(this.clientSocket.getOutputStream());
        }catch(IOException IOex){
            closeEveryThing();
            IOex.printStackTrace();
        }
    }

    public void run(){
        //we keep listening on a different thread
        try{
            //System.out.println(Thread.currentThread());
            dataOutputStream.writeUTF(NetworkProtocol.connectionEstablished);
            dataOutputStream.flush();
            String client_command = dataInputStream.readUTF();

            //notice that all commands will return true except the logOut
            while(executeCommand(client_command)){
                String GUIBuilder = username + " REQUESTED " + client_command.split(NetworkProtocol.dataDelimiter)[0];
                BackendGUI_Interface.ClientInformationHandler(GUIBuilder, false);
                client_command = dataInputStream.readUTF();
            } 

        }catch(Exception exception){
            if(exception instanceof InvalidFirstCommand || exception instanceof InvalidCommandException){
                try{
                    exception.printStackTrace(new PrintStream(MainServer.requestExceptionFile));
                }catch(FileNotFoundException fileNotFoundException){
                    fileNotFoundException.printStackTrace(System.err);
                }
            }else{
                exception.printStackTrace(System.err);
            }
            try {
                closeEveryThing();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }       
    }

    public boolean executeCommand(String command) throws Exception{
        return requestExecution.executeCommand(command);
    }

    public DataOutputStream getOutStream(){
        return this.dataOutputStream;
    }

    public DataInputStream getInputStream(){
        return this.dataInputStream;
    }

    public Socket getSocket(){
        return this.clientSocket;
    }

    public String getUserName(){
        return this.username;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public void closeEveryThing() throws IOException{
            if(dataInputStream != null){
                dataInputStream.close();
                dataInputStream = null;
            }
            if(dataOutputStream != null){
                dataOutputStream.close();
                dataOutputStream = null;
            }
            if(clientSocket != null){
                clientSocket.close();
                clientSocket = null;
            }
            requestExecution = null;
            mainServer.removeSpecificClient(this);
    //we need to notify the arraylist of client handlers to remove this client
    }
}


