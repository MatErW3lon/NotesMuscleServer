package ServerBackEnd.DataBaseManager.Query;

import java.sql.Statement;
import java.sql.ResultSet;
import NetWorkProtocol.NetworkProtocol;

public class Timetable_Retrieve_Query extends RunSqlQuery{

    public Timetable_Retrieve_Query(Statement statement){
        super(statement);
    }

    @Override
    public Object runSqlQuery(String query) throws Exception {
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            String lecturesBuilder = null;
            lecturesBuilder = resultSet.getString("Lecture1") + NetworkProtocol.DATA_DELIMITER + 
                              resultSet.getString("Lecture2") + NetworkProtocol.DATA_DELIMITER +
                              resultSet.getString("Lecture3") + NetworkProtocol.DATA_DELIMITER +
                              resultSet.getString("Lecture4");
            return lecturesBuilder;
        }
        return null;
    }
}
