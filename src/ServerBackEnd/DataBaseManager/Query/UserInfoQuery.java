package ServerBackEnd.DataBaseManager.Query;

import java.sql.Statement;

import NetWorkProtocol.NetworkProtocol;

import java.sql.ResultSet;

public class UserInfoQuery extends RunSqlQuery{

    public UserInfoQuery(Statement statement){
        super(statement);
    }

    @Override
    public Object runSqlQuery(String query) throws Exception {
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
           String info = resultSet.getString("UserID") + NetworkProtocol.DATA_DELIMITER + 
                        resultSet.getString("Firstname") + NetworkProtocol.DATA_DELIMITER + resultSet.getString("Lastname");
           return info;             
        }
        return null;
    }

}
