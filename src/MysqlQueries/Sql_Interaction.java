package MysqlQueries;

import NetWorkProtocol.NetworkProtocol;

public interface Sql_Interaction extends Sql_Interaction_Type{
    final String getAllLoginInfo = "select * from login_info";
    final String getAllUserInfo = "select * from user";
    final String getAllCoursesInfo = "select * from courses";

    static String createLoginUserQuery(String username, String password){
        return "select BilkentID from login_info where Username = '" + username + "' and Password = '" + password + "'";
    }

    static String createBilkentIDUniquessQuery(String bilkentID){
        return "select * from login_info where BilkentID = " + bilkentID;
    }

    //new user data is of the form firstname/lastname/bilkentID/course1/course2/course3/course4/course5/username/password
    static String createNewUserRecordUpdate(String newUserData){
        String[] userData = newUserData.split(NetworkProtocol.dataDelimiter);
        String updateBuilder = "INSERT INTO `notesmuscle`.`user`"  +
                                "(`UserID`,`Firstname`,`Lastname`,`CoursesID`,`FriendsList`) " +
                                "VALUES (" + userData[2] + ",'" + userData[0] +"','" + userData[1] + "'," + userData[2] +  ",'" + "NULL" + "') ;";
        return updateBuilder;
    }

    static String createNewLoginRecordUpdate(String newUserData){
        String[] userData = newUserData.split(NetworkProtocol.dataDelimiter);
        String updateBuilder = "INSERT INTO `notesmuscle`.`login_info`" +
                                "(`BilkentID`,`Username`,`Password`,`UserID`)" +
                                "VALUES (" + userData[2] + ",'" + userData[9] +"','" + userData[10] + "'," + userData[2] + ");";
        return updateBuilder;
    }

    static String createNewCoursesRecordUpdate(String newUserData){
        String[] userData = newUserData.split(NetworkProtocol.dataDelimiter);

    }
}
