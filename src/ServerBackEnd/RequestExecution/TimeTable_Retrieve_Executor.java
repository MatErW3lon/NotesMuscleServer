package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;
import MysqlQueries.Sql_Interaction;
import java.sql.ResultSet;


class TimeTable_Retrieve_Executor extends Command_Executor{

    public TimeTable_Retrieve_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        //here we need to run multiple sql queries and combine them all into a form as shown below
        //lecM1/lecM2/lecM3/lecM4/lecT1/lecT2/lecT3/lecT4/lecW1/lecW2/lecW3/lecW4/lecTH1/lecTH2/lecTH3/lecTH4/LecF1/lecF2/lecF3/lecF4
        String bilkentID = incomingData[1];
        ResultSet[] resultSetsOfDayEntities = new ResultSet[5];
        ResultSet resultSet = (ResultSet) MainServer.getInstance().runSqlQuery(Sql_Interaction.crea, queryType)

        return true;
    }
}
