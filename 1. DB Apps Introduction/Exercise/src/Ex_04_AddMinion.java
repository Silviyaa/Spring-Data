import com.sun.jdi.ClassNotPreparedException;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Ex_04_AddMinion {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user","root");
        props.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",props);

        Scanner scanner = new Scanner(System.in);
        String[] minionInfo  = scanner.nextLine().split(" ");
        //String[] minionInfo  = split[1].trim().split(" ");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];
        String villainName = scanner.nextLine().split(" ")[1];

        int townId = getOrInsertTown(connection, minionTown);
        int villainId = getOrInsertVillain(connection,villainName);



        PreparedStatement insertMinion = connection.prepareStatement(
                "INSERT INTO minions(name,age,town_id) VALUES (?, ?, ?)");
        insertMinion.setString(1,minionName);
        insertMinion.setInt(2,minionAge);
        insertMinion.setInt(3,townId);
        insertMinion.executeUpdate();


        PreparedStatement getLastMinions = connection.prepareStatement(
                "SELECT id FROM minions ORDER BY id DESC LIMIT 1"
        );
        ResultSet lastMinionsSet = getLastMinions.executeQuery();
        lastMinionsSet.next();
        int lastMinionId = lastMinionsSet.getInt("id");



        PreparedStatement insertMinionsVillains = connection.prepareStatement(
                "INSERT INTO minions_villains VALUES (?,?)"
        );
        insertMinionsVillains.setInt(1,lastMinionId);
        insertMinionsVillains.setInt(2,villainId);
        insertMinionsVillains.executeUpdate();



        System.out.printf("Successfully added %s to be minion of %s.%n" ,minionName,villainName);

    }

    private static int getOrInsertVillain(Connection connection, String villainName) throws SQLException {
        PreparedStatement selectVillain = connection.prepareStatement
                ("SELECT id from villains where name = ?"); // съществува ли такъв град?
        selectVillain.setString(1, villainName);

        ResultSet villainSet = selectVillain.executeQuery();

        int villainId = 0;
        if(!villainSet.next()){
            PreparedStatement insertVillain = connection.prepareStatement
                    ("INSERT INTO villains(name,evilness_factor) VALUES (?, ?)"); // ако не съществува добавяме към базата
            insertVillain.setString(1,villainName);
            insertVillain.setString(2,"evil");
            insertVillain.executeUpdate();

            ResultSet newVillainSet = selectVillain.executeQuery();
            newVillainSet.next();
            villainId = newVillainSet.getInt("id");
            System.out.printf("Villain %s was added to the database.%n",villainName);
        }else {
            villainId = villainSet.getInt("id");
        }
        return 0;
    }

    private static int getOrInsertTown(Connection connection, String minionTown) throws SQLException {
        PreparedStatement selectTown = connection.prepareStatement
                ("SELECT id from towns where name = ?"); // съществува ли такъв град?
        selectTown.setString(1, minionTown);

        ResultSet townSet = selectTown.executeQuery();

        int townId = 0;
        if(!townSet.next()){
            PreparedStatement insertTown = connection.prepareStatement
                    ("INSERT INTO towns(name) VALUES (?)"); // ако не съществува добавяме към базата
            insertTown.setString(1,minionTown);
            insertTown.executeUpdate();

            ResultSet newTownSet = selectTown.executeQuery();
            newTownSet.next();
            townId = newTownSet.getInt("id"); // получаваме id-то на града, който да използваме в insert операцията
            System.out.printf("Town %s was added to the database.%n",minionTown);
        }else {
            townId = townSet.getInt("id");
        }
        return townId;
    }
}
