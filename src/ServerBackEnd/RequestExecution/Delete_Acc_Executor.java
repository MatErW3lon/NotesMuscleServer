package ServerBackEnd.RequestExecution;

import MysqlQueries.Sql_Interaction;
import NetWorkProtocol.NetworkProtocol;
import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;

class Delete_Acc_Executor extends Command_Executor {

    public Delete_Acc_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception {
        MainServer mainServer = MainServer.getInstance();
        String userID = myClientHandler.getUserInfo().split(NetworkProtocol.DATA_DELIMITER)[0];
        System.out.println(userID);
        mainServer.deleteUserDir("test");
        //need to run sql queries here. need to make sure that the entities with foreign keys in other entities are deleted first
        String[] getSqlDeletionUpdates = mainServer.getSqlInteration().createAccDeletionsUpdates(userID);
        for(int i = 0; i < getSqlDeletionUpdates.length; i++){
            mainServer.runSqlUpdate(getSqlDeletionUpdates[i], Sql_Interaction.ACCOUNT_DELETION_UPDATE);
        }
        return false;
    }
}
