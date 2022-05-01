package ServerBackEnd.DataBaseManager;

import java.sql.ResultSet;
import java.sql.Statement;
import NetWorkProtocol.NetworkProtocol;

class CheckBilkentIDUniquenessQuery extends RunSqlQuery{

    public CheckBilkentIDUniquenessQuery(Statement statement){
        super(statement);
    }

    @Override
    public Object runSqlQuery(String query) throws Exception{
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            return NetworkProtocol.ACCOUNT_EXISTS_ERROR;
        }else{
            return NetworkProtocol.ACCOUNT_CONTINUE;
        }
    }
}
