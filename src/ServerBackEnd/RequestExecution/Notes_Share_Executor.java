package ServerBackEnd.RequestExecution;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;

import MysqlQueries.Sql_Interaction;
import NetWorkProtocol.NetworkProtocol;
import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;

/**
 * Notes_Share_Executor
 */

class Notes_Share_Executor extends Command_Executor{

    public Notes_Share_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    //the incoming data will be the ID and the path to the text file
    //the id will be sent by the client while the path is determined by the Note_Retriever_Executor
    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        //first check if there is a bilkent id for the given receiver
        MainServer mainServer = MainServer.getInstance();
        Sql_Interaction sql_Interaction = mainServer.getSqlInteration();
        String query_builder = sql_Interaction.createBilkentIDPresenceQuery(incomingData[0]);
        Boolean isAUser = (Boolean) mainServer.runSqlQuery(query_builder, Sql_Interaction.IS_A_USER_QUERY);
        if(isAUser){
            //check for if the user takes the lecture
            //check receiving user directory        
            String[] absolutePath = new File(incomingData[1]).getAbsolutePath().replace("\\", "/").split("/");
            String lectureToSendFor = absolutePath[absolutePath.length - 2];

            String[] lectures = mainServer.getAllLectures(incomingData[0]);
            boolean foundMatch = false;
            for(String lecture : lectures){
                if(lectureToSendFor.equalsIgnoreCase(lecture)){
                    foundMatch = true;
                }
            }
            if(!foundMatch){
                myClientHandler.getOutStream().writeUTF(NetworkProtocol.SHARE_NOTES_ERROR_STATUS_NOLECTURE);
                myClientHandler.getOutStream().flush();
                return false;
            }

            //do the note sharing and send the confirmation string
            //here we just copy the given text file into the receiving user;
            String file_name = new File(incomingData[1]).getName().replace(".txt", "");
            String file_path = System.getProperty("user.dir") + "//client//" + incomingData[0] + "//" + lectureToSendFor + "//" + file_name;
            mainServer.createNewNotesFile(file_path, incomingData[0], lectureToSendFor, file_name);
            //now copy to the file
            String copyText = getTextFromFile(incomingData[1]);
            //now write the text back...
            FileWriter fileWriter = new FileWriter(new File(file_path + ".txt"));
            fileWriter.write(copyText);
            fileWriter.close();

            myClientHandler.getOutStream().writeUTF(NetworkProtocol.SHARE_NOTES_CONFIRMATION);
            myClientHandler.getOutStream().flush();
            return true;
        }else{
            //send an error message
            myClientHandler.getOutStream().writeUTF(NetworkProtocol.SHARE_NOTES_ERROR_STATUS_NOUSER);
            myClientHandler.getOutStream().flush();
            return false;
        }
    }

    private String getTextFromFile(String path) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            String allData = sb.toString();
            bufferedReader.close();
            return allData;
    }
    
}