package NotesMuscles.io;

public class InvalidFirstCommand extends Exception{
    public InvalidFirstCommand(String fromIPAdr){
        super("THE FIRST COMMAND WAS NOT LOGIN FROM USER " + fromIPAdr);
    }
}
