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
       // String username = myClientHandler.getUserName();

        MainServer mainServer = MainServer.getInstance();
        String message = myClientHandler.getInputStream().readUTF();
        System.out.println(message);
        while(!message.equals(NetworkProtocol.END_GLOBAL_CHAT)){
            mainServer.broadCastGlobalChatFrom(myClientHandler, message);
            //myClientHandler.getOutStream().writeUTF(username +": " + message + "\n");
            message = myClientHandler.getInputStream().readUTF();
            if(message.equals(NetworkProtocol.END_GLOBAL_CHAT)){
                myClientHandler.getOutStream().writeUTF(NetworkProtocol.END_GLOBAL_CHAT);
            }
            System.out.println(message);
        }
        System.out.println("READY FOR ANOTHER COMMAND");
        myClientHandler.flush_global_messages = ClientHandler.BUFFER_MESSAGES;
        return true;
    }
}
