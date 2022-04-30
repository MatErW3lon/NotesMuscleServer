package ServerBackEnd.DataBaseManager;

import java.sql.Statement;

abstract class RunSqlQuery {

    protected Statement statement;

    public RunSqlQuery(Statement statement){
        this.statement = statement;
    }
    
    public abstract Object runSqlQuery(String query) throws Exception;

}
