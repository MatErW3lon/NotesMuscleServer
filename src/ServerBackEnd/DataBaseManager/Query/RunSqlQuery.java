package ServerBackEnd.DataBaseManager.Query;

import java.sql.Statement;

public abstract class RunSqlQuery {

    protected Statement statement;

    public RunSqlQuery(Statement statement){
        this.statement = statement;
    }
    
    public abstract Object runSqlQuery(String query) throws Exception;

}
