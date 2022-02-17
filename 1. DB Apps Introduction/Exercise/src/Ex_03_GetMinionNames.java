import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Ex_03_GetMinionNames {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int villainId = Integer.parseInt(scanner.nextLine());

        Properties props = new Properties();
        props.setProperty("user","root");
        props.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",props);


        PreparedStatement stmt = connection.prepareStatement(
                "SELECT name FROM villains WHERE id = ?");
        stmt.setInt(1,villainId);

        ResultSet villainSet = stmt.executeQuery();

        if(!villainSet.next()){
            System.out.printf("No villain with ID %d exists in the database.",villainId);
            return;
        }

        String villName = villainSet.getString("name");
        System.out.println("Villain: "+ villName);
        //Request minions

        PreparedStatement minionStatement = connection.prepareStatement
                ("select name, age" +
                        " from minions as m " +
                        "join minions_villains as mv " +
                        "on mv.minion_id = m.id " +
                        "where mv.villain_id = ?");
        minionStatement.setInt(1,villainId);

       ResultSet minionSet = minionStatement.executeQuery();

        for (int i = 1; minionSet.next(); i++) {
            String name = minionSet.getString("name");
            int age = minionSet.getInt("age");
            System.out.printf("%d, %s %d%n",i,name,age);
        }


    }
}
