package NotesMuscles.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import NotesMuscles.io.DirCreationException;

public class DirectoryManager {
    
    public DirectoryManager(){

    }

    /**
     * 
     * @param clientInfo THE STRING ARRAY STORES BILKENT INFO AND COURSES
     * @throws IOException
     */
    public void createClientDirOnNewAccount(String[] clientInfo) throws DirCreationException, IOException{
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

    private void createCoursesSubDir(String path, String[] clientInfo) throws DirCreationException, IOException{
        File courseSubDir;
        File notes_arraylist_dat_file;
        for(int i = 1; i < clientInfo.length; i++){
            courseSubDir = new File(path + "//" + clientInfo[i]);
            if (!courseSubDir.mkdir()) {
                throw new DirCreationException(clientInfo[0]);
            }  
            //put a .dat file into each course dir
            notes_arraylist_dat_file = new File(path + "//" + clientInfo[i] + "//notes_arraylist.dat");
            FileOutputStream createFile = new FileOutputStream(notes_arraylist_dat_file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(createFile);
            objectOutputStream.writeObject(new ArrayList<String>());
            objectOutputStream.close();
            createFile.close();
        }
        //the notes arraylist stores the names of each text file as <name>.txt;
    }
}
