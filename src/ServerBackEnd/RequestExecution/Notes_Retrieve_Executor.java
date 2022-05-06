package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;
import MysqlQueries.Sql_Interaction;
import NetWorkProtocol.NetworkProtocol;

import java.util.ArrayList;

class Notes_Retrieve_Executor extends Command_Executor{
    
    public Notes_Retrieve_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception {
        System.out.println("HERE IN NOTES");
        String bilkentID = incomingData[1];
        String[] lectures_over_week = new String[5];
        MainServer mainServer = MainServer.getInstance();
        Sql_Interaction sql_interaction = mainServer.getSqlInteration();
        lectures_over_week[0] = (String) mainServer.runSqlQuery(sql_interaction.createMondayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        lectures_over_week[1] = (String) mainServer.runSqlQuery(sql_interaction.createTuesdayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        lectures_over_week[2] = (String) mainServer.runSqlQuery(sql_interaction.createWednesdayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        lectures_over_week[3] = (String) mainServer.runSqlQuery(sql_interaction.createThursdayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        lectures_over_week[4] = (String) mainServer.runSqlQuery(sql_interaction.createFridayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        String writeTimetable = "";
        for(String result : lectures_over_week){
            writeTimetable += result + NetworkProtocol.DATA_DELIMITER;
        }
        String[] courses = getCourses(writeTimetable.split(NetworkProtocol.DATA_DELIMITER));
        //first we need to write the number of courses this user takes
        myClientHandler.getOutStream().writeInt(courses.length);
        myClientHandler.getOutStream().flush();
        return true;
    }

     //lecM1/lecM2/lecM3/lecM4/lecT1/lecT2/lecT3/lecT4/lecW1/lecW2/lecW3/lecW4/lecTH1/lecTH2/lecTH3/lecTH4/LecF1/lecF2/lecF3/lecF4
    private String[] getCourses(String[] clientInfo){
        ArrayList<String> lectures_list = new ArrayList<>(1);
        for(int i = 0; i < clientInfo.length; i++){
            if(lectures_list.contains(clientInfo[i]) || clientInfo[i].equalsIgnoreCase("NONE")){
                continue;
            }
            lectures_list.add(clientInfo[i]);
        }
        String[] lectures_array = new String[lectures_list.size()];
        for(int i = 0; i < lectures_list.size(); i++){
            System.out.println(lectures_list.get(i));
            lectures_array[i] = lectures_list.get(i);
        }
        return lectures_array;
    }
}
