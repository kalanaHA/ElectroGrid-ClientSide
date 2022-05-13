package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Billing;;

/**
 * Servlet implementation class DoctorAPI
 */
@WebServlet("/BillingAPI")
public class BillingApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	Billing appObj = new Billing();
	
    public BillingApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String result = appObj.insertBilling(request.getParameter("billAmount"), 
				request.getParameter("billUnit"), 
				request.getParameter("unitPrice"), 
				request.getParameter("billCR"),
				request.getParameter("billdate"));
		
		response.getWriter().write(result);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	
	private Map<String, String> getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();  
		try  {   
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");   
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";   
			scanner.close(); 
		 
		  String[] params = queryString.split("&");   
		  for (String param : params)   {
			  String[] p = param.split("=");    
			  map.put(p[0], p[1]);   
		  }  
		  
		}catch (Exception e)  {  
			
		} 
		return map;
	}
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		Map<String, String> param = getParasMap(request);
		
		String result = appObj.updateBilling(param.get("hidbillIDSave").toString(),
				param.get("billAmount").toString().toString().replace("+", " "),     
				param.get("billUnit").toString().toString().replace("+", " "),        
				param.get("unitPrice").toString().toString().replace("+", " "),       
				param.get("billCR").toString().toString().replace("+", " "),
				param.get("billDate").toString().toString().replace("+", " ")
		 	
 				);
		
		response.getWriter().write(result);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		Map<String, String> param = getParasMap(request);
		
		String result = appObj.deleteBilling(param.get("billID").toString());
		
		response.getWriter().write(result);
	}

}

