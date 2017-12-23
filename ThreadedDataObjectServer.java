import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;


public class ThreadedDataObjectServer
{  public static void main(String[] args ) 
   {  
	
      try 
      {  ServerSocket s = new ServerSocket(7044);
         
         for (;;)
         {  Socket incoming = s.accept( );
            new ThreadedDataObjectHandler(incoming).start();
             
	   	 }   
      }
      catch (Exception e) 
      {  System.out.println(e);
      } 
   } 
}

class ThreadedDataObjectHandler extends Thread
{  public ThreadedDataObjectHandler(Socket i) 
   { 
   		incoming = i;
   }
   
   public void run()
   {  try 
      { 	ObjectInputStream in =
				new ObjectInputStream(incoming.getInputStream());

		ObjectOutputStream out =
				new ObjectOutputStream(incoming.getOutputStream());

		myObject = (DataObject)in.readObject();

		System.out.println("Message read: " + myObject.getMessage());
                
                String[] loginStream = myObject.getMessage().split(":");
                String flag = loginStream[0];
                String parameters = loginStream[1];
                
                               
                String url = "sql2.njit.edu";
		String ucid = "ns632";	
		String dbpassword = "8rN8zy9nk";
               
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		}
		catch (Exception e) {
			System.err.println("Unable to load driver.");
			e.printStackTrace();
		}
		System.out.println("Driver loaded.");
		System.out.println("Establishing connection . . . ");
		try {
			Connection conn;
	 
			conn = DriverManager.getConnection("jdbc:mysql://"+url+"/"+ucid+"?user="+ucid+"&password="+dbpassword);
			
			System.out.println("Connection established.");			
			System.out.println("Creating a Statement object... ");

			Statement stmt = conn.createStatement();
			System.out.println("Statement object created...");
			
                      if (flag.equals("1")){               
               
                
                        String[] values = parameters.split(",");
                        String username = values[0];
                        String password = values[1];
                        String role = values[2];   
                        
                        PreparedStatement ps;
                        ps = conn.prepareStatement("SELECT `Email` , `Password` , `Role`  FROM `login_details` WHERE `Email` = ? AND `Password` = ? AND `Role` = ?");
            
                        ps.setString(1, username);
                        ps.setString(2, password);
                        ps.setString(3, role);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
                                myObject.setMessage(username+","+role+",Exists");
                                out.writeObject(myObject);
                                System.out.println("Message read:" + myObject.getMessage());	
                        }
                        else
                        {
                            myObject.setMessage("Invalid User");
                            out.writeObject(myObject);        
                            System.out.println("Message read:" + myObject.getMessage());
                        }
                        ps.close();
                        rs.close();
                      }
                        
               
                      
                      if (flag.equals("2")){               
               
                
                        String[] values = parameters.split(",");
                        String firstname = values[0];
                        String lastname = values[1];
                        String password =  values[3];
                        String email = values[2];
                        String ucd = values[4];
                        String role = values[5];  
                        
                        PreparedStatement ps;
                        ps = conn.prepareStatement("INSERT INTO `login_details`(`UCID`, `First_Name`, `Last_Name`, `Email`, `Password`, `Role`) VALUES (?,?,?,?,?,?)");
                        ps.setString(1, ucd);    
                        ps.setString(2, firstname);
                        ps.setString(3, lastname);
                        ps.setString(4, email);
			ps.setString(5, password);
                        ps.setString(6, role);
                        ps.executeUpdate();
			PreparedStatement ps1;
                        ps1 = conn.prepareStatement("INSERT INTO `user_details`(`ucid`, `first_name`, `last_name`, `email`) VALUES (?,?,?,?)");
                        ps1.setString(1, ucd);    
                        ps1.setString(2, firstname);
                        ps1.setString(3, lastname);
                        ps1.setString(4, email);
			
                        ps1.executeUpdate();
                        
                                myObject.setMessage("User Registered");
                                out.writeObject(myObject);
                                System.out.println("Message read:" + myObject.getMessage());	
                        
                        ps.close();
                        ps.close();
                        
                }
                      
                      if (flag.equals("3")){               
               
                
                        String email = parameters;
                       
                        PreparedStatement ps;
                        ps = conn.prepareStatement("SELECT `ucid`,`first_name`,`last_name`,`DOB`,`email`,`address`,`phone`,`room`,`hall` FROM `user_details` WHERE `email` = ?");
            
                        ps.setString(1, email);
                   	ResultSet rs = ps.executeQuery();
                         while (rs.next())
                        {
                          String ucd = rs.getString("ucid");
                          String firstName = rs.getString("first_name");
                          String lastName = rs.getString("last_name");
                          String dob = rs.getDate("DOB").toString();
                          String emailout = rs.getString("email");
                          String address = rs.getString("address");
                          String phone = rs.getString("phone");
                          String room = rs.getString("room");
                          String hall = rs.getString("hall");

                          // print the results
                          String OutputFormat = ucd+","+firstName+","+lastName+","+dob+","+emailout+","+address+","+phone+","+room+","+hall;
                        
                                myObject.setMessage(OutputFormat+",viewPro");
                                out.writeObject(myObject);
                                System.out.println("Message read:" + myObject.getMessage());	
                        }
                        
                        ps.close();
                        rs.close();
                        
                }
                      if (flag.equals("4")){               
               
                
                        String[] values = parameters.split(",");
                        String firstname = values[0];
                        String lastname = values[1];
                        String dob = values[2];
                        String email = values[3];
                        String uid = values[4];
                        String address = values[5];
                        String phone = values[6];
                        String room = values[7];
                        String hall = values[8];
                        String primaryKey = values[9];
                        Date date=Date.valueOf(dob);                        
                        PreparedStatement ps;
                        ps = conn.prepareStatement("UPDATE `user_details` SET `ucid`=?,`first_name`=?,`last_name`=?,`DOB`=?,`email`=?,`address`=?,`phone`=?,`room`=?,`hall`=? WHERE `email` = ?");
                        ps.setString(1, uid);    
                        ps.setString(2, firstname);
                        ps.setString(3, lastname);
                        ps.setDate(4, date);
			ps.setString(5, email);
                        ps.setString(6, address);
                        ps.setString(7, phone);
                        ps.setString(8, room);
                        ps.setString(9, hall);
                        ps.setString(10, primaryKey);
                        ps.executeUpdate();
			
                                myObject.setMessage("User Details Updated");
                                out.writeObject(myObject);
                                System.out.println("Message read:" + myObject.getMessage());	
                        
                        ps.close();
                       
                }
                      if (flag.equals("5")){               
               
                String[] values = parameters.split(",");
                        String uci = values[0];
                        String login = values[1];
                        try{
                        PreparedStatement ps;
                        ps = conn.prepareStatement("SELECT `ucid`,`first_name`,`last_name`,`DOB`,`email`,`address`,`phone`,`room`,`hall` FROM `user_details` WHERE `ucid` = ?");
            
                        ps.setString(1, uci);
                   	ResultSet rs = ps.executeQuery();
                         while (rs.next())
                        {
                          String ucd = rs.getString("ucid");
                          String firstName = rs.getString("first_name");
                          String lastName = rs.getString("last_name");
                          String dob = rs.getDate("DOB").toString();
                          String emailout = rs.getString("email");
                          String address = rs.getString("address");
                          String phone = rs.getString("phone");
                          String room = rs.getString("room");
                          String hall = rs.getString("hall");
                         
                          String OutputFormat = ucd+","+firstName+","+lastName+","+dob+","+emailout+","+address+","+phone+","+room+","+hall+","+login;
                        
                                myObject.setMessage(OutputFormat+",searchPro");
                                out.writeObject(myObject);
                                System.out.println("Message read:" + myObject.getMessage());	
                        }
                        
                        ps.close();
                        rs.close();
                        }
                        catch (Exception e){
                             JOptionPane.showMessageDialog(null, "User Not Found");
                        }
                        
                      }
                      if (flag.equals("6")){               
               
                
                        String email = parameters;
                        
                        PreparedStatement ps;
                        ps = conn.prepareStatement("SELECT `ucid`,`first_name`,`last_name`,`DOB`,`email`,`address`,`phone`,`room`,`hall` FROM `user_details` WHERE `email` = ?");
            
                        ps.setString(1, email);
                   	ResultSet rs = ps.executeQuery();
                         while (rs.next())
                        {
                          String ucd = rs.getString("ucid");
                          String firstName = rs.getString("first_name");
                          String lastName = rs.getString("last_name");
                          String dob = rs.getDate("DOB").toString();
                          String emailout = rs.getString("email");
                          String address = rs.getString("address");
                          String phone = rs.getString("phone");
                          String room = rs.getString("room");
                          String hall = rs.getString("hall");

                          // print the results
                          String OutputFormat = ucd+","+firstName+","+lastName+","+dob+","+emailout+","+address+","+phone+","+room+","+hall;
                        
                                myObject.setMessage(OutputFormat+",viewRcPro");
                                out.writeObject(myObject);
                                System.out.println("Message read:" + myObject.getMessage());	
                        }
                        
                        ps.close();
                        rs.close();
                }
                      if (flag.equals("7")){               
               
                String[] values = parameters.split(",");
                        String uci = values[0];
                        String login = values[1];
                        try{
                        PreparedStatement ps;
                        ps = conn.prepareStatement("SELECT `ucid`,`first_name`,`last_name`,`DOB`,`email`,`address`,`phone`,`room`,`hall` FROM `user_details` WHERE `ucid` = ?");
            
                        ps.setString(1, uci);
                   	ResultSet rs = ps.executeQuery();
                         while (rs.next())
                        {
                          String ucd = rs.getString("ucid");
                          String firstName = rs.getString("first_name");
                          String lastName = rs.getString("last_name");
                          String dob = rs.getDate("DOB").toString();
                          String emailout = rs.getString("email");
                          String address = rs.getString("address");
                          String phone = rs.getString("phone");
                          String room = rs.getString("room");
                          String hall = rs.getString("hall");
                         

                          // print the results
                          String OutputFormat = ucd+","+firstName+","+lastName+","+dob+","+emailout+","+address+","+phone+","+room+","+hall+","+login;
                        
                                myObject.setMessage(OutputFormat+",searchRcPro");
                                out.writeObject(myObject);
                                System.out.println("Message read:" + myObject.getMessage());	
                        }
                        
                        ps.close();
                        rs.close();
                        }
                        catch (Exception e){
                             JOptionPane.showMessageDialog(null, "User Not Found");
                        }
                      
                        
                }
                       if (flag.equals("8")){               
               
                
                        String[] values = parameters.split(",");
                        String firstname = values[0];
                        String lastname = values[1];
                        String dob = values[2];
                        String email = values[3];
                        String uid = values[4];
                        String address = values[5];
                        String phone = values[6];
                        String room = values[7];
                        String hall = values[8];
                        String primaryKey = values[9];
                        Date date=Date.valueOf(dob);                        
                        PreparedStatement ps;
                        ps = conn.prepareStatement("UPDATE `user_details` SET `ucid`=?,`first_name`=?,`last_name`=?,`DOB`=?,`email`=?,`address`=?,`phone`=?,`room`=?,`hall`=? WHERE `ucid` = ?");
                        ps.setString(1, uid);    
                        ps.setString(2, firstname);
                        ps.setString(3, lastname);
                        ps.setDate(4, date);
			ps.setString(5, email);
                        ps.setString(6, address);
                        ps.setString(7, phone);
                        ps.setString(8, room);
                        ps.setString(9, hall);
                        ps.setString(10, primaryKey);
                        ps.executeUpdate();
			
                                myObject.setMessage("User Details Updated");
                                out.writeObject(myObject);
                                System.out.println("Message read:" + myObject.getMessage());	
                        
                        ps.close();
                       
                }
                       if (flag.equals("9")){               
               
                         String uid = parameters;
                        PreparedStatement ps;
                        ps = conn.prepareStatement("DELETE FROM `user_details` WHERE `ucid` = ?");
            
                        ps.setString(1, uid);
                   	ps.executeUpdate();
			myObject.setMessage("User Deleted");
                        out.writeObject(myObject);
                        System.out.println("Message read:" + myObject.getMessage());	                        
                        ps.close();
                       
                }
                     
                if (flag.equals("10")){               
               
                
                        String[] values = parameters.split(",");
                        String firstname = values[0];
                        String lastname = values[1];
                        String dob=values[2];
                        String password =  "user123";
                        String email = values[3];
                        String ucd = values[4];
                        String address=values[5];
                        String phone=values[6];
                        String room=values[7];
                        String hall=values[8];
                        String role = "Resident"; 
                        Date date=Date.valueOf(dob);
                        
                        PreparedStatement ps;
                        ps = conn.prepareStatement("INSERT INTO `login_details`(`UCID`, `First_Name`, `Last_Name`, `Email`, `Password`, `Role`) VALUES (?,?,?,?,?,?)");
                        ps.setString(1, ucd);    
                        ps.setString(2, firstname);
                        ps.setString(3, lastname);
                        ps.setString(4, email);
			ps.setString(5, password);
                        ps.setString(6, role);
                        ps.executeUpdate();
			PreparedStatement ps1;
                        ps1 = conn.prepareStatement("INSERT INTO `user_details`(`ucid`, `first_name`, `last_name`, `DOB`, `email`, `address`, `phone`, `room`, `hall`) VALUES (?,?,?,?,?,?,?,?,?)");
                        ps1.setString(1, ucd);    
                        ps1.setString(2, firstname);
                        ps1.setString(3, lastname);
                        ps1.setDate(4, date);
			ps1.setString(5, email);
                        ps1.setString(6, address);
                        ps1.setString(7, phone);
                        ps1.setString(8, room);
                        ps1.setString(9, hall);
                        ps1.executeUpdate();
                        
                                myObject.setMessage("User Added");
                                out.writeObject(myObject);
                                System.out.println("Message read:" + myObject.getMessage());	
                        
                        ps.close();
                        ps.close();
                        
                }       
                      
                      
			
                        in.close();			
                        out.close();
                        incoming.close();			
			stmt.close();
			conn.close();
		
		}
		catch (SQLException E) {
			System.out.println("SQLException: " + E.getMessage());
			System.out.println("SQLState:     " + E.getSQLState());
			System.out.println("VendorError:  " + E.getErrorCode());
		}
					    
      }
   
   
      catch (Exception e) 
      {  System.out.println(e);
      } 
   }
   
   DataObject myObject = null;
   private Socket incoming;
      
}