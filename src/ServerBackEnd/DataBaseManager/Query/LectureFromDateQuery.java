package ServerBackEnd.DataBaseManager.Query;

import java.sql.ResultSet;
import java.sql.Statement;

public class LectureFromDateQuery extends RunSqlQuery{
    
    public LectureFromDateQuery(Statement statement){
        super(statement);
    }

    @Override
    public Object runSqlQuery(String query) throws Exception {
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
