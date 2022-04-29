package NotesMuscles.io;

public class InvalidCommandException extends Exception{
    public InvalidCommandException(String command, String fromIP){
        super("THE NETWORK ENCOUNTERED AN INVALID COMMAND" + command + " FROM USER " + fromIP);
    }
}
