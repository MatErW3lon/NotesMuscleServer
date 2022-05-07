package MysqlQueries;

interface Sql_Interaction_Type {
    
    Integer LOGIN_QUERY = 1;

    Integer GET_USER_INFO_QUERY = 2;

    Integer BILKENTID_UNIQUENESS_QUERY = 3;

    Integer CREATE_NEW_ACCOUNT_UPDATE = 4;

    Integer RETRIEVE_TIMETABLE = 5;

    Integer GET_LECTURE_FROM_DATE = 6;
}
