package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Billing {

	//A common method to connect to the DB 
		private Connection connect() {
			Connection con = null;
			
			try {
				 Class.forName("com.mysql.jdbc.Driver");
				 //Provide the correct details: DBServer/DBName, username, password 
				 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "");

				//For testing          
				 System.out.print("Successfully connected");
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return con; 
		}
		
		public String readBilling() {  
			String output = "";  
			
			try {  
				Connection con = connect();  
				if (con == null)  {   
					return "Error while connecting to the database for reading.";  
				} 

				// Prepare the html table to be displayed   
				output = "<table border='1'><tr><th>Bill Amount</th>"
						+ "<th>Bill Unit</th><th>Unit Price</th>"
						+ "<th>Bill CR</th><th>Bill Date</th>"
						+ "<th>Update</th><th>Remove</th></tr>";


				  String query = "select * from billings";   
				  Statement stmt = con.createStatement();   
				  ResultSet rs = stmt.executeQuery(query); 

				  // iterate through the rows in the result set   
				  while (rs.next())   {  

					  	String billID = Integer.toString(rs.getInt("billID"));
						String billAmount = rs.getString("billAmount");
						String billUnit = rs.getString("billUnit");
						String unitPrice = rs.getString("unitPrice");
						String billCR = rs.getString("billCR");
						String billDate = rs.getString("billDate");
						
					  // Add into the html table    

					  output += "<tr><td><input id='hidbillIDUpdate' name='hidbillIDUpdate' type='hidden' value='" + billID + "'>" + billAmount + "</td>"; 

					  output += "<td>" + billUnit + "</td>";
						output += "<td>" + unitPrice + "</td>";
						output += "<td>" + billCR + "</td>";
						output += "<td>" + billDate + "</td>";
						
					  
					// buttons     
					  output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					  		+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-app_ID='"+ billID +"'>"+"</td></tr>";

					} 
				  
				  con.close(); 

				  // Complete the HTML tables
				  output += "</table>"; 
				}
				catch (Exception e) {  
					output = "Error while reading the billi.";  
					System.err.println(e.getMessage()); 
				}

				return output;
			}
		
		//Insert billing
		public String insertBilling(String billAmount, String billUnit, String unitPrice, String billCR, String billDate) {
			String output = "";

			try {
				Connection con = connect();  

				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement   
				String query = " insert into billings (`billID`,`billAmount`,`billUnit`,`unitPrice`,`billCR`,`billDate`)"+" values (?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values 
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, billAmount);
				preparedStmt.setString(3, billUnit);
				preparedStmt.setString(4, unitPrice);
				preparedStmt.setString(5,billCR);
				preparedStmt.setString(6,billDate);
				
				
				//execute the statement   
				preparedStmt.execute();   
				con.close(); 

				//Create JSON Object to show successful msg.
				String newBilling = readBilling();
				output = "{\"status\":\"success\", \"data\": \"" + newBilling + "\"}";
			}
			catch (Exception e) {  
				//Create JSON Object to show Error msg.
				output = "{\"status\":\"error\", \"data\": \"Error while Inserting bill.\"}";   
				System.err.println(e.getMessage());  
			} 

			 return output; 
		}
		
		//Update appointment
		public String updateBilling(String billID, String billAmount, String billUnit, String unitPrice, String billCR, String billDate )  {   
			String output = ""; 
		 
		  try   {   
			  Connection con = connect();
		 
			  if (con == null)    {
				  return "Error while connecting to the database for updating."; 
			  } 
		 
		   // create a prepared statement    
			   String query = "UPDATE billings SET billAmount=?,billUnit=?,unitPrice=?,billCR=?,billDate=?WHERE billID=?";
				 
		   PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		   // binding values    
		    preparedStmt.setString(1, billAmount);
			preparedStmt.setString(2,billUnit);
			preparedStmt.setString(3, unitPrice);
			preparedStmt.setString(4,billCR);
			preparedStmt.setString(5,billDate);
			preparedStmt.setInt(6, Integer.parseInt(billID));
		   
		 
		   // execute the statement    
		   preparedStmt.execute();    
		   con.close(); 
		 
		   //create JSON object to show successful msg
		   String newBilling = readBilling();
		   output = "{\"status\":\"success\", \"data\": \"" + newBilling + "\"}";
		   }   catch (Exception e)   {    
			   output = "{\"status\":\"error\", \"data\": \"Error while Updating bill Details.\"}";      
			   System.err.println(e.getMessage());   
		   } 
		 
		  return output;  
		  }
		
		public String deleteBilling(String billID) {  
			String output = ""; 
		 
		 try  {   
			 Connection con = connect();
		 
		  if (con == null)   {    
			  return "Error while connecting to the database for deleting.";   
		  } 
		 
		  // create a prepared statement   
		  String query = "DELETE FROM billings WHERE billID=?"; 
		 
		  PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		  // binding values   
		  preparedStmt.setInt(1, Integer.parseInt(billID));       
		  // execute the statement   
		  preparedStmt.execute();   
		  con.close(); 
		 
		  //create JSON Object
		  String newBilling = readBilling();
		  output = "{\"status\":\"success\", \"data\": \"" + newBilling + "\"}";
		  }  catch (Exception e)  {  
			  //Create JSON object 
			  output = "{\"status\":\"error\", \"data\": \"Error while Deleting Bill.\"}";
			  System.err.println(e.getMessage());  
			  
		 } 
		 
		 return output; 
		 }
}
