package MysqlQueries;

import NetWorkProtocol.NetworkProtocol;

public interface Sql_Interaction extends Sql_Interaction_Type, Sql_Create_Acc_Update, Sql_TimeTable_Queries{
    final String getAllLoginInfo = "select * from login_info";
    final String getAllUserInfo = "select * from user";
    final String getAllCoursesInfo = "select * from courses";

    static String createLoginUserQuery(String username, String password){
        return "select BilkentID from login_info where Username = '" + username + "' and Password = '" + password + "'";
    }

    static String createBilkentIDUniquessQuery(String bilkentID){
        return "select * from login_info where BilkentID = " + bilkentID;
    }

    static String createUserInfoQuery(String username){
        String queryBuilder = "select user.UserID, user.Firstname, user.Lastname from user " + 
                                "left join login_info on login_info.Username = '" + username + "';";
        return queryBuilder;
    }
}