import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ex_08_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties props = new Properties();
        props.setProperty("user","root");
        props.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",props);

        System.out.println("Enter Minions ID: ");
        List<Integer> minionsId = Arrays.stream(scanner.nextLine()
                .split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());


        PreparedStatement updateMinionsAge = connection.
                prepareStatement("UPDATE minions SET age = age + 1, name = LOWER(name) WHERE id = ?");
        for (Integer id : minionsId) {

            updateMinionsAge.setInt(1, id);

            updateMinionsAge.executeUpdate();
        }
        PreparedStatement printMinions =
                connection.prepareStatement("SELECT name,age FROM minions");
        ResultSet allMinions = printMinions.executeQuery();
        while (allMinions.next()) {
            System.out.println(allMinions.getString("name") + " " + allMinions.getInt("age"));
        }
        connection.close();
    }
}
