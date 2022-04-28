package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;

abstract class Command_Executor {
    
    protected ClientHandler myClientHandler;

    public Command_Executor(ClientHandler myClientHandler){
        this.myClientHandler = myClientHandler;
    }

    public abstract boolean executeCommand(String[] incomingData) throws Exception;

}
