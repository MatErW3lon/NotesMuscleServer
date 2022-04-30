package NotesMuscles.io;

public class LoginQueryFailedException extends Exception {
    public LoginQueryFailedException(String LoginQuery){
        super("THE LOGIN QUERY " + LoginQuery + " FAILED");
    }    
}
