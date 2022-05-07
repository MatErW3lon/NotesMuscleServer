package ServerBackEnd;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import NetWorkProtocol.NetworkProtocol;

public class Notes_Builder {
    
    private byte[] notes_data;
    private String dateOfNotes; //this is set upon login //must be of format dd--mm--yy e.g. 6-May-22
    public boolean createdNotesFile;
    private ClientHandler myClientHandler;
    private String file_path, file_name;
    private Map<String,String> day_DayID_map;
    private Map<Integer,String> time_LectureID_map;

    public Notes_Builder(ClientHandler myClientHandler){
        this.myClientHandler = myClientHandler;
        createdNotesFile = false;
        day_DayID_map = new HashMap<String,String>();
        time_LectureID_map = new HashMap<Integer, String>();
        initDayMapper();
        initLectureMapper();
    }

    private void initDayMapper() {
        day_DayID_map.put("Mon", "MondayID");
        
        day_DayID_map.put("Tue", "TuesdayID");
        
        day_DayID_map.put("Wed", "WednesdayID");
        
        day_DayID_map.put("Thu", "ThursdayID");
        
        day_DayID_map.put("Fri", "FridayID");
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

    public void appendToFile(byte[] notes_data) throws IOException{
        this.notes_data = notes_data;
        String notesString = new String(this.notes_data, StandardCharsets.UTF_8);
        FileWriter fileWriter = new FileWriter(file_path);
        fileWriter.write(notesString);
        fileWriter.close();
    }

    public void createNotesFile() throws Exception{
        //here we get the lecture based on the date of the user
        //need sql queries to get the lecture, we have the bilkent id
        //file path is set here
        //we will parse the date and the user info to get to the lecture we want to store at
        //________________________________________
        //create file name from the date first
        String[] dateSplit = dateOfNotes.split(" ");
        file_name = dateSplit[2] + '-' + dateSplit[1] + '-' + dateSplit[dateSplit.length - 1].substring(2) + ".txt"; //will result in e.g. May 07 2022 -> "07-May-22.txt"
        
        //get the column names for the database
        String dayCol = day_DayID_map.get(dateSplit[0]);
        String[] timeSplit = dateSplit[3].split(":");
        Integer time = Integer.parseInt(timeSplit[0] + timeSplit[1]);
        String lectureCol = getLectureNumberFromTime(time);

        String userID = myClientHandler.getUserInfo().split(NetworkProtocol.DATA_DELIMITER)[0]; 
        String lecture =MainServer.getInstance().getSqlInteration().createGetCurrentLectureQuery(userID, dayCol, lectureCol); //userID maps to courseID
    }

    private String getLectureNumberFromTime(Integer time) {
        if(time >= 830 && time <= 1030){
            return time_LectureID_map.get(1);
        }else if(time <= 1230){
            return time_LectureID_map.get(2);
        }else if(time <= 330){
            return time_LectureID_map.get(3);
        }else{
            return time_LectureID_map.get(4);
        }
    }

}
