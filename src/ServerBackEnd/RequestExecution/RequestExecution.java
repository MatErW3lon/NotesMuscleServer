package ServerBackEnd.RequestExecution;

import java.util.HashMap;
import java.util.Map;

import ServerBackEnd.*;
import NetWorkProtocol.NetworkProtocol;
import NotesMuscles.io.*;

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
        command_executors.put(NetworkProtocol.User_LogIn, new Login_Executor(myClientHandler));

        //init LogOut_Executor;
        command_executors.put(NetworkProtocol.User_LogOut, new LogOut_Executor(myClientHandler));

        //init Imaga_Executor;
        command_executors.put(NetworkProtocol.Image_Send, new Image_Executor(myClientHandler));

        //init ImageStop_Executor;
        command_executors.put(NetworkProtocol.Image_Stop, new ImageStop_Executor(myClientHandler));

        //init CreateAccount_Executor;
        command_executors.put(NetworkProtocol.Create_Account_Request, new Create_Account_Executor(myClientHandler));

        //init Cancel Executor;
        command_executors.put(NetworkProtocol.Cancel_Acc_Request, new Cancel_Acc_Creation_Executor(myClientHandler));

        //init FinalizeAccout_Executor;
        command_executors.put(NetworkProtocol.Acc_Info_Ready, new Finalize_Acc_Executor(myClientHandler));

        //init TimeTable Retriever
        command_executors.put(NetworkProtocol.RETRIEVE_TIMETABLE_REQUEST, new TimeTable_Retrieve_Executor(myClientHandler));
    }

    public boolean executeCommand(String incomingData) throws Exception{
        String[] data = incomingData.split(NetworkProtocol.dataDelimiter);
        if(!command_executors.containsKey(data[0])){
            throw new InvalidCommandException(data[0], myClientHandler.getSocket().getInetAddress().toString()); 
        }
        Command_Executor command_Executor = command_executors.get(data[0]);
        return command_Executor.executeCommand(data);
    }
}


