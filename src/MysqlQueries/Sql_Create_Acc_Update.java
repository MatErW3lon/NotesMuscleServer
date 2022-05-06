package MysqlQueries;

import NetWorkProtocol.NetworkProtocol;

interface Sql_Create_Acc_Update {
    //new user data is of the form firstname/lastname/bilkentid/dayID/lec1/lec2/lec3/lec4/dayID/lec1/lec2/lec3/lec4/dayID/lec1/lec2/lec3/lec4/dayID/lec1/lec2/lec3/lec4/dayID/lec1/lec2/lec3/lec4/username/password
    public default String createNewUserRecordUpdate(String newUserData){
        String[] userData = newUserData.split(NetworkProtocol.DATA_DELIMITER);
        String updateBuilder = "INSERT INTO `notesmuscle`.`user`"  +
                                "(`UserID`,`Firstname`,`Lastname`,`CoursesID`,`FriendsList`) " +
                                "VALUES (" + userData[2] + ",'" + userData[0] +"','" + userData[1] + "'," + userData[2] +  ",'" + "NULL" + "') ;";
        return updateBuilder;
    }

    public default String createNewLoginRecordUpdate(String newUserData){
        String[] userData = newUserData.split(NetworkProtocol.DATA_DELIMITER);
        String updateBuilder = "INSERT INTO `notesmuscle`.`login_info`" +
                                "(`BilkentID`,`Username`,`Password`,`UserID`)" +
                                "VALUES (" + userData[2] + ",'" + userData[userData.length - 2] +"','" + userData[userData.length - 1] + "'," + userData[2] + ");";
        return updateBuilder;
    }

    public default String createNewCoursesRecordUpdate(String newUserData){
        String[] userData = newUserData.split(NetworkProtocol.DATA_DELIMITER);
        String updateBuilder = "INSERT INTO `notesmuscle`.`courses`" +
                                "(`CoursesID`,`MondayID`,`TuesdayID`,`WednesdayID`,`ThursdayID`,`FridayID`)" +
                                "VALUES (" + userData[2] + ",'" + userData[3] + "','" + userData[8] + "','" + userData[13] + "','" + userData[18] + "','" + userData[23] + "');";
        return updateBuilder;
    }

    public default String createNewMondayRecordUpdate(String newUserData){
        int i = 3;
        String[] userData = newUserData.split(NetworkProtocol.DATA_DELIMITER);
        String updateBuilder = "INSERT INTO `notesmuscle`.`monday`"  +
                                "(`MondayID`,`Lecture1`,`Lecture2`,`Lecture3`,`Lecture4`)" + 
                                "VALUES('" + userData[i] + "','" + userData[i+1] + "','" + userData[i+2] + "','" +  userData[i+3] + "','" + userData[i+4] + "');";
        return updateBuilder;
    }

    public default String createNewTuesdayRecordUpdate(String newUserData){
        int i = 8;
        String[] userData = newUserData.split(NetworkProtocol.DATA_DELIMITER);
        String updateBuilder = "INSERT INTO `notesmuscle`.`tuesday`"  +
                                "(`TuesdayID`,`Lecture1`,`Lecture2`,`Lecture3`,`Lecture4`)" + 
                                "VALUES('" + userData[i] + "','" + userData[i+1] + "','" + userData[i+2] + "','" +  userData[i+3] + "','" + userData[i+4] + "');";
        return updateBuilder;
    }

    public default String createNewWednesdayRecordUpdate(String newUserData){
        int i= 13;
        String[] userData = newUserData.split(NetworkProtocol.DATA_DELIMITER);
        String updateBuilder = "INSERT INTO `notesmuscle`.`wednesday`"  +
                                "(`WednesdayID`,`Lecture1`,`Lecture2`,`Lecture3`,`Lecture4`)" + 
                                "VALUES('" + userData[i] + "','" + userData[i+1] + "','" + userData[i+2] + "','" +  userData[i+3] + "','" + userData[i+4] + "');";
        return updateBuilder;
    }

    public default String createNewThursdayRecordUpdate(String newUserData){
        int i= 18;
        String[] userData = newUserData.split(NetworkProtocol.DATA_DELIMITER);
        String updateBuilder = "INSERT INTO `notesmuscle`.`thursday`"  +
                                "(`ThursdayID`,`Lecture1`,`Lecture2`,`Lecture3`,`Lecture4`)" + 
                                "VALUES('" + userData[i] + "','" + userData[i+1] + "','" + userData[i+2] + "','" +  userData[i+3] + "','" + userData[i+4] + "');";
        return updateBuilder;
    }

    public default String createNewFridayRecordUpdate(String newUserData){
        int i= 23;
        String[] userData = newUserData.split(NetworkProtocol.DATA_DELIMITER);
        String updateBuilder = "INSERT INTO `notesmuscle`.`friday`"  +
                                "(`FridayID`,`Lecture1`,`Lecture2`,`Lecture3`,`Lecture4`)" + 
                                "VALUES('" + userData[i] + "','" + userData[i+1] + "','" + userData[i+2] + "','" +  userData[i+3] + "','" + userData[i+4] + "');";
        return updateBuilder;
    }
}
