package ServerBackEnd.RequestExecution;

import MysqlQueries.SqlQueries;
import MysqlQueries.SqlQueryType;
import NetWorkProtocol.NetworkProtocol;
import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;

class Create_Account_Executor extends Command_Executor{

    public Create_Account_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        String bilkentID =  myClientHandler.getInputStream().readUTF(); // read for bilkentID
        System.out.println(bilkentID);
        String sqlResult = (String) MainServer.getInstance().runSqlQuery(SqlQueries.createBilkentIDUniquessQuery(bilkentID), SqlQueryType.BILKENTID_UNIQUENESS_QUERY);
        if(sqlResult.equals(NetworkProtocol.ACCOUNT_EXISTS_ERROR)){
            while(sqlResult.equals(NetworkProtocol.ACCOUNT_EXISTS_ERROR)){
                System.out.println(NetworkProtocol.ACCOUNT_EXISTS_ERROR);
                myClientHandler.getOutStream().writeUTF(NetworkProtocol.ACCOUNT_EXISTS_ERROR);
                myClientHandler.getOutStream().flush();
                bilkentID = myClientHandler.getInputStream().readUTF();
                System.out.println(bilkentID);
                sqlResult = (String) MainServer.getInstance().runSqlQuery(SqlQueries.createBilkentIDUniquessQuery(bilkentID), SqlQueryType.BILKENTID_UNIQUENESS_QUERY);    
            }
        }
        System.out.println("CONFIRM");
        myClientHandler.getOutStream().writeUTF(NetworkProtocol.ACCOUNT_CONTINUE);
        myClientHandler.getOutStream().flush();
        myClientHandler.setUserName(myClientHandler.getSocket().getInetAddress().toString());
        return true;
    }
}
