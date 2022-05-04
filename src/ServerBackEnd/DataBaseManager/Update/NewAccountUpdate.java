package ServerBackEnd.DataBaseManager.Update;

import java.sql.Statement;


public class NewAccountUpdate extends RunSqlUpdate{
    
    public NewAccountUpdate(Statement statement){
        super(statement);
    }

    @Override
    public void runSqlUpdate(String update) throws Exception {
        statement.executeUpdate(update);
        
    }

}
