import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties props = new Properties();
        props.setProperty("user","root");
        props.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",props);

        PreparedStatement stmt = connection.prepareStatement(
                "SELECT v.name,COUNT(DISTINCT mv.minion_id) as 'villain_min'\n" +
                "FROM villains as v\n" +
                "JOIN minions_villains as mv\n" +
                "ON v.id = mv.villain_id\n" +
                "GROUP BY v.name\n" +
                "having villain_min > 15\n" +
                "ORDER BY villain_min DESC;");


        String names = scanner.nextLine();
        stmt.setString(1,names);
        ResultSet rs = stmt.getResultSet();


        while (rs.next()){
            System.out.printf("%s %d", rs.getString("name"), rs.getInt("count"));
        }
        connection.close();
    }
}
