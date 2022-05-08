package ServerBackEnd.RequestExecution;

import NetWorkProtocol.NetworkProtocol;
import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;

class Global_Chat_Executor extends Command_Executor{

    public Global_Chat_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception {
        System.out.println("HERE IN GLOBAL CHAT");
        myClientHandler.flush_global_messages  = ClientHandler.FLUSH_MESSAGES;
        MainServer mainServer = MainServer.getInstance();
        String message = myClientHandler.getInputStream().readUTF();
        while(!message.equals(NetworkProtocol.END_GLOBAL_CHAT)){
            mainServer.broadCastGlobalChatFrom(myClientHandler, message);
        }
        myClientHandler.flush_global_messages = ClientHandler.BUFFER_MESSAGES;
        return true;
    }
}
