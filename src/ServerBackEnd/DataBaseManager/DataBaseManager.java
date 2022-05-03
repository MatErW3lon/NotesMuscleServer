package ServerBackEnd.DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import MysqlQueries.Sql_Interaction;
import ServerBackEnd.DataBaseManager.Query.CheckBilkentIDUniquenessQuery;
import ServerBackEnd.DataBaseManager.Query.LoginQuery;
import ServerBackEnd.DataBaseManager.Query.RunSqlQuery;
import ServerBackEnd.DataBaseManager.Update.NewAccountUpdate;
import ServerBackEnd.DataBaseManager.Update.RunSqlUpdate;

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

        //init Bilkent ID uniqueness query
        query_map.put(Sql_Interaction.BILKENTID_UNIQUENESS_QUERY, new CheckBilkentIDUniquenessQuery(statement));

    }

    private void initUpdateMap(){
        update_map.put(Sql_Interaction.CREATE_NEW_ACCOUNT, new NewAccountUpdate(statement));
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
