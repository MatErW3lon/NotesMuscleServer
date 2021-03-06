package NotesMuscles.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


import NotesMuscles.io.DirCreationException;

public class DirectoryManager {
    
    public DirectoryManager(){}

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

    @SuppressWarnings("unchecked")
    public void createNewNotesFile(String path, String coursesID, String lecture, String file_name) throws IOException, ClassNotFoundException{
        File new_notes_file = new File(path + ".txt");
        if(!new_notes_file.exists()){
            System.out.println("FILE CREATED");
            new_notes_file.createNewFile();
            //now we need to add the file_name to the .dat file in the respective dir
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "//client//" + coursesID + "//" + lecture + "//notes_arraylist.dat");
            ObjectInputStream objInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<String> notes_arraylist = (ArrayList<String>) objInputStream.readObject();
            notes_arraylist.add(file_name);
            objInputStream.close();
            fileInputStream.close();
            //write it back
            FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + "//client//" + coursesID + "//" + lecture + "//notes_arraylist.dat");
            ObjectOutputStream objOutputStream = new ObjectOutputStream(fileOutputStream);
            objOutputStream.writeObject(notes_arraylist);
            objInputStream.close();
            fileOutputStream.close();
        }
    }

    public void deleteUserDir(File userFile){
        File[] contents = userFile.listFiles();
        if(contents != null){
            for(File subFile : contents){
                deleteUserDir(subFile);
            }
        }
        userFile.delete();
    }

    public void deleteLectureDir(File lectureFile){
        File[] contents = lectureFile.listFiles();
        if(contents != null){
            for(File subFile : contents){
                deleteUserDir(subFile);
            }
        }
        lectureFile.delete();
    }

    public void newLectureDir(String newLecturePath) throws IOException {
        File lectureDir = new File(newLecturePath);
        if (!lectureDir.exists()) {
            if (lectureDir.mkdir()) {
                File notes_arraylist_dat_file = new File(newLecturePath + "//notes_arraylist.dat");
                FileOutputStream createFile = new FileOutputStream(notes_arraylist_dat_file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(createFile);
                objectOutputStream.writeObject(new ArrayList<String>());
                objectOutputStream.close();
                createFile.close();
            }
        }
    }

    public String[] getAllLectureDir(String bilkentID){
        File file = new File(System.getProperty("user.dir") + "//client//" + bilkentID);
        File[] lectureDirs = file.listFiles();
        String[] lectures = new String[lectureDirs.length];
        for(int i = 0; i < lectureDirs.length; i++){
            lectures[i] = lectureDirs[i].getName();
        }
        return lectures;
    }
}
