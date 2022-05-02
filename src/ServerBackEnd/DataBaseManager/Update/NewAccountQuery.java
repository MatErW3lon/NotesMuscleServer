package ServerBackEnd.DataBaseManager.Update;

import java.sql.Statement;



public class NewAccountQuery  extends RunSqlUpdate{
    
    public NewAccountQuery(Statement statement){
        super(statement);
    }

    @Override
    public void runSqlUpdate(String update) throws Exception {
        statement.executeUpdate(update);
    }

}
