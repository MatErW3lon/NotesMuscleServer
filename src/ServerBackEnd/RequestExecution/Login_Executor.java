package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;

class Login_Executor extends Command_Executor{
    
    public Login_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

}
