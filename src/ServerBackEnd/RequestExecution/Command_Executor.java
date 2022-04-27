package ServerBackEnd.RequestExecution;

import NetWorkProtocol.NetworkProtocol;
import ServerBackEnd.ClientHandler;

abstract class Command_Executor {
    
    protected ClientHandler myClientHandler;

    public Command_Executor(ClientHandler myClientHandler){
        this.myClientHandler = myClientHandler;
    }

    public abstract void executeCommand(String incomingData);

}
