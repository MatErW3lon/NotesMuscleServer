package MysqlQueries;

public interface SqlQueries {
    final String getAllUsers = "select * from login_info";

    static String createLoginUserQuery(String username, String password){
        return "select BilkentID from login_info where UserName = '" + username + "' and PassWord = '" + password + "'";
    }
}
