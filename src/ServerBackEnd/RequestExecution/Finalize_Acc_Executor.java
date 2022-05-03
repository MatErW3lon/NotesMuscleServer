package ServerBackEnd.RequestExecution;

import java.nio.charset.StandardCharsets;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;
import MysqlQueries.*;
import NetWorkProtocol.NetworkProtocol;
import NotesMuscles.io.*;

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
        if(countDataDelimiter(userInfo) != 9){
            throw new AccountCreationException(userInfo);
        }
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewUserRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewLoginRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewCoursesRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        createDir(getPaths(userInfo.split(NetworkProtocol.dataDelimiter)));
        return false;
    }

    private int countDataDelimiter(String userInfo){
        int count = 0;
        for(int i = 0; i < userInfo.length(); i++){
            if(userInfo.charAt(i) == NetworkProtocol.dataDelimiter.charAt(0)){
                count++;
            }
        }
        return count;
    }

    private void createDir(String[] paths) throws Exception{
        MainServer.getInstance().createNewDir(paths);
    }

    //new user data is of the form firstname/lastname/bilkentID/course1/course2/course3/course4/course5/username/password
    private String[] getPaths(String[] clientInfo){
        String[] paths = new String[6];
        for(int i = 0; i < paths.length; i++){
            paths[i] = clientInfo[i + 2];
        }
        return paths;
    }
}
