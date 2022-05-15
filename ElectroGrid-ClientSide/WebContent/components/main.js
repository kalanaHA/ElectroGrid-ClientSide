$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateHospitalForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidbillIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "BillingAPI",
		type : t,
		data : $("#formBilling").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalSaveComplete(response.responseText, status);
		}
	});
}); 

function onHospitalSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidbillIDSave").val("");
	$("#formBilling")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidbillIDSave").val($(this).closest("tr").find('#hidbillIDUpdate').val());     
	$("#billAmount").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#billUnit").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#unitPrice").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#billCR").val($(this).closest("tr").find('td:eq(3)').text());
	$("#billDate").val($(this).closest("tr").find('td:eq(4)').text()); 
	

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "BillingApi",
		type : "DELETE",
		data : "billID=" + $(this).data("billID"),
		dataType : "text",
		complete : function(response, status)
		{
			onPaymentDeletedComplete(response.responseText, status);
		}
	});
});

function onHospitalDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}


//CLIENTMODEL
function validateHospitalForm() {  


	// billAmount 
	if ($("#billAmount").val().trim() == "")  {   
		return "Insert billAmount.";  
		
	} 
	
	 // billUnit  
	if ($("#billUnit").val().trim() == "")  {   
		return "Insert billUnit.";  
		
	} 
	 
	
	 
	 // unitPrice 
	if ($("#unitPrice").val().trim() == "")  {   
		return "Insert unitPrice.";  
		
	} 
	
	// billCR  
	if ($("#billCR").val().trim() == "")  {   
		return "Insert billCR.";  
		
	} 
	 
	 // billDate  
	if ($("#billDate").val().trim() == "")  {   
		return "Insert billDate.";  
		
	} 
	 
	
 
	 
	 return true; 
	 
}
