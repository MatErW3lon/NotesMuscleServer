package ServerBackEnd.RequestExecution;

import java.nio.charset.StandardCharsets;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;
import MysqlQueries.*;

class Finalize_Acc_Executor extends Create_Account_Executor{

    public Finalize_Acc_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    //we need to end account creation now
    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        byte[] accountInfoBytes = new byte[Integer.parseInt(incomingData[1])];
        myClientHandler.getOutStream().writeUTF("READY");
        myClientHandler.getOutStream().flush();
        myClientHandler.getInputStream().readFully(accountInfoBytes);
        String userInfo = new String(accountInfoBytes, StandardCharsets.UTF_8);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewUserRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewLoginRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewCoursesRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        return false;
    }
}
