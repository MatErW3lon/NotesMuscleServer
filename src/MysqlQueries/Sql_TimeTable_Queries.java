package MysqlQueries;


interface Sql_TimeTable_Queries {
    
    public default String createMondayLecturesQuery(String bilkentID){
        String primary_key = bilkentID + "M";
        String query_builder = "select * from monday where MondayID = '" + primary_key + "';";
        return query_builder;
    }

    public default String createTuesdayLecturesQuery(String bilkentID){
        String primary_key = bilkentID + "T";
        String query_builder = "select * from tuesday where TuesdayID = '" + primary_key + "';";
        return query_builder;
    }

    public default String createWednesdayLecturesQuery(String bilkentID){
        String primary_key = bilkentID + "W";
        String query_builder = "select * from wednesday where WednesdayID = '" + primary_key + "';";
        return query_builder;
    }

    public default String createThursdayLecturesQuery(String bilkentID){
        String primary_key = bilkentID + "Th";
        String query_builder = "select * from thursday where ThursdayID = '" + primary_key + "';";
        return query_builder;
    }

    public default String createFridayLecturesQuery(String bilkentID){
        String primary_key = bilkentID + "F";
        String query_builder = "select * from friday where FridayID = '" + primary_key + "';";
        return query_builder;
    }
}
