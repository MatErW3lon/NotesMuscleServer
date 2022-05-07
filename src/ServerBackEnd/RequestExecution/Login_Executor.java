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
        MainServer mainServer = MainServer.getInstance();
        if(incomingData[0].equals(NetworkProtocol.USER_LOGIN)){
            String hashed_password = mainServer.getSha256Hash(incomingData[2]);
            String sqlResult = (String) mainServer.runSqlQuery(mainServer.getSqlInteration().createLoginUserQuery(incomingData[1], hashed_password), Sql_Interaction.LOGIN_QUERY );
            if(!(sqlResult == null)){    
                myClientHandler.setUserName(incomingData[1]);
                String userInfo = (String) mainServer.runSqlQuery(mainServer.getSqlInteration().createUserInfoQuery(incomingData[1]), Sql_Interaction.GET_USER_INFO_QUERY);
                myClientHandler.setUserInfo(userInfo);
                myClientHandler.setNotesBuilder();
                myClientHandler.getOutStream().writeUTF(NetworkProtocol.SUCCESSFULL_LOGIN + NetworkProtocol.DATA_DELIMITER + userInfo); //need to add user info here such as firstname, lastname and bilkentID
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