import jdk.jshell.spi.SPIResolutionException;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Ex_04_AddMinion {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties props = new Properties();
        props.setProperty("user","root");
        props.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",props);

        PreparedStatement preparedStatement = connection.prepareStatement("");

        //Minion: Robert 14 Berlin
        //Villain: Gru

        String[] split = scanner.nextLine().split(":");
        String[] minionInfo  = scanner.nextLine().split(" ");
        //String[] minionInfo  = split[1].trim().split(" ");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];

        String villainName = scanner.nextLine().split(" ")[1];

        PreparedStatement selectTown = connection.prepareStatement
                ("SELECT id from towns where name = ?");
        selectTown.setString(1,minionInfo[3]);

        ResultSet townSet = selectTown.executeQuery();

        int townId = 0;
        if(!townSet.next()){
            PreparedStatement insertTown = connection.prepareStatement
                    ("INSERT INTO towns(name) VALUES (?)");
            insertTown.setString(1,minionTown);
            insertTown.executeUpdate();

            ResultSet newTownSet = selectTown.executeQuery();
            townId = newTownSet.getInt("id");
        }else {
            townId = townSet.getInt("id");
        }


    }
}
