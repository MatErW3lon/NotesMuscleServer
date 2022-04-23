package ServerBackEnd;

import java.util.ArrayList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.File;


public class MainServer extends Thread{

    public static final File backendExFile;
    
    static{
        backendExFile = new File(System.getProperty("user.dir") + "\\assets\\BackEndExceptions.txt");
    }

    ServerSocket serverSocket;
    ArrayList<ClientHandler> clients;
    DataBaseManager dataBaseManager;

    private boolean keepServerRunning = true;

    public MainServer() throws Exception{
        serverSocket  = new ServerSocket(4444);
        dataBaseManager = new DataBaseManager();
        
        clients = new ArrayList<>(); 
    }

    public void run(){
        //we need the server to be on a different thread because of the GUI
        while(keepServerRunning){
            try{
                if(serverSocket == null){
                    //start the server again
                    serverSocket = new ServerSocket(4444);
                }
                Socket clientSocket = serverSocket.accept(); //wait for a new client a blocking method 
                System.out.println("NEW CLIENT CONNECTED");
                //if the server is closed on the blocked accept() the SocketException will be thrown which is normal
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandler.start();
                clients.add(clientHandler);
            }catch(IOException IOex){
                IOex.printStackTrace();
                try {
                    closeEveryThing();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setServerState(boolean keepServerRunning){
        this.keepServerRunning = keepServerRunning;
    }

    public void removeSpecificClient(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    //this method has to synched because multiple clients will try to execute sql queries
    public synchronized String runSqlQuery(String query){
        String query_result;
        try{
            query_result = dataBaseManager.runSqlQuery(query);
            return query_result;
        }catch(Exception e){
            e.printStackTrace(System.err);
            return "ERROR";
        }
    }

    public void closeEveryThing() throws IOException{
        //need to close all client sockets also
            for(ClientHandler client : clients){
                client.closeEveryThing();
            }
            if(serverSocket != null){
                serverSocket.close();
            }
            dataBaseManager = null;
    }
}
