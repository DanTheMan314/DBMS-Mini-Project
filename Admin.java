import java.util.Scanner;
import java.sql.*;
import java.lang.*;

public class Admin {
    Scanner sc = new Scanner(System.in);
    String s, name;
    int pid, Qty, orderno = 9001, update;
    float price = 0, Tot_Price = 0;
    // step1 load the driver class
    Class.forName("com.mysql.jdbc.Driver");

    // step2 create the connection object
    Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/db?characterEncoding=latin1&useConfigs=maxPerformance", "scott", "tiger");

    // step3 create the statement object
    Statement stmt = con.createStatement();
    PreparedStatement ps = null;
    ResultSet rs;
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
        ps = con.prepareStatement(s);
        ps.execute();
        break;
    }
    public void Update_Inventory()
    {
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
        break;
    }
    public void Change_Price()
    {
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
    }
}
