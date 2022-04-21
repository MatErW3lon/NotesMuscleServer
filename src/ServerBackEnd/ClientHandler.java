package ServerBackEnd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import NetWorkProtocol.NetworkProtocol;
import MysqlQueries.SqlQueries;

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
       
        try{
            String client_command = dataInputStream.readUTF();
            if(client_command.split(NetworkProtocol.splitCharacter)[0].equals(NetworkProtocol.User_LogIn)){
                //check for a login
                String[] login_credentials = client_command.split(NetworkProtocol.splitCharacter);
                String sqlResult = mainServer.runSqlQuery(SqlQueries.createLoginUserQuery(login_credentials[1], login_credentials[2]));
                if(!sqlResult.equals("ERROR")){
                    Thread.sleep(50);
                    //we need to write confirmation for login
                    dataOutputStream.writeUTF(NetworkProtocol.SuccessFull_LOGIN);
                    client_command = dataInputStream.readUTF();
                    while(!client_command.equals(NetworkProtocol.User_LogOut) && client_command != NetworkProtocol.Invalid_LogOut){
                        executeCommand(client_command);
                    }
                }

            }else{
                //if it was an invalid first command, end transmission
                throw new InvalidFirstCommand();
            }
            
            
        }catch(IOException IOex){
            IOex.printStackTrace(System.err);
        }catch(InvalidFirstCommand invalidFirstCommand){
            try {
                invalidFirstCommand.printStackTrace(new PrintStream(MainServer.backendExFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace(System.err);
            }
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }finally{
            try {
                closeEveryThing();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
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

class InvalidFirstCommand extends Exception{
    public InvalidFirstCommand(){
        super("THE FIRST COMMAND WAS NOT LOGIN");
    }
}
