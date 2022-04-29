package NetWorkProtocol;

public interface NetworkProtocol {
    String LOGIN_FAILED = "ERROR";

    String connectionEstablished = "CONNECTED";

    String dataDelimiter = "/";

    String User_LogOut = "LOGOUT";
    String Invalid_LogOut = null;

    String User_LogIn = "LOGIN"; //note that this will be extend by username and password 
    //example command to log in a user LOGIN/MatErW3lon/uglyday@14

    String SuccessFull_LOGIN = "SUCCESS";

    String Image_Send = "IMAGE";
    String Image_Stop = "STOPIMAGE";
    int Image_Received_Confirmation = 1;
}
