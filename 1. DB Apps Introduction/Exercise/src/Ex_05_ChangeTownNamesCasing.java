import javax.swing.text.html.HTMLDocument;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Ex_05_ChangeTownNamesCasing {
    public static void main(String[] args) throws SQLException {

        Properties props = new Properties();
        props.setProperty("user","root");
        props.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",props);

        Scanner scanner = new Scanner(System.in);
        String countryName = scanner.nextLine();

        PreparedStatement updateTownName = connection.prepareStatement(
                "update towns set name = UPPER(name) where country = ?");
        updateTownName.setString(1,countryName);


        int updateCount = updateTownName.executeUpdate();

        if(updateCount==0){
            System.out.println("No town names were affected.");
            return;
        }
        System.out.println(updateCount + " town names were affected.");


        PreparedStatement selectAllTowns = connection.prepareStatement(
                "SELECT name FROM towns WHERE country = ?"
        );
        selectAllTowns.setString(1,countryName);
        ResultSet townsSet = selectAllTowns.executeQuery();

        List<String> towns = new ArrayList<>();
        while (townsSet.next()){
            String townName = townsSet.getString("name");
            towns.add(townName);
        }
        System.out.print(towns);
    }
}
