package NotesMuscles.io;

public class DirCreationException extends Exception{
    public DirCreationException(String bilkentID){
        super("FAILED TO CREATE USER DIR FOR " + bilkentID);
    }
}
