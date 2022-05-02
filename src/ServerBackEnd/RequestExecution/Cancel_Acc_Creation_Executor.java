package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;

/**
 * Cancel_Acc_Creation_Executor
 */
class Cancel_Acc_Creation_Executor extends Create_Account_Executor{

    public Cancel_Acc_Creation_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        return false;
    }
}

