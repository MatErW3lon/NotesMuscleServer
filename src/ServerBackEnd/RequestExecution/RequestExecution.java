package ServerBackEnd.RequestExecution;

import java.io.IOException;
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
    }

    private void initializeMap(){
        //init Login_Executor;
        command_executors.put(NetworkProtocol.User_LogIn, new Login_Executor(myClientHandler));
    }

    public void executeCommand(String incomingData){
        String command = incomingData.split(NetworkProtocol.dataDelimiter)[0];
        Command_Executor command_Executor = command_executors.get(command);
        command_Executor.executeCommand(incomingData);
    }

    private void logUserOut(){
        try {
            myClientHandler.getOutStream().writeUTF(NetworkProtocol.User_LogOut);
            myClientHandler.getOutStream().flush();
            BackendGUI_Interface.ClientInformationHandler(myClientHandler.getUserName(), false, true);
            Thread.sleep(10);
            myClientHandler.closeEveryThing();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(InterruptedException interruptedException){}
        
    }

}
