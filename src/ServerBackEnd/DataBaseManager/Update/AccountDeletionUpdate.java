package ServerBackEnd.DataBaseManager.Update;

import java.sql.Statement;

public class AccountDeletionUpdate extends RunSqlUpdate{

    public AccountDeletionUpdate(Statement statement){
        super(statement);
    }

    @Override
    public void runSqlUpdate(String update) throws Exception {
        statement.executeUpdate(update);
    }
}
