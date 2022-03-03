import java.util.Scanner;
import java.sql.*;

public class Menu {
    public static void main(String args[]) {
        try {
            //step1 load the driver class
            Class.forName("com.mysql.jdbc.Driver");

            // step2 create the connection object
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db?characterEncoding=latin1&useConfigs=maxPerformance", "scott", "tiger");

            // step3 create the statement object
            Statement stmt = con.createStatement();
            //PreparedStatement ps = null;

            Scanner sc = new Scanner(System.in);
            String name;
            //int pid, Qty, update, t=0; 
            int orderno = 9001;

            String s = "select oid FROM orders";
            ResultSet rs = stmt.executeQuery(s);
            rs.next();
            orderno = rs.getInt(1) + 1;
            System.out.print(rs.getInt(1));
            System.out.println(" " + orderno + " orderno " + t + " t");
            char ch = 'y';
            //float price = 0, Tot_Price = 0;
            do {
                System.out.print("User choice:\n1.Admin\n2.Customer\nEnter choice: ");
                int c1 = sc.nextInt();
                if (c1 == 1) {
                    Admin oba = new Admin();
                    System.out.print(
                            "Admin functions:\n1.Add a new product\n2.Update inventory\n3.Change price\n4.Delete a product\n5.View all orders for a certain product\nEnter choice: ");
                    int c2 = sc.nextInt();
                    name = sc.nextLine();
                    switch (c2) {
                        case 1:
                            oba.Add_Product();
                            break;
                        case 2:
                            oba.Delete_Product();
                            break;
                        case 3:
                            oba.Change_Price();
                            break;
                        case 4:
                            oba.Delete_Product();
                            break;
                        case 5:
                            oba.View_Orders();
                            break;
                        default:
                            System.out.println("Invalid choice!");
                    }
                } 
                else if (c1 == 2) {
                    Customers obc = new Customers();
                    obc.Order();
                }
                System.out.println("Do you want to continue: ");
                ch = sc.next().charAt(0);
            } while (ch == 'y');
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
