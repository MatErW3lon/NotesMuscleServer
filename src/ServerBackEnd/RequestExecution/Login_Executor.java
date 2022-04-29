package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.BackendGUI_Interface;
import ServerBackEnd.MainServer;
import NetWorkProtocol.NetworkProtocol;
import MysqlQueries.SqlQueries;
import NotesMuscles.io.*;

class Login_Executor extends Command_Executor{
    
    public Login_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        //System.out.println("THREAD IN LOGIN: " + Thread.currentThread());
        if(incomingData[0].equals(NetworkProtocol.User_LogIn)){
            String sqlResult = MainServer.getInstance().runSqlQuery(SqlQueries.createLoginUserQuery(incomingData[1], incomingData[2]));
            if(!sqlResult.equals(NetworkProtocol.LOGIN_FAILED)){    
                myClientHandler.setUserName(incomingData[1]);
                BackendGUI_Interface.ClientInformationHandler(incomingData[1], false, false);
                Thread.sleep(10);
                myClientHandler.getOutStream().writeUTF(NetworkProtocol.SuccessFull_LOGIN);
                return true;
            }else{
                myClientHandler.getOutStream().writeUTF(NetworkProtocol.LOGIN_FAILED);
                myClientHandler.getOutStream().flush();
                String clientIP = myClientHandler.getSocket().getInetAddress().toString();
                myClientHandler.closeEveryThing();
                System.out.println("INVALID LOGIN EXCEPTION THROWN");
                throw new InvalidFirstCommand(clientIP);
            }
        }else{
            return false;
        }
    }
}


