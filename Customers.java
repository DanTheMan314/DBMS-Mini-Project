import java.util.Scanner;
import java.sql.*;

public class Customers {
    int a[] = new int[20];
    int i;
    // step1 load the driver class
    Class.forName("com.mysql.jdbc.Driver");

    // step2 create the connection object
    Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/db?characterEncoding=latin1&useConfigs=maxPerformance", "scott", "tiger");

    // step3 create the statement object
    Statement stmt = con.createStatement();
    PreparedStatement ps = null;
    ResultSet rs;
    Scanner sc = new Scanner(System.in);
    String name,s;
    int pid, Qty, orderno = 9001, update;
    char ch = 'y';
    float price = 0, Tot_Price = 0;
    public void Order()
    {
        System.out.println("Product list:\n");
        s = "select product, price, qty FROM Stockings";
        rs = stmt.executeQuery(s);
        System.out.println("Product name\tPrice\tQuantity\n");
        while (rs.next())
            System.out.println(rs.getString(1) + "\t" + rs.getFloat(2) + "\t" + rs.getInt(3));
        for (i = 0; i < 20; i++)
            a[i] = 0;
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
            a[i] = pid;
            a[i + 1] = Qty;
            update = Quantity - a[i+1];
            i += 2;
            if (i > 19) {
                System.out.println("Exceeded cloud storage");
                break;
            }
            if (Qty > Quantity)
            {
                System.out.println("Exceeded quantity in stock!");
                break;
            }
            price = rs.getFloat(2);
            Tot_Price += price * Qty;
            System.out.println("Total price: " + Tot_Price);
            System.out.println("Do you want to order more products: ");
            ch = sc.next().charAt(0);
        } while (ch == 'y');
        System.out.println("Would you like to check out? ");
        ch = sc.next().charAt(0);
        if (ch == 'y') {
            for (int j = 0; j < i; j += 2) {
                s = "insert into orders values(" + orderno + "," + a[j] + ","
                    + a[j + 1] + ",TIMESTAMPADD(MONTH, 2, SYSDATE()))";
            ps = con.prepareStatement(s);
            ps.execute();
            s = "update stockings set qty = " + update + " WHERE pid = " + a[j];
            rs = stmt.executeQuery(s);       
        }
        orderno++;
        // Set the date of closing as sysdate + random number
        }
    }
}
