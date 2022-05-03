package ServerBackEnd.DataBaseManager.Update;

import java.sql.Statement;

import NetWorkProtocol.NetworkProtocol;
import NotesMuscles.io.AccountCreationException;

public class NewAccountUpdate extends RunSqlUpdate{
    
    public NewAccountUpdate(Statement statement){
        super(statement);
    }

    @Override
    public void runSqlUpdate(String update) throws Exception {
        statement.executeUpdate(update);
        
    }

    private int countDataDelimiter(String update){
        int count = 0;
        for(int i = 0; i < update.length(); i++){
            if(update.charAt(i) ==NetworkProtocol.dataDelimiter.charAt(0)){
                count++;
            }
        }
        return count;
    }
}
