package ServerBackEnd;

import java.io.IOException;

import NetWorkProtocol.NetworkProtocol;

public class RequestExecution {
   
    private ClientHandler myClientHandler;

    public RequestExecution(ClientHandler myClientHandler){
        this.myClientHandler = myClientHandler;
    }

    public void executeCommand(String command){
        if(command.equals(NetworkProtocol.User_LogOut)){
            logUserOut();
        }
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
