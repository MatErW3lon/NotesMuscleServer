package ServerBackEnd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

import NotesMuscles.io.*;
import ServerBackEnd.RequestExecution.RequestExecution;
import NetWorkProtocol.NetworkProtocol;

public class ClientHandler extends Thread {
    
    private MainServer mainServer;
    private Socket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private RequestExecution requestExecution;
    private String user_name, user_info;
    private Notes_Builder myNotes_Builder;

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
            
            String client_command = dataInputStream.readUTF();

            //notice that all commands will return true except the logOut
            while(executeCommand(client_command)){
                String GUIBuilder = user_name + " REQUESTED " + client_command.split(NetworkProtocol.DATA_DELIMITER)[0];
                BackendGUI_Interface.ClientInformationHandler(GUIBuilder, false);
                client_command = dataInputStream.readUTF();
            } 
            
        }catch(Exception exception){
            if(exception instanceof InvalidFirstCommand || exception instanceof InvalidCommandException || exception instanceof AccountCreationException){
                try{
                    exception.printStackTrace(new PrintStream(MainServer.requestExceptionFile));
                }catch(FileNotFoundException fileNotFoundException){
                    fileNotFoundException.printStackTrace(System.err);
                }
            }else if(exception instanceof NoSuchAlgorithmException){
                System.err.println("NoSUchAlgoEx on account creation -> password hash");
            }    
            exception.printStackTrace(System.err);
            }
            try {
                closeEveryThing();
            } catch (IOException e) {
                e.printStackTrace();
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
        return this.user_name;
    }

    public void setUserName(String username){
        this.user_name = username;
    }

    public String getUserInfo(){
        return this.user_info;
    }

    public void setUserInfo(String user_info){
        this.user_info = user_info;
    }

    public void setNotesBuilder(){
        myNotes_Builder = new Notes_Builder(this);
    }

    public Notes_Builder getNotesBuilder(){
        return this.myNotes_Builder;
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