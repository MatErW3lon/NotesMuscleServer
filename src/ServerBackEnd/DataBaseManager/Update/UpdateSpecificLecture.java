package ServerBackEnd.DataBaseManager.Update;

import java.sql.Statement;

public class UpdateSpecificLecture extends RunSqlUpdate{

    public UpdateSpecificLecture(Statement statement){
        super(statement);
    }

    @Override
    public void runSqlUpdate(String update) throws Exception {
        statement.executeUpdate(update);
    }
    
}
