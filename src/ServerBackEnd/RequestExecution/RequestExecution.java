package ServerBackEnd.RequestExecution;

import java.util.HashMap;
import java.util.Map;

import ServerBackEnd.*;
import NetWorkProtocol.NetworkProtocol;
import NotesMuscles.io.*;
//import NotesMuscles.util.HashMap;

public class RequestExecution {
   
    private ClientHandler myClientHandler;
    private Map<String, Command_Executor> command_executors;

    public RequestExecution(ClientHandler myClientHandler){
        this.myClientHandler = myClientHandler;
        command_executors = new HashMap<String, Command_Executor>();
        initializeMap();
    }

    private void initializeMap(){
        //init Login_Executor;
        command_executors.put(NetworkProtocol.USER_LOGIN, new Login_Executor(myClientHandler));

        //init LogOut_Executor;
        command_executors.put(NetworkProtocol.USER_LOGOUT, new LogOut_Executor(myClientHandler));

        //init Imaga_Executor;
        command_executors.put(NetworkProtocol.IMAGE_SEND, new Image_Executor(myClientHandler));

        //init ImageStop_Executor;
        command_executors.put(NetworkProtocol.IMAGE_STOP, new ImageStop_Executor(myClientHandler));

        //init CreateAccount_Executor;
        command_executors.put(NetworkProtocol.CREATE_ACCOUNT_REQUEST, new Create_Account_Executor(myClientHandler));

        //init Cancel Executor;
        command_executors.put(NetworkProtocol.CANCEL_ACC_REQUEST, new Cancel_Acc_Creation_Executor(myClientHandler));

        //init FinalizeAccout_Executor;
        command_executors.put(NetworkProtocol.ACC_INFO_READY, new Finalize_Acc_Executor(myClientHandler));

        //init TimeTable Retriever
        command_executors.put(NetworkProtocol.RETRIEVE_TIMETABLE_REQUEST, new TimeTable_Retrieve_Executor(myClientHandler));

        //init TimeTable Edit 
        command_executors.put(NetworkProtocol.EDIT_TIMETABLE_REQUEST, new Edit_TimeTable_Executor(myClientHandler));

        //init Notes Retriever
        command_executors.put(NetworkProtocol.RETRIEVE_NOTES_REQUEST, new Notes_Retrieve_Executor(myClientHandler));

        //init Account Deletion
        command_executors.put(NetworkProtocol.DELETE_ACCOUNT_REQUEST, new Delete_Acc_Executor(myClientHandler));

        //init Global Chat Executor
        command_executors.put(NetworkProtocol.PULL_GLOBAL_CHAT, new Global_Chat_Executor(myClientHandler));
    }

    public boolean executeCommand(String incomingData) throws Exception{
        String[] data = incomingData.split(NetworkProtocol.DATA_DELIMITER);
        if(!command_executors.containsKey(data[0])){
            throw new InvalidCommandException(data[0], myClientHandler.getSocket().getInetAddress().toString()); 
        }
        Command_Executor command_Executor = command_executors.get(data[0]);
        return command_Executor.executeCommand(data);
    }
}