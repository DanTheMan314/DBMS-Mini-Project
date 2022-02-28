import java.util.Scanner;

public class Menu {
    public static void main(String args[])  
    {    
     Scanner sc = new Scanner(System.in);
     String name;
     int pid, Qty;
     char ch = 'y';
     float price = 0, Tot_Price = 0;
     System.out.print("User choice:\n1.Admin\n2.Customer\nEnter choice: ");  
     int c1 = sc.nextInt();
     if(c1 == 1)
     {
        System.out.print("Admin functions:\n1.Add a new product\n2.Update inventory\n3.Change price\n4.Delete a product\n5.View all orders for a certain product\nEnter choice: ");
        int c2 = sc.nextInt();
        switch(c2)
        {
            case 1: 
            System.out.println("Enter the product details:\nProduct name: ");
            name = sc.nextLine();
            System.out.println("Product id: ");
            pid = sc.nextInt();
            System.out.println("Price: ");
            price = sc.nextFloat();
            System.out.println("Quantity: ");
            Qty = sc.nextInt();
            //INSERT INTO Stocking VALUES(pid,name,qty,price);
            break;
            case 2:
            System.out.println("Product list:\n");
            //SELECT ProductName, Quantity, Price FROM Stocking;
            System.out.println("Enter the name and id of the product to be updated: ");
            name = sc.nextLine();
            pid = sc.nextInt();
            System.out.println("Quantity: ");
            Qty = sc.nextInt();
            //UPDATE Stocking SET Quantity = Qty WHERE ProductID == pid;
            break;
            case 3:
            System.out.println("Product list:\n");
            //SELECT ProductName, Quantity, Price FROM Stocking;
            System.out.println("Enter the name and id of the product on sale: ");
            name = sc.nextLine();
            pid = sc.nextInt();
            System.out.println("Enter Discount: ");
            int disc = sc.nextInt();
            //a query to get price dunno how exactly
            price += price*(disc/100);
            //UPDATE Stocking SET Price = price WHERE ProductID == pid;
            break;
            case 4:
            System.out.println("Enter product name and id to be deleted: ");
            name = sc.nextLine();
            pid = sc.nextInt();
            System.out.println("Product deleted!");
            //DELETE FROM Stocking WHERE ProductID = pid;
            break;
            case 5:
            System.out.println("Enter the name and id of the product: ");
            name = sc.nextLine();
            pid = sc.nextInt();
            //SELECT OrderID, Qty AS Quantity Ordered FROM Stocking s, Orders o WHERE s.ProductID == o.pid AND s.ProductID == pid AND Date_Of_Closing >= SYSDATE;   
            break;
            default:
            System.out.println("Invalid choice!");
        }
     }
     else if(c1 == 2)
     {
        System.out.println("Product list:\n");
        //SELECT ProductName, Quantity, Price FROM Stocking;
        do
        {
            System.out.println("Enter the name of the product: ");
            name = sc.nextLine();
            System.out.println("Quantity to be bought: ");
            Qty = sc.nextInt();
            /*Get Quantity from db somehow
            if(Qty > Quantity)
            {
                System.out.println("Exceeded quantity in stock!");
            }*/
            System.out.println("Do you want to order more products: ");
            ch = sc.next().charAt(0);
            Tot_Price += 
        }while(ch=='y');
        //Set the date of closing as sysdate + random number        
     }
    }
}   
