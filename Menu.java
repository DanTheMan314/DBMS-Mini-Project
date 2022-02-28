import java.util.Scanner;
import java.sql.*;

public class Menu {
    public static void main(String args[]) {
        try {
            // step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // step2 create the connection object
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl2", "scott", "tigress");

            // step3 create the statement object
            Statement stmt = con.createStatement();
            PreparedStatement ps = null;

            Scanner sc = new Scanner(System.in);
            String name;
            int pid, Qty;
            char ch = 'y';
            float price = 0, Tot_Price = 0;
            do {
                System.out.print("User choice:\n1.Admin\n2.Customer\nEnter choice: ");
                int c1 = sc.nextInt();
                if (c1 == 1) {
                    System.out.print(
                            "Admin functions:\n1.Add a new product\n2.Update inventory\n3.Change price\n4.Delete a product\n5.View all orders for a certain product\nEnter choice: ");
                    int c2 = sc.nextInt();
                    name = sc.nextLine();
                    switch (c2) {
                        case 1:
                            System.out.println("Enter the product details:\nProduct name: ");
                            
                            name = sc.nextLine();
                            System.out.println("Product id: ");
                            pid = sc.nextInt();
                            System.out.println("Price: ");
                            price = sc.nextFloat();
                            System.out.println("Quantity: ");
                            Qty = sc.nextInt();
                            String s = "insert into stockings values("+pid+",'"+name+"',"+price+","+Qty+")";
                            ps = con.prepareStatement(s);
                            ps.execute();
                            break;
                        case 2:
                        System.out.println("Product list:\n");
                        s = "select product, price, qty FROM Stockings";
                        ResultSet rs = stmt.executeQuery(s);
                        System.out.println("Product name\tPrice\tQuantity\n");
                        while(rs.next())
                            System.out.println(rs.getString(1)+"\t"+rs.getFloat(2)+"\t"+rs.getInt(3));
                            System.out.println("Enter the name and id of the product to be updated: ");
                            name = sc.nextLine();
                            pid = sc.nextInt();
                            System.out.println("Quantity: ");
                            Qty = sc.nextInt();
                            s = "update stockings set qty = "+Qty+" WHERE pid = "+pid;
                            rs = stmt.executeQuery(s);
                            break;
                            case 3:
                            System.out.println("Product list:\n");
                            s = "select product, price, qty FROM Stockings";
                            rs = stmt.executeQuery(s);
                            System.out.println("Product name\tPrice\tQuantity\n");
                            while(rs.next())
                                System.out.println(rs.getString(1)+"\t"+rs.getFloat(2)+"\t"+rs.getInt(3));
                            System.out.println("Enter the name and id of the product on sale: ");
                            name = sc.nextLine();
                            pid = sc.nextInt();
                            System.out.println("Enter Discount: ");
                            int disc = sc.nextInt();
                            // a query to get price dunno how exactly
                            price += price * (disc / 100);
                            // UPDATE Stocking SET Price = price WHERE ProductID == pid;
                            break;
                        case 4:
                            System.out.println("Enter product name and id to be deleted: ");
                            name = sc.nextLine();
                            pid = sc.nextInt();
                            System.out.println("Product deleted!");
                            // DELETE FROM Stocking WHERE ProductID = pid;
                            break;
                        case 5:
                            System.out.println("Enter the name and id of the product: ");
                            name = sc.nextLine();
                            pid = sc.nextInt();
                            // SELECT OrderID, Qty AS Quantity Ordered FROM Stocking s, Orders o WHERE
                            // s.ProductID == o.pid AND s.ProductID == pid AND Date_Of_Closing >= SYSDATE;
                            break;
                        default:
                            System.out.println("Invalid choice!");
                    }
                } else if (c1 == 2) {
                    System.out.println("Product list:\n");
                    String s = "select product, price, qty FROM Stockings";
                    ResultSet rs = stmt.executeQuery(s);
                    System.out.println("Product name\tPrice\tQuantity\n");
                    while(rs.next())
                        System.out.println(rs.getString(1)+"\t"+rs.getFloat(2)+"\t"+rs.getInt(3));
                    
                    do {
                        System.out.println("Enter the name of the product: ");
                        name = sc.nextLine();
                        name = sc.nextLine();
                        System.out.println("Quantity to be bought: ");
                        Qty = sc.nextInt();
                        s = "select qty,price from Stockings where product = '"+name+"'";
                        rs = stmt.executeQuery(s);
                        rs.next();
                        int Quantity = rs.getInt(1);
                        if(Qty>Quantity)
                            System.out.println("Exceeded quantity in stock!");
                        System.out.println("Do you want to order more products: ");
                        ch = sc.next().charAt(0);
                        price = rs.getFloat(2);
                        Tot_Price += price * Qty;
                        System.out.println("Total price: " + Tot_Price);
                    } while (ch == 'y');
                    
                    // Set the date of closing as sysdate + random number
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
