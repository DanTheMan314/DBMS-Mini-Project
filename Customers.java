import java.util.Scanner;
import java.sql.*;

public class Customers {
    int a[] = new int[30];
    int i;
    // step1 load the driver class

    // step2 create the connection object
    Connection con = null;

    // step3 create the statement object
    Statement stmt = null;
    PreparedStatement ps = null;
    ResultSet rs;
    Scanner sc = new Scanner(System.in);
    String name, s;
    int pid, Qty, orderno = 9001, custno = 000;
    char ch = 'y';
    float price = 0, Tot_Price = 0, exist_tot = 0,new_tot;

    Customers() {
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

    public void Order(int flag) {
        try {
            Tot_Price = 0;
            if (flag == 0) {
                s = "select cid FROM Customers";
                rs = stmt.executeQuery(s);
                while (rs.next())
                    custno = rs.getInt(1);
                custno++;
                System.out.println("Enter your name");
                String cname = sc.nextLine();
                System.out.println("Enter your Phone Number");
                long no = sc.nextLong();
                System.out.println("Enter your Locality");
                String loc = sc.nextLine();
                loc = sc.nextLine();
                s = "insert into customers values(" + custno + ",'" + cname + "'," + no + ",'"
                        + loc + "',0)";
                ps = con.prepareStatement(s);
                ps.execute();
                exist_tot = 0;
            } else {
                System.out.println("Enter your name");
                String cname = sc.nextLine();
                s = "select cid,total FROM Customers where cname = " + cname;
                rs = stmt.executeQuery(s);
                rs.next();
                custno = rs.getInt(1);
                exist_tot = rs.getInt(2);
            }
            s = "select oid FROM Orders";
            rs = stmt.executeQuery(s);
            while (rs.next())
                orderno = rs.getInt(1);
            orderno++;
            System.out.println("Product list:\n");
            s = "select product, price, qty FROM Stockings";
            rs = stmt.executeQuery(s);
            System.out.println("Product name\tPrice\tQuantity\n");
            while (rs.next())
                System.out.println(rs.getString(1) + "\t" + rs.getFloat(2) + "\t" + rs.getInt(3));
            for (i = 0; i < 30; i++)
                a[i] = 0;
            i = 0;
            do {
                System.out.println("Enter the name of the product: ");
                name = sc.nextLine();
                System.out.println("Quantity to be bought: ");
                Qty = sc.nextInt();
                s = "select qty,price,pid from Stockings where product = '" + name + "'";
                rs = stmt.executeQuery(s);
                rs.next();
                int Quantity = rs.getInt(1);
                pid = rs.getInt(3);
                a[i] = pid;
                a[i + 1] = Qty;
                a[i + 2] = Quantity - a[i + 1];
                i += 2;
                if (i > 29) {
                    System.out.println("Exceeded cloud storage");
                    break;
                }
                if (Qty > Quantity) {
                    System.out.println("Exceeded quantity in stock!");
                    break;
                }
                price = rs.getFloat(2);
                Tot_Price += price * Qty;
                System.out.println("Total price: " + Tot_Price);
                System.out.println("Do you want to order more products?");
                ch = sc.next().charAt(0);
                name = sc.nextLine();
            } while (ch == 'y');

            for (int j = 0; j < i; j += 2) {
                s = "insert into orders values(" + orderno + "," + a[j] + "," + custno + ","
                        + a[j + 1] + ",SYSDATE())";
                ps = con.prepareStatement(s);
                ps.execute();
                s = "update stockings set qty = " + a[j + 2] + " WHERE pid = " + a[j];
                ps = con.prepareStatement(s);
                ps.execute();
            }
            new_tot = Tot_Price+exist_tot;
            s = "update customers set total = " + new_tot + " WHERE cid = " + custno;
            ps = con.prepareStatement(s);
            ps.execute();
            orderno++;
            // Set the date of closing as sysdate + random number
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
