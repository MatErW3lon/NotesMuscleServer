package NetWorkProtocol;

public interface NetworkProtocol {
    String LOGIN_FAILED = "ERROR";

    String dataDelimiter = "/";

    String User_LogOut = "LOGOUT";
    String Invalid_LogOut = null;

    String User_LogIn = "LOGIN"; //note that this will be extend by username and password 
    //example command to log in a user LOGIN/MatErW3lon/uglyday@14

    String SuccessFull_LOGIN = "SUCCESS";

    String Image_Send = "IMAGE";
    String Image_Stop = "STOPIMAGE";
    Integer Image_Received_Confirmation = 1;

    String Create_Account_Request = "CREATEACCOUNT";
    String ACCOUNT_EXISTS_ERROR = "-1";
    String ACCOUNT_CONTINUE = "1";
}
