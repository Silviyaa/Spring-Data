import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password","");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/diablo",props);

        PreparedStatement statement = connection.prepareStatement
                ("SELECT u.user_name,u.first_name,u.last_name, COUNT(ug.id)\n" +
                        "FROM users as u\n" +
                        "JOIN users_games as ug\n" +
                        "ON u.id = ug.user_id\n" +
                        "WHERE u.user_name = ? \n" +
                        "group by ug.user_id;");


        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();

        statement.setString(1,username);

       ResultSet resultSet = statement.executeQuery();

       if(resultSet.next()){
           String dbUsername =  resultSet.getString("user_name");
           String dbFirstname =  resultSet.getString("first_name");
           String dbLastname = resultSet.getString("last_name");
           Integer dbGamesCount = resultSet.getInt("COUNT(ug.id)");
           System.out.printf("User: %s\n%s %s has played %d games\n",dbUsername,dbFirstname,dbLastname,dbGamesCount);
       }else{
           System.out.println("No such user exists");
       }
    }
}
