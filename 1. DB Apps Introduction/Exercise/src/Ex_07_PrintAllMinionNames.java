import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Ex_07_PrintAllMinionNames {


    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://127.0.0.1:3306/minions_db", properties);

        List<String> minions = new ArrayList<>();

        PreparedStatement selectAllMinionNames = connection.prepareStatement("SELECT name FROM minions");
        ResultSet namesSet = selectAllMinionNames.executeQuery();

        while (namesSet.next()) {
            String minionsNames = namesSet.getString(1);
            minions.add(minionsNames);
        }
        int startIndex = 0;
        int endIndex = minions.size() - 1;

        for (int i = 0; i < minions.size(); i++) {
            String output = i % 2 == 0 ? minions.get(startIndex++) : minions.get(endIndex--);
            System.out.println(output);
        }
        connection.close();
    }
}
