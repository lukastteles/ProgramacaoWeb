function openAjaxCall(userName){
	alert("Chamando função AJAX delay");
	$.ajax({  
        type : 'GET',
		cache: false,
		url : "homeDeleteUserAjax.html",  
        data : { "userName": userName, "_": $.now()},
		success : function(response) {  
			$("#contentUsers").html(response);
		},   
		error: function(xhr,err){
			alert(xhr.responseText);
		} 
    });
}

function openAjaxCallWOTimeout(userName){
	alert("Chamando funcao sem AJAX com delay");
	$.ajax({  
        type : 'GET',
		cache: false,
		url : "homeDeleteUser.html",  
        data : { "userName": userName, "_": $.now()},
		success : function(response) {  
			document.open();
			document.write(response);
			document.close();
		},   
		error: function(xhr,err){
			alert(xhr.responseText);
		} 
    });
}