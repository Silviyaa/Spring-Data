import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Ex_09_IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
            Scanner scanner = new Scanner(System.in);
        Properties props = new Properties();
        props.setProperty("user","root");
        props.setProperty("password", "");

        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",props);

        System.out.println("Enter Minion ID: ");
        int minionId = Integer.parseInt(scanner.nextLine());

        CallableStatement callableStatement = connection.prepareCall("CALL usp_get_older(?)");
        callableStatement.setInt(1, minionId);
        callableStatement.executeUpdate();
        connection.close();
    }
}
