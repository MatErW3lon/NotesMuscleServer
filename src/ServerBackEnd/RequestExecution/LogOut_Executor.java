package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.BackendGUI_Interface;


class LogOut_Executor extends Command_Executor{
    
    public LogOut_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    //log out must always return false to log the user out;
    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        BackendGUI_Interface.ClientInformationHandler(myClientHandler.getUserName() + " REQUESTED " + "LOGOUT", false);
        BackendGUI_Interface.ClientInformationHandler(myClientHandler.getUserName(), false);
        return false;
    }
}
