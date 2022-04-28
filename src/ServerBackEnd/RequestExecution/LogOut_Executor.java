package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.BackendGUI_Interface;
import ServerBackEnd.MainServer;
import NetWorkProtocol.NetworkProtocol;
import MysqlQueries.SqlQueries;
import NotesMuscles.util.*;

class LogOut_Executor extends Command_Executor{
    
    public LogOut_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    //log out must always return false to log the user out;
    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        myClientHandler.getOutStream().writeUTF(NetworkProtocol.User_LogOut);
        myClientHandler.getOutStream().flush();
        BackendGUI_Interface.ClientInformationHandler(myClientHandler.getUserName(), false, true);
        Thread.sleep(10);
        myClientHandler.closeEveryThing();
        return false;
    }

}
