import java.util.Scanner;
import java.sql.*;

public class Menu {
    public class Product {
        int pid, qty;
    }

    public static void main(String args[]) {
        try {
            Product list[] = new Product[10];
            int i;
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
            int pid, Qty, orderno = 0;
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
                            String s = "insert into stockings values(" + pid + ",'" + name + "'," + price + "," + Qty
                                    + ")";
                            ps = con.prepareStatement(s);
                            ps.execute();
                            break;
                        case 2:
                            System.out.println("Product list:\n");
                            s = "select product, price, qty FROM Stockings";
                            ResultSet rs = stmt.executeQuery(s);
                            System.out.println("Product name\tPrice\tQuantity\n");
                            while (rs.next())
                                System.out.println(rs.getString(1) + "\t" + rs.getFloat(2) + "\t" + rs.getInt(3));
                            System.out.println("Enter the name and id of the product to be updated: ");
                            name = sc.nextLine();
                            pid = sc.nextInt();
                            System.out.println("Quantity: ");
                            Qty = sc.nextInt();
                            s = "update stockings set qty = " + Qty + " WHERE pid = " + pid;
                            rs = stmt.executeQuery(s);
                            break;
                        case 3:
                            System.out.println("Product list:\n");
                            s = "select product, price, qty FROM Stockings";
                            rs = stmt.executeQuery(s);
                            System.out.println("Product name\tPrice\tQuantity\n");
                            while (rs.next())
                                System.out.println(rs.getString(1) + "\t" + rs.getFloat(2) + "\t" + rs.getInt(3));
                            System.out.println("Enter the id of the product on sale: ");
                            pid = sc.nextInt();
                            System.out.println("Enter Discount: ");
                            int disc = sc.nextInt();
                            s = "select price from Stockings where product = " + pid;
                            rs = stmt.executeQuery(s);
                            rs.next();
                            price = rs.getFloat(1);
                            price += price * (disc / 100);
                            s = "update stockings set price = " + price + " WHERE pid = " + pid;
                            rs = stmt.executeQuery(s);
                            break;
                        case 4:
                            System.out.println("Enter product name and id to be deleted: ");
                            name = sc.nextLine();
                            pid = sc.nextInt();
                            System.out.println("Product deleted!");
                            s = "delete from stockings where pid = " + pid;
                            rs = stmt.executeQuery(s);
                            break;
                        case 5:
                            System.out.println("Enter the name and id of the product: ");
                            name = sc.nextLine();
                            pid = sc.nextInt();
                            System.out.println("OrderID\tQuantity\n");
                            s = "select oid,qty from stockings s, orders o WHERE s.pid = o.prodid AND s.pid = "
                                    + pid + " AND dateofclosing >= SYSDATE";
                            rs = stmt.executeQuery(s);
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
                    while (rs.next())
                        System.out.println(rs.getString(1) + "\t" + rs.getFloat(2) + "\t" + rs.getInt(3));

                    for (i = 0; i < 10; i++)
                        list[i].pid = list[i].qty = 0;
                    i = 0;
                    do {
                        System.out.println("Enter the name of the product: ");
                        name = sc.nextLine();
                        name = sc.nextLine();
                        System.out.println("Quantity to be bought: ");
                        Qty = sc.nextInt();
                        s = "select qty,price,pid from Stockings where product = '" + name + "'";
                        rs = stmt.executeQuery(s);
                        rs.next();
                        int Quantity = rs.getInt(1);
                        pid = rs.getInt(3);
                        list[i].pid = pid;
                        list[i].qty = Qty;
                        i++;
                        if (i > 9) {
                            System.out.println("Exceeded cloud storage");
                            break;
                        }
                        if (Qty > Quantity)
                            System.out.println("Exceeded quantity in stock!");
                        System.out.println("Do you want to order more products: ");
                        ch = sc.next().charAt(0);
                        price = rs.getFloat(2);
                        Tot_Price += price * Qty;
                        System.out.println("Total price: " + Tot_Price);
                    } while (ch == 'y');
                    System.out.println("Would you like to check out? ");
                    ch = sc.next().charAt(0);

                    if (ch == 'y') {
                        s = "insert into orders values(" + orderno + "," + rs.getInt(3) + "," + Qty +
                                ",add_months(sysdate,1))";
                        ps = con.prepareStatement(s);
                        ps.execute();
                    }
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
