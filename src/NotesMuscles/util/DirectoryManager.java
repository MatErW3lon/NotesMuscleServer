package NotesMuscles.util;

import java.io.File;

import NotesMuscles.io.DirCreationException;

public class DirectoryManager {
    
    public DirectoryManager(){

    }

    /**
     * 
     * @param clientInfo THE STRING ARRAY STORES BILKENT INFO AND COURSES
     */
    public void createClientDirOnNewAccount(String[] clientInfo) throws DirCreationException{
        String bilkentID = clientInfo[0];
        File clientDir = new File(System.getProperty("user.dir")+ "//client//" + bilkentID);
        if (!clientDir.exists()) {
            if (clientDir.mkdir()) {
                createCoursesSubDir(System.getProperty("user.dir")+ "//client//" + bilkentID, clientInfo);
            } else {
                throw new DirCreationException(bilkentID);
            }
        }
    }

    private void createCoursesSubDir(String path, String[] clientInfo) throws DirCreationException{
        File courseSubDir;
        for(int i = 1; i < clientInfo.length; i++){
            if(clientInfo[i].equalsIgnoreCase("NONE")){
                continue;
            }
            courseSubDir = new File(path + "//" + clientInfo[i]);
            if (!courseSubDir.mkdir()) {
                throw new DirCreationException(clientInfo[0]);
            }   
            
        }
    }

}
