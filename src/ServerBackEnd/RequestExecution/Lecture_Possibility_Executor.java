package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;

import java.util.Map;
import java.util.HashMap;

import MysqlQueries.Sql_Interaction;
import NetWorkProtocol.NetworkProtocol;

class Lecture_Possibility_Executor extends Command_Executor{
    
    private Map<String,String[]> day_DayID_map;
    private Map<Integer,String> time_LectureID_map;

    public Lecture_Possibility_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
        day_DayID_map = new HashMap<String,String[]>();
        time_LectureID_map = new HashMap<Integer, String>();
        initDayMapper();
        initLectureMapper();
    }

    private void initDayMapper() {
        day_DayID_map.put("Mon", new String[]{"MondayID", "monday"});
        
        day_DayID_map.put("Tue", new String[]{"TuesdayID", "tuesday"});
        
        day_DayID_map.put("Wed", new String[]{"WednesdayID", "wednesday"});
        
        day_DayID_map.put("Thu", new String[]{"ThursdayID", "thursday"});
        
        day_DayID_map.put("Fri", new String[]{"FridayID", "friday"});
    }

    private void initLectureMapper() {
        time_LectureID_map.put(1, "Lecture1");

        time_LectureID_map.put(2, "Lecture2");

        time_LectureID_map.put(3, "Lecture3");

        time_LectureID_map.put(4, "Lecture4");
    }
    

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception {
        System.out.println("HERE IN TIMETABLE POSSIBILITY");
        MainServer mainServer = MainServer.getInstance();
        Sql_Interaction sql_Interaction = mainServer.getSqlInteration();
        String[] dateSplit = incomingData[1].split(" ");
        for(String dateData : dateSplit){
            System.out.println(dateData);
        }

        //get the column names for the database
        String dayCol = day_DayID_map.get(dateSplit[0])[0];
        String[] timeSplit = dateSplit[3].split(":");
        String dayEntity = day_DayID_map.get(dateSplit[0])[1]; 
        Integer time = Integer.parseInt(timeSplit[0] + timeSplit[1]);
        String lectureCol = getLectureNumberFromTime(time);


        String coursesID = myClientHandler.getUserInfo().split(NetworkProtocol.DATA_DELIMITER)[0]; 
        String lecture = (String)mainServer.runSqlQuery(sql_Interaction.createGetCurrentLectureQuery(coursesID, dayCol, dayEntity,lectureCol), Sql_Interaction.GET_LECTURE_FROM_DATE); 
        System.out.println(lecture);

        if(lecture.equals("NONE")){
            myClientHandler.getOutStream().writeUTF(NetworkProtocol.LECTURE_NOT_POSSIBLE);
        }else{
            myClientHandler.getOutStream().writeUTF(NetworkProtocol.LECTURE_POSSIBLE);
        }
        myClientHandler.getOutStream().flush();
        return true;
    }

    private String getLectureNumberFromTime(Integer time) {
        if(time >= 830 && time <= 1030){
            return time_LectureID_map.get(1);
        }else if(time <= 1230){
            return time_LectureID_map.get(2);
        }else if(time <= 1530){
            return time_LectureID_map.get(3);
        }else{
            return time_LectureID_map.get(4);
        }
        //why are we not checking for less than 17:30?
        //because the client validates lecture recording times before sending a request for lecture
    }    
}
