package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;
import MysqlQueries.Sql_Interaction;
import NetWorkProtocol.NetworkProtocol;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;

class Notes_Retrieve_Executor extends Command_Executor{
    
    ArrayList<String> notes_paths_arraylist;

    public Notes_Retrieve_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean executeCommand(String[] incomingData) throws Exception {
        String bilkentID = incomingData[1];
        String[] lectures_over_week = new String[5];
        MainServer mainServer = MainServer.getInstance();
        Sql_Interaction sql_interaction = mainServer.getSqlInteration();
        lectures_over_week[0] = (String) mainServer.runSqlQuery(sql_interaction.createMondayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        lectures_over_week[1] = (String) mainServer.runSqlQuery(sql_interaction.createTuesdayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        lectures_over_week[2] = (String) mainServer.runSqlQuery(sql_interaction.createWednesdayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        lectures_over_week[3] = (String) mainServer.runSqlQuery(sql_interaction.createThursdayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        lectures_over_week[4] = (String) mainServer.runSqlQuery(sql_interaction.createFridayLecturesQuery(bilkentID), Sql_Interaction.RETRIEVE_TIMETABLE);
        String lectures = "";
        for(String result : lectures_over_week){
            lectures += result + NetworkProtocol.DATA_DELIMITER;
        }
        String[] lectures_split = getCourses(lectures.split(NetworkProtocol.DATA_DELIMITER));
        //first we need to write the number of courses this user takes
        myClientHandler.getOutStream().writeUTF(lectures_split.length + NetworkProtocol.DATA_DELIMITER + rebuildLecturesStr(lectures_split));
        myClientHandler.getOutStream().flush();

        String selected_course = myClientHandler.getInputStream().readUTF();
        
        if(selected_course.equals(NetworkProtocol.CANCEL_NOTES)){
            return true;
        }

        String buildDatFilePath = System.getProperty("user.dir") + "//client//" + bilkentID + "//" + selected_course + "//notes_arraylist.dat";

        FileInputStream openDatFileForRead = new FileInputStream(buildDatFilePath);
        ObjectInputStream inputStream = new ObjectInputStream(openDatFileForRead);
        notes_paths_arraylist = (ArrayList<String>) inputStream.readObject();
        inputStream.close();

        //write the size of the arraylist and the name of each file
        String buildTxtFilesNameStr = getTextFilesStr();
        myClientHandler.getOutStream().writeUTF(buildTxtFilesNameStr);
        myClientHandler.getOutStream().flush();

        //read for the selected text file from user
        String pathToTxtFile = myClientHandler.getInputStream().readUTF();
        if(pathToTxtFile.equals(NetworkProtocol.CANCEL_NOTES)){
            return true;
        }
        pathToTxtFile = System.getProperty("user.dir") + "//client//" + bilkentID + "//" + selected_course + "//" + pathToTxtFile + ".txt";

        byte[] txt_data_byte = getTextFileDataBytes(pathToTxtFile);
        //now we write the text data
        myClientHandler.getOutStream().writeInt(txt_data_byte.length);
        myClientHandler.getOutStream().flush();
        Thread.sleep(10);
        myClientHandler.getOutStream().write(txt_data_byte, 0, txt_data_byte.length);
        myClientHandler.getOutStream().flush();

        String chooseOption = myClientHandler.getInputStream().readUTF();
        if(chooseOption.equals(NetworkProtocol.CANCEL_NOTES)){
            return true;
        }else if(chooseOption.equals(NetworkProtocol.SHARE_NOTES)){
            //user chose to share the notes
            //build the data here
            //get the bilkent id
            String sendToBilkentID = myClientHandler.getInputStream().readUTF();
            //keep taking input until cancel or true
            if(sendToBilkentID.equals("CANCEL")){
                return true;
            }
            String[] forwardData = new String[2];
            forwardData[0] = sendToBilkentID;
            forwardData[1] = pathToTxtFile;
            boolean shared = new Notes_Share_Executor(myClientHandler).executeCommand(forwardData);
            while(!shared){
                forwardData[0] = myClientHandler.getInputStream().readUTF();
                //keep taking input until cancel or true
                if(forwardData[0].equals("CANCEL")){
                    return true;
                }
                shared = new Notes_Share_Executor(myClientHandler).executeCommand(forwardData);
            }
            return shared;
        }

        //now we wait for the edit
        String edittedText = myClientHandler.getInputStream().readUTF();
        if(edittedText.equals(NetworkProtocol.CANCEL_NOTES)){
            return true;
        }
        setTextFileToStr(pathToTxtFile ,edittedText);
        return true;
    }

    private void setTextFileToStr(String pathToTxtFile, String edittedText) throws IOException {
        FileWriter editWriter = new FileWriter(new File(pathToTxtFile));
        editWriter.write(edittedText);
        editWriter.close();
    }

    private String getTextFilesStr() {
        String rtnString = notes_paths_arraylist.size() + NetworkProtocol.DATA_DELIMITER;
        for(String temp : notes_paths_arraylist){
            rtnString += temp + NetworkProtocol.DATA_DELIMITER;
        }
        return rtnString;
    }

    private byte[] getTextFileDataBytes(String pathToTxtFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToTxtFile));
        StringBuilder buildNotesData = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            buildNotesData.append(line);
            buildNotesData.append(System.lineSeparator());
            line = bufferedReader.readLine();
        }
        String everything = buildNotesData.toString();
        byte[] notes_data_bytes = everything.getBytes(StandardCharsets.UTF_8);
        bufferedReader.close();
        return notes_data_bytes;
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
            lectures_array[i] = lectures_list.get(i);
        }
        return lectures_array;
    }

    private String rebuildLecturesStr(String[] lectures_split){
        String rebuiltStr = "";
        for(String temp : lectures_split){
            rebuiltStr += temp + NetworkProtocol.DATA_DELIMITER;
        }
        return rebuiltStr;
    }
}