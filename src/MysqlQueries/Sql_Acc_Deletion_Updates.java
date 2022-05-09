package MysqlQueries;

interface Sql_Acc_Deletion_Updates {

    public default String[] createAccDeletionsUpdates(String userID){
        String[] rtnArr = new String[8];
        rtnArr[0] = createDeleteLoginInfoUpdate(userID);
        rtnArr[1] = createDeleteCoursesUpdate(userID);
        rtnArr[2] = createDeleteTuesdayLecturesUpdate(userID);
        rtnArr[3] = createDeleteWednesdayLecturesUpdate(userID);
        rtnArr[4] = createDeleteThursdayLecturesUpdate(userID);
        rtnArr[5] = createDeleteFridayLecturesUpdate(userID);
        rtnArr[6] = createDeleteMondayLecturesUpdate(userID);
        rtnArr[7] = createDeleteUserInfo(userID);
        return rtnArr;
    }
    
    public default String createDeleteLoginInfoUpdate(String userID){
        String updateBuilder = "DELETE FROM `notesmuscle`.`login_info`" + 
                               " WHERE BilkentID =" + userID + ";";
        return updateBuilder;
    }

    public default String createDeleteMondayLecturesUpdate(String userID){
        String updateBuilder = "DELETE FROM monday" + 
                               " WHERE MondayID = '" +  userID+"M"  + "';";
        return updateBuilder;
    }

    public default String createDeleteTuesdayLecturesUpdate(String userID){
        String updateBuilder = "DELETE FROM `notesmuscle`.`tuesday`" + 
                               " WHERE TuesdayID = '" +  userID+"T"  + "';";
        return updateBuilder;
    }
    
    public default String createDeleteWednesdayLecturesUpdate(String userID){
        String updateBuilder = "DELETE FROM `notesmuscle`.`wednesday`" + 
                               " WHERE WednesdayID = '" +  userID+"W"  + "';";
        return updateBuilder;
    }

    public default String createDeleteThursdayLecturesUpdate(String userID){
        String updateBuilder = "DELETE FROM `notesmuscle`.`thursday`" + 
                               " WHERE ThursdayID = '" +  userID+"Th"  + "';";
        return updateBuilder;
    }

    public default String createDeleteFridayLecturesUpdate(String userID){
        String updateBuilder = "DELETE FROM `notesmuscle`.`friday`" + 
                               " WHERE FridayID = '" +  userID+"F"  + "';";
        return updateBuilder;
    }

    public default String createDeleteCoursesUpdate(String userID){
        String updateBuilder = "DELETE FROM `notesmuscle`.`courses`" +
                               " WHERE CoursesID = " + userID + ";";
        return updateBuilder;
    }

    public default String createDeleteUserInfo(String userID){
        String updateBuilder = "DELETE FROM `notesmuscle`.`user`" +
                               " WHERE UserID = " + userID + ";";
        return updateBuilder;
    }

}
