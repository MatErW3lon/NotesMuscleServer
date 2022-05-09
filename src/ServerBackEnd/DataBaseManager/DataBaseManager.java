package ServerBackEnd.DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import MysqlQueries.Sql_Interaction;
import ServerBackEnd.DataBaseManager.Query.CheckBilkentIDUniquenessQuery;
import ServerBackEnd.DataBaseManager.Query.Get_Specific_Lecture;
import ServerBackEnd.DataBaseManager.Query.Is_A_User_Query;
import ServerBackEnd.DataBaseManager.Query.LectureFromDateQuery;
import ServerBackEnd.DataBaseManager.Query.LoginQuery;
import ServerBackEnd.DataBaseManager.Query.RunSqlQuery;
import ServerBackEnd.DataBaseManager.Query.Timetable_Retrieve_Query;
import ServerBackEnd.DataBaseManager.Query.UserInfoQuery;
import ServerBackEnd.DataBaseManager.Update.AccountDeletionUpdate;
import ServerBackEnd.DataBaseManager.Update.NewAccountUpdate;
import ServerBackEnd.DataBaseManager.Update.RunSqlUpdate;
import ServerBackEnd.DataBaseManager.Update.UpdateSpecificLecture;

import java.util.Map;
import java.util.HashMap;

public class DataBaseManager {
    
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    Map<Integer, RunSqlQuery> query_map;
    Map<Integer, RunSqlUpdate> update_map;

    public DataBaseManager() throws Exception{
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notesmuscle", "root", "uglyday@14"); 
        statement = connection.createStatement();
        query_map = new HashMap<>();
        update_map = new HashMap<>();
        initQueryMap();
        initUpdateMap(); 
    }

    private void initQueryMap() {
        //init Login Query
        query_map.put(Sql_Interaction.LOGIN_QUERY, new LoginQuery(statement));

        //init User info Query
        query_map.put(Sql_Interaction.GET_USER_INFO_QUERY, new UserInfoQuery(statement));

        //init Bilkent ID uniqueness query
        query_map.put(Sql_Interaction.BILKENTID_UNIQUENESS_QUERY, new CheckBilkentIDUniquenessQuery(statement));

        //init Timetable Retrieve query
        query_map.put(Sql_Interaction.RETRIEVE_TIMETABLE, new Timetable_Retrieve_Query(statement));

        //init Get Specific Lecture
        query_map.put(Sql_Interaction.GET_SPECIFIC_LECTURE, new Get_Specific_Lecture(statement));

        //init Lecture Retrieve query
        query_map.put(Sql_Interaction.GET_LECTURE_FROM_DATE, new LectureFromDateQuery(statement));

        //init Is A User Query
        query_map.put(Sql_Interaction.IS_A_USER_QUERY, new Is_A_User_Query(statement));
    }

    private void initUpdateMap(){
        update_map.put(Sql_Interaction.CREATE_NEW_ACCOUNT_UPDATE, new NewAccountUpdate(statement));

        //init account deletion
        update_map.put(Sql_Interaction.ACCOUNT_DELETION_UPDATE, new AccountDeletionUpdate(statement));

        //init specific lecture update
        update_map.put(Sql_Interaction.UPDATE_SPECIFIC_LECTURE, new UpdateSpecificLecture(statement));
    }

    public Object SqlQuery(String query, Integer type) throws Exception{
        RunSqlQuery runSqlQuery = query_map.get(type);
        return runSqlQuery.runSqlQuery(query);
    }

    public void SqlUpdate(String update, Integer type) throws Exception{
        RunSqlUpdate runSqlUpdate = update_map.get(type);
        runSqlUpdate.runSqlUpdate(update);
    }
}
