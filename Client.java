import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class Client{
   
     public Client(){
        initComponents("");
    }
    public Client(String usernamepassword){
        initComponents(usernamepassword);
    }
    
     private void initComponents(String up) {
         try{
             
         DataObject myObject = new DataObject();

			myObject.setMessage(up);

			System.out.println("loginStream : " + myObject.getMessage()); 
			Socket socketToServer = new Socket("afsconnect1.njit.edu", 7044);

			ObjectOutputStream myOutputStream =
				new ObjectOutputStream(socketToServer.getOutputStream());

			ObjectInputStream myInputStream =
				new ObjectInputStream(socketToServer.getInputStream());

			myOutputStream.writeObject(myObject);

			myObject = (DataObject)myInputStream.readObject();

                        if (myObject.getMessage().contains("Exists")){
                           JOptionPane.showMessageDialog(null, "Login Success");
                           String[] str = myObject.getMessage().split(",");
                            
                        if (str[1].equals("Resident")){   
                           homeFram obj1 = new homeFram();
                           obj1.setVisible(true);
                           //String[] str = myObject.getMessage().split(":");
                           obj1.jLabel3.setText(str[0]);
                        }
                        else if (str[1].equals("RC")){
                           rcHomeFram obj2 = new rcHomeFram();
                           obj2.setVisible(true);
                           //String[] str1 = myObject.getMessage().split(":");
                           obj2.jLabel4.setText(str[0]);
                        }   
                           
                           System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        else if (myObject.getMessage().equals("Invalid User")){
                            JOptionPane.showMessageDialog(null, "Invalid Login Details");
                            System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        else if (myObject.getMessage().equals("User Registered")){
                            JOptionPane.showMessageDialog(null, "New User Registered Successfully");
                            System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        else if (myObject.getMessage().contains("viewPro")){
                           profileFram obj2 = new profileFram();
                           obj2.setVisible(true);
                            String[] profParam = myObject.getMessage().split(","); 
                           // profileFram obj2 = new profileFram();
                            obj2.jTextField1.setText(profParam[1]);
                            obj2.jTextField2.setText(profParam[2]);
                            obj2.jTextField3.setText(profParam[0]);
                            obj2.jTextField4.setText(profParam[5]);
                            obj2.jTextField5.setText(profParam[6]);
                            obj2.jTextField6.setText(profParam[7]);
                            obj2.jComboBox1.setName(profParam[8]);
                            obj2.jFormattedTextField1.setText(profParam[3]);
                            obj2.jFormattedTextField2.setText(profParam[4]);     
                            obj2.jLabel4.setText(profParam[4]);
                            System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        else if (myObject.getMessage().equals("User Details Updated")){
                            JOptionPane.showMessageDialog(null, "User Details  Updated Successfully");
                            System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        else if (myObject.getMessage().contains("searchPro")){
                           searchFram obj2 = new searchFram();
                           obj2.setVisible(true);
                            String[] profParam = myObject.getMessage().split(","); 
                           // profileFram obj2 = new profileFram();
                            obj2.jTextField1.setText(profParam[1]);
                            obj2.jTextField2.setText(profParam[2]);
                            obj2.jTextField3.setText(profParam[0]);
                            obj2.jTextField4.setText(profParam[5]);
                            obj2.jTextField5.setText(profParam[6]);
                            obj2.jTextField6.setText(profParam[7]);
                            obj2.jComboBox1.setName(profParam[8]);
                            obj2.jFormattedTextField1.setText(profParam[3]);
                            obj2.jFormattedTextField2.setText(profParam[4]);     
                            obj2.jLabel4.setText(profParam[9]);
                            System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        else if (myObject.getMessage().contains("viewRcPro")){
                           rcProfileFram obj2 = new rcProfileFram();
                           obj2.setVisible(true);
                            String[] profParam = myObject.getMessage().split(","); 
                           // profileFram obj2 = new profileFram();
                            obj2.jTextField1.setText(profParam[1]);
                            obj2.jTextField2.setText(profParam[2]);
                            obj2.jTextField3.setText(profParam[0]);
                            obj2.jTextField4.setText(profParam[5]);
                            obj2.jTextField5.setText(profParam[6]);
                            obj2.jTextField6.setText(profParam[7]);
                            obj2.jComboBox1.setName(profParam[8]);
                            obj2.jFormattedTextField1.setText(profParam[3]);
                            obj2.jFormattedTextField2.setText(profParam[4]);     
                            obj2.jLabel4.setText(profParam[4]);
                            System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        else if (myObject.getMessage().contains("searchRcPro")){
                           rcViewDeleteFram obj2 = new rcViewDeleteFram();
                           obj2.setVisible(true);
                            String[] profParam = myObject.getMessage().split(","); 
                           // profileFram obj2 = new profileFram();
                            obj2.jTextField1.setText(profParam[1]);
                            obj2.jTextField2.setText(profParam[2]);
                            obj2.jTextField3.setText(profParam[0]);
                            obj2.jTextField4.setText(profParam[5]);
                            obj2.jTextField5.setText(profParam[6]);
                            obj2.jTextField6.setText(profParam[7]);
                            obj2.jComboBox1.setName(profParam[8]);
                            obj2.jFormattedTextField1.setText(profParam[3]);
                            obj2.jFormattedTextField2.setText(profParam[4]);     
                            obj2.jLabel4.setText(profParam[9]);
                            System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        else if (myObject.getMessage().equals("User Deleted")){
                            JOptionPane.showMessageDialog(null, "User Deleted Successfully");
                            System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        else if (myObject.getMessage().equals("User Added")){
                            JOptionPane.showMessageDialog(null, "User Added Successfully");
                            System.out.println("Messaged received : " + myObject.getMessage());
                        }
                        
			myOutputStream.close();			
			myInputStream.close();
            		socketToServer.close();
	
		}
		catch(Exception e){
			System.out.println(e);
        		}
     }
        public static void main(String[] arg){		
                        
                        loginFram obj1 = new loginFram();
                        obj1.setVisible(true);
                        
                        
        }
}
