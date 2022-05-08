package NetWorkProtocol;

public interface NetworkProtocol {
    String LOGIN_FAILED = "ERROR";

    String DATA_DELIMITER = "/";

    String USER_LOGOUT = "LOGOUT";
    String INVALID_LOGOUT = null;

    String USER_LOGIN = "LOGIN";

    String SUCCESSFULL_LOGIN = "SUCCESS";

    String IMAGE_SEND = "IMAGE";
    String IMAGE_STOP = "STOP_IMAGE";
    Integer IMAGE_RECEIVED_CONFIRMATION = 1;

    String CREATE_ACCOUNT_REQUEST = "CREATE_ACCOUNT";
    String ACCOUNT_EXISTS_ERROR = "-1";
    String ACCOUNT_CONTINUE = "1";
    String CANCEL_ACC_REQUEST = "CANCEL_NEW_ACC";
    String ACC_INFO_READY = "READY_TO_CREATE";

    String RETRIEVE_TIMETABLE_REQUEST = "TIMETABLE";
    String EDIT_TIMETABLE_REQUEST = "EDIT_TIMETABLE";

    String RETRIEVE_NOTES_REQUEST = "NOTES";
    String CANCEL_NOTES = "CANCEL_NOTES";

    //Account deletion
    String DELETE_ACCOUNT_REQUEST = "DELETE_ACC";
}