import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.*;
public class Account {
    private Integer accountNo;
    private Double balance;
    private String uname;
    private String pass;
    private String bcryptHash;

    public void registerUser(String uname, String pass){
        String url = "jdbc:mysql://localhost:3306/javaBank";
        String u = "root";
        String p = "britneybitch";
        bcryptHash = BCrypt.withDefaults().hashToString(12,pass.toCharArray());
        try {
            Connection connection = DriverManager.getConnection(url, u, p);
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            String getBack = "INSERT INTO Accounts (username, password) VALUES (?,?)";
            PreparedStatement pstmt = connection.prepareStatement(getBack);
            resultSet = statement.executeQuery("SELECT * FROM Accounts");
            pstmt.setString(1,uname);
            pstmt.setString(2, bcryptHash);
            pstmt.executeUpdate();

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkPass(String pass){
        BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), bcryptHash);
        return result.verified;
    }

    /* Check if username and password already exists in database
    public boolean checkDupe(){
        if(uname && pass ==)
    }
     */




}
