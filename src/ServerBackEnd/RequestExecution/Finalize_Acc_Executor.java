package ServerBackEnd.RequestExecution;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;
import MysqlQueries.*;
import NetWorkProtocol.NetworkProtocol;
import NotesMuscles.io.*;

class Finalize_Acc_Executor extends Create_Account_Executor{

    private final int delimiterCount = 29;

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
        System.out.println(userInfo);
        if(countDataDelimiter(userInfo) != delimiterCount){
            System.out.println("ACCOUNT CREATION EXCEPTION WAS THROWN");
            throw new AccountCreationException(userInfo);
        }
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewUserRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewLoginRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);

        //now set the timetable. there are foreign key constraints so need to update days first
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewMondayRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewTuesdayRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewWednesdayRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewThursdayRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
        MainServer.getInstance().runSqlUpdate(Sql_Interaction.createNewFridayRecordUpdate(userInfo), Sql_Interaction.CREATE_NEW_ACCOUNT);
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

    //new user data is of the form firstname/lastname/bilkentid/dayID/lec1/lec2/lec3/lec4/dayID/lec1/lec2/lec3/lec4/dayID/lec1/lec2/lec3/lec4/dayID/lec1/lec2/lec3/lec4/dayID/lec1/lec2/lec3/lec4/username/password
    private String[] getPaths(String[] clientInfo){
        ArrayList<String> lectures = new ArrayList<>(6);
        for(int i = 4; i < clientInfo.length - 2; i++){
            if(i == 8 || i == 13 || i == 18 || i == 23){
                continue;
            }
            if(lectures.contains(clientInfo[i]) || clientInfo[i].equalsIgnoreCase("NONE")){
                continue;
            }
            lectures.add(clientInfo[i]);
        }
        String[] paths = new String[lectures.size() + 1];
        paths[0] = clientInfo[2];
        for(int i = 1; i < lectures.size() + 1; i++){
            paths[i] = lectures.get(i-1);
        }
        return paths;
    }
}
