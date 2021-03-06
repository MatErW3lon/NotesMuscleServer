package ServerBackEnd.RequestExecution;

import MysqlQueries.Sql_Interaction;
import NetWorkProtocol.NetworkProtocol;
import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;

class Create_Account_Executor extends Command_Executor{

    public Create_Account_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        MainServer mainServer = MainServer.getInstance();
        String bilkentID =  myClientHandler.getInputStream().readUTF(); // read for bilkentID
        String sqlResult = (String) mainServer.runSqlQuery(mainServer.getSqlInteration().createBilkentIDPresenceQuery(bilkentID), Sql_Interaction.BILKENTID_UNIQUENESS_QUERY);
        if(sqlResult.equals(NetworkProtocol.ACCOUNT_EXISTS_ERROR)){
            while(sqlResult.equals(NetworkProtocol.ACCOUNT_EXISTS_ERROR)){
                myClientHandler.getOutStream().writeUTF(NetworkProtocol.ACCOUNT_EXISTS_ERROR);
                myClientHandler.getOutStream().flush();
                bilkentID = myClientHandler.getInputStream().readUTF();
                if(bilkentID.equals(NetworkProtocol.CANCEL_ACC_REQUEST)){
                    return false;
                };
                sqlResult = (String) mainServer.runSqlQuery(mainServer.getSqlInteration().createBilkentIDPresenceQuery(bilkentID), Sql_Interaction.BILKENTID_UNIQUENESS_QUERY);    
            }
        }
        myClientHandler.getOutStream().writeUTF(NetworkProtocol.ACCOUNT_CONTINUE);
        myClientHandler.getOutStream().flush();
        myClientHandler.setUserName(myClientHandler.getSocket().getInetAddress().toString());
        return true;
    }
}
