package ServerBackEnd.DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import MysqlQueries.SqlQueryType;

import java.util.Map;
import java.util.HashMap;

public class DataBaseManager {
    
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    Map<Integer, RunSqlQuery> query_map;

    public DataBaseManager() throws Exception{
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notesmuscle", "root", "uglyday@14"); 
        statement = connection.createStatement();
        query_map = new HashMap<>();
        initializeQueryMap(); 
    }

    private void initializeQueryMap() {
        //init Login Query
        query_map.put(SqlQueryType.LOGIN_QUERY, new LoginQuery(statement));

        //init Bilkent ID uniqueness query
        query_map.put(SqlQueryType.BILKENTID_UNIQUENESS_QUERY, new CheckBilkentIDUniquenessQuery(statement));
    }

    public Object SqlQuery(String query, Integer type) throws Exception{
        RunSqlQuery runSqlQuery = query_map.get(type);
        return runSqlQuery.runSqlQuery(query);
    }

}
