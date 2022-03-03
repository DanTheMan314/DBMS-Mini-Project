import java.util.Scanner;
import java.sql.*;

public class Admin {
    int pid, Qty, orderno = 9001, update;
    float price = 0, Tot_Price = 0;
    String s, name;
    Scanner sc = new Scanner(System.in);
    Connection con = null;
    Statement stmt = null;
    PreparedStatement ps = null;
    ResultSet rs;

    Admin() {
        name = sc.nextLine();
        try {
            stmt = con.createStatement();
            // step1 load the driver class
            Class.forName("com.mysql.jdbc.Driver");
            // step2 create the connection object
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db?characterEncoding=latin1&useConfigs=maxPerformance", "scott",
                    "tiger");
        } catch (Exception e) {
            System.out.println(e);
        }
        // step3 create the statement object
    }

    public void Add_Product() {
        System.out.println("Enter the product details:\nProduct name: ");
        name = sc.nextLine();
        System.out.println("Product id: ");
        pid = sc.nextInt();
        System.out.println("Price: ");
        price = sc.nextFloat();
        System.out.println("Quantity: ");
        Qty = sc.nextInt();
        s = "insert into stockings values(" + pid + ",'" + name + "'," + price + "," + Qty
                + ")";
        try {
            ps = con.prepareStatement(s);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Update_Inventory() {
        try {
            System.out.println("Product list:\n");
            s = "select product, price, qty FROM Stockings";
            rs = stmt.executeQuery(s);
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Change_Price() {
        try {
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Delete_Product() {
        System.out.println("Enter product name and id to be deleted: ");
        name = sc.nextLine();
        pid = sc.nextInt();
        System.out.println("Product deleted!");
        s = "delete from stockings where pid = " + pid;
        try {
            rs = stmt.executeQuery(s);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void View_Orders()
    {
        try{
            System.out.println("Enter the name and id of the product: ");
            name = sc.nextLine();
            pid = sc.nextInt();
            System.out.println("OrderID\tQuantity\n");
            s = "select oid,qty from stockings s, orders o WHERE s.pid = o.prodid AND s.pid = "
                + pid + " AND dateofclosing >= SYSDATE";
            rs = stmt.executeQuery(s);
            // SELECT OrderID, Qty AS Quantity Ordered FROM Stocking s, Orders o WHERE
            // s.ProductID == o.pid AND s.ProductID == pid AND Date_Of_Closing >= SYSDATE;
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
