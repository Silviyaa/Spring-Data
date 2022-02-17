import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username default (root): ");
        String user = scanner.nextLine();
        user = user.equals("") ? "root" : user;
        System.out.println();

        System.out.println("Enter password default (empty): ");
        String password = scanner.nextLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user",user);
        props.setProperty("password",password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",props);

        PreparedStatement stmt = connection.prepareStatement(
                "SELECT v.name,COUNT(DISTINCT mv.minion_id) as 'villain_min'\n" +
                "FROM villains as v\n" +
                "JOIN minions_villains as mv\n" +
                "ON v.id = mv.villain_id\n" +
                "GROUP BY v.name\n" +
                "having villain_min > 15\n" +
                "ORDER BY villain_min DESC;");


        String salary = scanner.nextLine();
        stmt.setDouble(1, Double.parseDouble(salary));
        ResultSet rs = stmt.executeQuery();


        while (rs.next()){
            System.out.println(rs.getString("Count: "));
        }
        connection.close();
    }
}
