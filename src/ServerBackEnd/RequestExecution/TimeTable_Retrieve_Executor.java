package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;

import java.nio.charset.StandardCharsets;

import MysqlQueries.Sql_Interaction;
import NetWorkProtocol.NetworkProtocol;

class TimeTable_Retrieve_Executor extends Command_Executor{

    public TimeTable_Retrieve_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        //here we need to run multiple sql queries and combine them all into a form as shown below
        //lecM1/lecM2/lecM3/lecM4/lecT1/lecT2/lecT3/lecT4/lecW1/lecW2/lecW3/lecW4/lecTH1/lecTH2/lecTH3/lecTH4/LecF1/lecF2/lecF3/lecF4
        System.out.println("HERE in timetable");
        String bilkentID = incomingData[1];
        String[] resultSetsOfDayEntities = new String[5];
        MainServer mainServer = MainServer.getInstance();
        Sql_Interaction sql_interaction = mainServer.getSqlInteration();
        resultSetsOfDayEntities[0] = (String) mainServer.runSqlQuery(sql_interaction.createMondayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        resultSetsOfDayEntities[1] = (String) mainServer.runSqlQuery(sql_interaction.createTuesdayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        resultSetsOfDayEntities[2] = (String) mainServer.runSqlQuery(sql_interaction.createWednesdayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        resultSetsOfDayEntities[3] = (String) mainServer.runSqlQuery(sql_interaction.createThursdayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        resultSetsOfDayEntities[4] = (String) mainServer.runSqlQuery(sql_interaction.createFridayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        String writeTimetable = "";
        for(String result : resultSetsOfDayEntities){
            writeTimetable += result + NetworkProtocol.DATA_DELIMITER;
        }
        byte[] bytes = writeTimetable.getBytes(StandardCharsets.UTF_8);
        myClientHandler.getOutStream().writeInt(bytes.length);
        myClientHandler.getOutStream().flush();
        //pause for sending bytes
        myClientHandler.getInputStream().readInt();

        myClientHandler.getOutStream().write(bytes, 0, bytes.length);
        myClientHandler.getOutStream().flush();
        return true;
    }
}
