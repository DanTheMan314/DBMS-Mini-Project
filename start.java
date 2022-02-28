import java.sql.*;

class Start 
{
    public static void main(String args[]) 
    {
        try 
        {
            // step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // step2 create the connection object
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl2", "scott", "tigress");

            // step3 create the statement object
            Statement stmt = con.createStatement();

            // step4 execute query
            stmt.executeQuery("create table stockings( pid number primary key"
            +", product varchar(20), price number, qty number)");
            stmt.executeQuery("CREATE TABLE ORDERS (Oid number PRIMARY KEY, Prodid NUMBER"
            +",FOREIGN KEY (Prodid) REFERENCES Stockings(Pid), qty number, dateofclosing date)");
            //while (rs.next())
            //System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            
            stmt.executeQuery("drop table orders");
            stmt.executeQuery("drop table stockings");
            // step5 close the connection object
            con.close();
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }

    }
}