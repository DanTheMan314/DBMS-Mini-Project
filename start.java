import java.sql.*;

class Start 
{
    public static void main(String args[]) 
    {
        try 
        {
            // step1 load the driver class
            Class.forName("com.mysql.jdbc.Driver");

            // step2 create the connection object
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db?characterEncoding=latin1&useConfigs=maxPerformance", "scott", "tiger");

            // step3 create the statement object
            Statement stmt = con.createStatement();
            PreparedStatement ps = null;
            // step4 execute query
            String s = "create table stockings( pid integer primary key"
            +", product varchar(20), price integer, qty integer)";
            ps = con.prepareStatement(s);
            ps.execute();
            s = "CREATE TABLE ORDERS (Oid integer PRIMARY KEY, Prodid"
            + " integer,custid integer, qty integer, dateofclosing date)";
            ps = con.prepareStatement(s);
            ps.execute();
            s = "insert into stockings values(121,'cold coffee',88,10)";
            ps = con.prepareStatement(s);
            ps.execute();
            s = "insert into stockings values(122,'doughnuts',120,20)";
            ps = con.prepareStatement(s);
            ps.execute();
            //stmt.executeQuery("insert into orders values(1,121,5,to_date('28-02-2022','DD-MM-YYYY'))");
            //stmt.executeQuery("insert into orders values(2,122,13,to_date('28-02-2022','DD-MM-YYYY'))");
            ResultSet rsSt = stmt.executeQuery("select * from stockings");
            //ResultSet rsOr = stmt.executeQuery("select * from orders");
            while (rsSt.next())
            {System.out.println(rsSt.getInt(1) + "  " + rsSt.getString(2) + "  \tRs." + rsSt.getInt(3)+" \tx "+rsSt.getInt(4));}
            s = "drop table orders";
           /* ps = con.prepareStatement(s);
            boolean result = ps.execute();
            s = "drop table stockings";
            ps = con.prepareStatement(s);
            result = ps.execute();*/
            // step5 close the connection object
            con.close();
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }

    }
}