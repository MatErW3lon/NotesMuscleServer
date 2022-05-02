package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;
import NetWorkProtocol.NetworkProtocol;
import MysqlQueries.Sql_Interaction;
import NotesMuscles.io.*;

class Login_Executor extends Command_Executor{
    
    public Login_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        //System.out.println("THREAD IN LOGIN: " + Thread.currentThread());
        if(incomingData[0].equals(NetworkProtocol.User_LogIn)){
            String sqlResult = (String) MainServer.getInstance().runSqlQuery(Sql_Interaction.createLoginUserQuery(incomingData[1], incomingData[2]), Sql_Interaction.LOGIN_QUERY );
            if(!(sqlResult == null)){    
                myClientHandler.setUserName(incomingData[1]);
                Thread.sleep(10);
                myClientHandler.getOutStream().writeUTF(NetworkProtocol.SuccessFull_LOGIN);
                return true;
            }else{
                myClientHandler.getOutStream().writeUTF(NetworkProtocol.LOGIN_FAILED);
                myClientHandler.getOutStream().flush();
                String clientIP = myClientHandler.getSocket().getInetAddress().toString();
                myClientHandler.closeEveryThing();
                throw new InvalidFirstCommand(clientIP);
            }
        }else{
            return false;
        }
    }
}


