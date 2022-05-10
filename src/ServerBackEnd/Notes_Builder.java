package ServerBackEnd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import MysqlQueries.Sql_Interaction;
import NetWorkProtocol.NetworkProtocol;

public class Notes_Builder {
    
    private String notes_data;
    private String dateOfNotes; //this is set upon login //must be of format dd--mm--yy e.g. 6-May-22
    public boolean createdNotesFile;
    private ClientHandler myClientHandler;
    private String file_path, file_name;
    private Map<String,String[]> day_DayID_map;
    private Map<Integer,String> time_LectureID_map;

    public Notes_Builder(ClientHandler myClientHandler){
        notes_data = null;
        this.myClientHandler = myClientHandler;
        createdNotesFile = false;
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

    public void setDate(String dateOfNotes){
        this.dateOfNotes = dateOfNotes;
    }

    public void appendToFile(String notes_data) throws IOException{
        this.notes_data = notes_data;
        System.out.println("HERE IN APPEND");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file_path+ ".txt"));
        StringBuilder buildNotesData = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            buildNotesData.append(line);
            buildNotesData.append(System.lineSeparator());
            line = bufferedReader.readLine();
        }
        String everything = buildNotesData.toString();
        bufferedReader.close();
        FileWriter fileWriter = new FileWriter(file_path + ".txt");
        fileWriter.write(everything+ this.notes_data);
        fileWriter.close();
    }

    public void createNotesFile() throws Exception{
        //here we get the lecture based on the date of the user
        //need sql queries to get the lecture, we have the bilkent id
        //file path is set here
        //we will parse the date and the user info to get to the lecture we want to store at
        //________________________________________
        //create file name from the date first
        createdNotesFile = true;
        MainServer mainServer = MainServer.getInstance();
        Sql_Interaction sql_Interaction = mainServer.getSqlInteration();
        String[] dateSplit = dateOfNotes.split(" ");
        file_name = dateSplit[2] + '-' + dateSplit[1] + '-' + dateSplit[dateSplit.length - 1].substring(2); //will result in e.g. May 07 2022 -> "07-May-22.txt"

        //get the column names for the database
        String dayCol = day_DayID_map.get(dateSplit[0])[0];
        String[] timeSplit = dateSplit[3].split(":");
        String dayEntity = day_DayID_map.get(dateSplit[0])[1]; 
        Integer time = Integer.parseInt(timeSplit[0] + timeSplit[1]);
        String lectureCol = getLectureNumberFromTime(time);


        String coursesID = myClientHandler.getUserInfo().split(NetworkProtocol.DATA_DELIMITER)[0]; 
        String lecture = (String)mainServer.runSqlQuery(sql_Interaction.createGetCurrentLectureQuery(coursesID, dayCol, dayEntity,lectureCol), Sql_Interaction.GET_LECTURE_FROM_DATE); 
        System.out.println(lecture);

        //now create the file path
        file_path = System.getProperty("user.dir") + "//client//" + coursesID + "//" + lecture + "//" + file_name;
        mainServer.createNewNotesFile(file_path, coursesID, lecture, file_name);
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
    }
}
