package ServerBackEnd.DataBaseManager;

import java.sql.Statement;
import java.sql.ResultSet;

import NotesMuscles.io.LoginQueryFailedException;

class LoginQuery extends RunSqlQuery{

    public LoginQuery(Statement statement){
        super(statement);
    }


    /*
        return a string
    */
    @Override
    public Object runSqlQuery(String query) throws Exception{
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            return resultSet.getString("BilkentID");
        }else{
            //the username and password was not found
            throw new LoginQueryFailedException(query);
        }
    }   
}
