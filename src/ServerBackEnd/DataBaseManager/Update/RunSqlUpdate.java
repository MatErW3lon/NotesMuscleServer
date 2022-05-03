package ServerBackEnd.DataBaseManager.Update;


import java.sql.Statement;

public abstract class RunSqlUpdate {
    
    protected Statement statement;

    public RunSqlUpdate(Statement statement){
        this.statement = statement;
    }

    public abstract void runSqlUpdate(String update) throws Exception;

}
