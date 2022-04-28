package ServerBackEnd.RequestExecution;

import java.util.HashMap;
import java.util.Map;

import ServerBackEnd.*;
import NetWorkProtocol.NetworkProtocol;

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
    }

    public boolean executeCommand(String incomingData) throws Exception{
        String[] data = incomingData.split(NetworkProtocol.dataDelimiter);
        Command_Executor command_Executor = command_executors.get(data[0]);
        return command_Executor.executeCommand(data);
    }
}


