package ServerBackEnd.DataBaseManager.Query;

import java.sql.Statement;
import java.sql.ResultSet;

public class Is_A_User_Query extends RunSqlQuery{

    public Is_A_User_Query(Statement statement){
        super(statement);
    }

    @Override
    public Object runSqlQuery(String query) throws Exception{
        ResultSet resultSet = statement.executeQuery(query);
        return (Boolean)resultSet.next();
    }
}
