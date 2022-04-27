package ServerBackEnd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import ServerBackEnd.RequestExecution.RequestExecution;
import NetWorkProtocol.NetworkProtocol;
import MysqlQueries.SqlQueries;

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
    String command;
    String username;

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
        //we have created a new client handler. the first thing we need to do is check login credentials
        boolean exceptionOccured = false;
        try{
            dataOutputStream.writeUTF(NetworkProtocol.connectionEstablished);
            dataOutputStream.flush();
            String client_command = dataInputStream.readUTF();
            String[] login_credentials = client_command.split(NetworkProtocol.dataDelimiter);
            //check for a login
            if(login_credentials[0].equals(NetworkProtocol.User_LogIn)){
                String sqlResult = mainServer.runSqlQuery(SqlQueries.createLoginUserQuery(login_credentials[1], login_credentials[2]));
                if(!sqlResult.equals(NetworkProtocol.LOGIN_FAILED)){    
                    username = login_credentials[1];
                    BackendGUI_Interface.ClientInformationHandler(username, false, false);
                    Thread.sleep(10);
                    //we need to write confirmation for login
                    dataOutputStream.writeUTF(NetworkProtocol.SuccessFull_LOGIN);
                    client_command = dataInputStream.readUTF();
                    System.out.println(client_command);
                    if(client_command.equals(NetworkProtocol.User_LogOut) && client_command != NetworkProtocol.Invalid_LogOut){
                        executeCommand(client_command);
                    }
                    while(!client_command.equals(NetworkProtocol.User_LogOut) && client_command != NetworkProtocol.Invalid_LogOut){
                        executeCommand(client_command);
                    }
                }else{
                    loginErrorOccured();
                }

            }else{
                //if it was an invalid first command, end transmission
                loginErrorOccured();
            }
            
            
        }catch(IOException IOex){
            exceptionOccured = true;
            IOex.printStackTrace(System.err);
        }catch(InvalidFirstCommand invalidFirstCommand){
            exceptionOccured = true;
            try {
                invalidFirstCommand.printStackTrace(new PrintStream(MainServer.backendExFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace(System.err);
            }
        } catch (InterruptedException e) {
            exceptionOccured = true;
            e.printStackTrace(System.err);
        }finally{
            if(exceptionOccured){
                try{
                    closeEveryThing();
                }catch(IOException io){}
            }
        }   
    }

    private void loginErrorOccured() throws IOException, InvalidFirstCommand{
        dataOutputStream.writeUTF(NetworkProtocol.LOGIN_FAILED);
        dataOutputStream.flush();
        String clientIP = clientSocket.getInetAddress().toString();
        closeEveryThing();
        System.out.println("INVALID LOGIN EXCEPTION THROWN");
        throw new InvalidFirstCommand(clientIP);
    }
    
    public void executeCommand(String command){
        requestExecution.executeCommand(command);
        //this method will include all the sql queries and accessing files
    }

    public DataOutputStream getOutStream(){
        return this.dataOutputStream;
    }

    public String getUserName(){
        return this.username;
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

class InvalidFirstCommand extends Exception{
    public InvalidFirstCommand(String fromIPAdr){
        super("THE FIRST COMMAND WAS NOT LOGIN FROM USER " + fromIPAdr);
    }
}
