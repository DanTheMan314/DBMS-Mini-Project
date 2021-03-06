import java.util.Scanner;
import java.sql.*;

public class Admin {
    int pid, Qty, orderno = 9001, update;
    int price = 0, Tot_Price = 0;
    String s, name;
    Scanner sc = new Scanner(System.in);
    Connection con = null;
    Statement stmt = null;
    PreparedStatement ps = null;
    ResultSet rs;

    Admin() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db?characterEncoding=latin1&useConfigs=maxPerformance", "scott",
                    "tiger");
            stmt = con.createStatement();
            // step1 load the driver class
            Class.forName("com.mysql.jdbc.Driver");
            // step2 create the connection object
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
        price = sc.nextInt();
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
            s = "select pid, product, price, qty FROM Stockings";
            rs = stmt.executeQuery(s);
            System.out.println("Pid\tProduct name\tPrice\tQuantity\n----------------------------------------");
            while (rs.next())
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2) + "\t" + rs.getInt(3) 
                + "\t" + rs.getInt(4));
            System.out.println("Enter the id of the product to be updated: ");
            pid = sc.nextInt();
            System.out.println("Quantity: ");
            Qty = sc.nextInt();
            s = "update stockings set qty = " + Qty + " WHERE pid = " + pid;
            ps = con.prepareStatement(s);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Change_Price() {
        try {
            System.out.println("Product list:\n");
            s = "select pid, product, price, qty FROM Stockings";
            rs = stmt.executeQuery(s);
            System.out.println("Pid\tProduct name\tPrice\tQuantity\n----------------------------------------");
            while (rs.next())
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2) + "\t" + 
                rs.getInt(3) + "\t" + rs.getInt(4));
            System.out.println("Enter the id of the product on sale: ");
            pid = sc.nextInt();
            System.out.println("Enter Discount: ");
            float disc = sc.nextInt();
            s = "select price from Stockings where pid = " + pid;
            rs = stmt.executeQuery(s);
            rs.next();
            price = rs.getInt(1);
            price -= price * (disc / 100);
            System.out.println("New price: ");
            s = "update stockings set price = " + price + " WHERE pid = " + pid;
            ps = con.prepareStatement(s);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Delete_Product() {
        try {
            System.out.println("Product list:\n");
            s = "select pid, product, price, qty FROM Stockings";
            rs = stmt.executeQuery(s);
            System.out.println("Pid\tProduct name\tPrice\tQuantity\n----------------------------------------");
            while (rs.next())
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2) + "\t" + 
                rs.getInt(3) + "\t" + rs.getInt(4));
            System.out.println("Enter product id to be deleted: ");
            pid = sc.nextInt();
            System.out.println("Product deleted!");
            s = "delete from stockings where pid = " + pid;
            ps = con.prepareStatement(s);
            ps.execute();
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void View_Orders() {
        try {
            System.out.println("Product list:\n");
            s = "select pid, product, price, qty FROM Stockings";
            rs = stmt.executeQuery(s);
            System.out.println("Pid\tProduct name\tPrice\tQuantity\n----------------------------------------");
            while (rs.next())
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getInt(3)+"\t"
                + rs.getInt(4));
            System.out.println("Enter the id of the product: ");
            pid = sc.nextInt();
            System.out.println("\nOrderID\tQty\tCustomer ID\n--------------------------");
            s = "select oid,o.qty,custid from stockings s, orders o WHERE s.pid = o.prodid AND s.pid = "
            + pid;
            rs = stmt.executeQuery(s);
            while (rs.next())
                System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3));
            // SELECT OrderID, Qty AS Quantity Ordered FROM Stocking s, Orders o WHERE
            // s.ProductID == o.pid AND s.ProductID == pid AND Date_Of_Closing >= SYSDATE;
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
