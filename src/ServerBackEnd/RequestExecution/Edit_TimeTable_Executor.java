package ServerBackEnd.RequestExecution;

import MysqlQueries.Sql_Interaction;
import NetWorkProtocol.NetworkProtocol;
import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;

class Edit_TimeTable_Executor extends Command_Executor{

    private final String[][] dayEntities = new String[][]{
        {"monday", "M"},
        {"tuesday", "T"},
        {"wednesday", "W"},
        {"thursday", "Th"},
        {"friday", "F"}
    };

    private final String[] lectureCols = new String[]{"Lecture1", "Lecture2", "Lecture3", "Lecture4"};

    private MainServer mainServer;
    private Sql_Interaction sql_Interaction;
    private String bilkentID;

    public Edit_TimeTable_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception {
        mainServer = MainServer.getInstance();
        sql_Interaction = mainServer.getSqlInteration();
        //EDIT_TIMETABLE/22101023Th/thursday/Lecture2
        String lectureAtThtIndex = (String) mainServer.runSqlQuery(sql_Interaction.getRespectiveLectureQuery(incomingData[2], incomingData[1], incomingData[3]),Sql_Interaction.GET_SPECIFIC_LECTURE);
        
        String setTo = myClientHandler.getInputStream().readUTF();
        if(setTo.equals("CANCEL")){
            return true;
        }
        //here comes the real deal
        //in each case we update the database to the input
        setTo = setTo.toUpperCase();
        bilkentID = myClientHandler.getUserInfo().split(NetworkProtocol.DATA_DELIMITER)[0];
        if(setTo.equalsIgnoreCase("NONE")){
            //delete the directory only

            //if there was no lecture in the first place
            if(lectureAtThtIndex.equalsIgnoreCase("NONE")){
                //do nothing
                return true;
            }else{
                //for each day delete the instance
                dropCourse(lectureAtThtIndex);
            }
            
        }else{
            //delete the dir if a collision is occuring and make a new lecture dir
            if(!lectureAtThtIndex.equalsIgnoreCase("NONE")){
                dropCourse(lectureAtThtIndex);
            }
            String update_builder = sql_Interaction.createUpdateSpecificLectureQuery(incomingData[2], incomingData[1], incomingData[3], setTo);
            mainServer.runSqlUpdate(update_builder, Sql_Interaction.UPDATE_SPECIFIC_LECTURE);
            //create a new dir if not existing already
            String newLecturePath = bilkentID + "//" + setTo;
            mainServer.createNewLectureDir(newLecturePath);
        }
        return true;
    }

    private void dropCourse(String courseToDrop){
        for(String[] dayEntity : dayEntities){
            for(String lectureCol : lectureCols){
                String lectureAtThtIndex = (String) mainServer.runSqlQuery(sql_Interaction.getRespectiveLectureQuery(dayEntity[0], bilkentID+dayEntity[1], lectureCol),Sql_Interaction.GET_SPECIFIC_LECTURE);
                if(lectureAtThtIndex.equalsIgnoreCase(courseToDrop)){
                    String update_builder = sql_Interaction.createUpdateSpecificLectureQuery(dayEntity[0], bilkentID+dayEntity[1], lectureCol, "NONE");
                    mainServer.runSqlUpdate(update_builder, Sql_Interaction.UPDATE_SPECIFIC_LECTURE); //updates the database
                }
            }
        }
        String lecturePath = bilkentID + "//" + courseToDrop;
        mainServer.deleteLectureDir(lecturePath);
    }

}
