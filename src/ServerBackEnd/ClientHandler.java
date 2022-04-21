package ServerBackEnd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import NetWorkProtocol.NetworkProtocol;

public class ClientHandler extends Thread {
    
    MainServer mainServer;
    Socket clientSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;


    public ClientHandler(Socket clientSocket, MainServer mainServer) throws IOException{
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
        //we have created a new client handler. the first thing we need to do is check login credentials
        boolean valid_login = false;


        try{
            String client_command = dataInputStream.readUTF();
        while(!client_command.equals(NetworkProtocol.User_LogOut) && client_command != NetworkProtocol.Invalid_LogOut){

        }
            
        }catch(IOException IOex){
            try {
                closeEveryThing();
            } catch (IOException e) {
                e.printStackTrace();
            }
            IOex.printStackTrace();
        }
        
    }
    
    public void executeCommand(String command){
        //this method will include all the sql queries and accessing files
    }


    public void closeEveryThing() throws IOException{
            if(dataInputStream != null){
                dataInputStream.close();
            }
            if(dataOutputStream != null){
                dataOutputStream.close();
            }

            if(clientSocket != null){
                clientSocket.close();
            }
            mainServer.removeSpecificClient(this);
    //we need to notify the arraylist of client handlers to remove this client
    }

}
