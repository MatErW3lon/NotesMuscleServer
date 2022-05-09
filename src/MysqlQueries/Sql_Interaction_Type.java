package MysqlQueries;

interface Sql_Interaction_Type {
    
    Integer LOGIN_QUERY = 1;

    Integer GET_USER_INFO_QUERY = 2;

    Integer BILKENTID_UNIQUENESS_QUERY = 3;

    Integer CREATE_NEW_ACCOUNT_UPDATE = 4;

    Integer RETRIEVE_TIMETABLE = 5;

    Integer GET_LECTURE_FROM_DATE = 6;

    Integer ACCOUNT_DELETION_UPDATE = 7;

    Integer GET_SPECIFIC_LECTURE = 8;

    Integer UPDATE_SPECIFIC_LECTURE = 9;

    Integer IS_A_USER_QUERY = 10;
}
