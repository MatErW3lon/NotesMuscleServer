package NetWorkProtocol;

public interface NetworkProtocol {
    String LOGIN_FAILED = "ERROR";

    String dataDelimiter = "/";

    String User_LogOut = "LOGOUT";
    String Invalid_LogOut = null;

    String User_LogIn = "LOGIN";

    String SuccessFull_LOGIN = "SUCCESS";

    String Image_Send = "IMAGE";
    String Image_Stop = "STOPIMAGE";
    Integer Image_Received_Confirmation = 1;

    String Create_Account_Request = "CREATEACCOUNT";
    String ACCOUNT_EXISTS_ERROR = "-1";
    String ACCOUNT_CONTINUE = "1";
    String Cancel_Acc_Request = "CANCEL_NEW_ACC";
    String Acc_Info_Ready = "READY_TO_CREATE";
}
