package NotesMuscles.io;

/**
 * AccountCreationException
 */
public class AccountCreationException extends Exception{

    public AccountCreationException(String update){
        super("THE UPDATE REQUEST " + update + " COULD NOT BE PROCESSED");
    }
    
}