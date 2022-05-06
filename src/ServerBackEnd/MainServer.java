package ServerBackEnd;

import java.util.ArrayList;

import ServerBackEnd.DataBaseManager.DataBaseManager;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.FileNotFoundException;

import MysqlQueries.Sql_Interaction;
import NotesMuscles.io.*;
import NotesMuscles.util.DirectoryManager;

public class MainServer extends Thread{

    public static final int OCR_SERVER_PORT = 4445;
    public static final File requestExceptionFile;
    private static final File queryExceptionFile;
    private static final File dirManagerFile;
    static MainServer mainServer;
    
    static{
        requestExceptionFile = new File(System.getProperty("user.dir") + "\\assets\\RequestExStream.txt");
        queryExceptionFile = new File(System.getProperty("user.dir") + "\\assets\\SqlStream.txt");
        dirManagerFile = new File(System.getProperty("user.dir") + "\\assets\\DirectoryManagerStream.txt");
        mainServer = null;
    }

    ServerSocket serverSocket;
    ArrayList<ClientHandler> clients;
    DataBaseManager dataBaseManager;
    DirectoryManager directoryManager;
    Sql_Interaction sql_Interaction;
    private boolean keepServerRunning = true;

    public static synchronized MainServer getInstance() throws Exception{
        if(mainServer == null){
            MainServer.mainServer = new MainServer();
        }
        return MainServer.mainServer;
    }

    public static void resetInstance() {
        MainServer.mainServer = null;
    }

    private MainServer() throws Exception{
        directoryManager = new DirectoryManager();
        serverSocket  = new ServerSocket(4444);
        sql_Interaction = new Sql_Interaction();
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
                //System.out.println("NEW CLIENT CONNECTED");
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
        BackendGUI_Interface.ClientInformationHandler(clientHandler.getUserName() + " RAN INTO AN EXCEPTION ", false);
        clients.remove(clientHandler);
    }

    public synchronized Sql_Interaction getSqlInteration(){
        return this.sql_Interaction;
    }

    //this method has to be synched because multiple clients will try to execute sql queries
    public synchronized Object runSqlQuery(String query, Integer queryType){
        Object query_result = null;
        try{
            query_result = MainServer.getInstance().dataBaseManager.SqlQuery(query, queryType);
            return query_result;
        }catch(Exception e){
            if(e instanceof LoginQueryFailedException){
                try {
                    e.printStackTrace(new PrintStream(queryExceptionFile));
                } catch (IOException e1) {
                    e1.printStackTrace(System.err);
                }
            }else{
                e.printStackTrace(System.err);
            }
            return query_result;
        }
    }

    public synchronized void runSqlUpdate(String update, Integer updateType){
        try{
            MainServer.getInstance().dataBaseManager.SqlUpdate(update, updateType);
        }catch(Exception e){
            e.printStackTrace(System.err);
        }
    }
    
    public synchronized void createNewDir(String[] clientInfo){
        try{
            MainServer.getInstance().directoryManager.createClientDirOnNewAccount(clientInfo);
        }catch(Exception e){
            if(e instanceof DirCreationException){
                try {
                    e.printStackTrace(new PrintStream(dirManagerFile));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace(System.err);
                }
            }else{
                e.printStackTrace(System.err);
            }
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
