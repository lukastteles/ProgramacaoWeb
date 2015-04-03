

function initPage(){
	 $('#success').children().attr('style','display:none')
	 $('#alert').children().attr('style','display:none')
}


initPage();

$("#inputNumeroAbreviado").ready( function() {
		$("#inputNumeroAbreviado").attr("maxlength", 5);
		$("#inputNumeroAbreviado").bind("keydown", function(event) {
			 if(somenteNumero(event)){
			 if($("#inputNumeroAbreviado").val().length>0){
				return true;
			 }
				
			 }else{
			 return false;
			 }
			 
		});
});


$("#button").click(function(){
    $("#form").valid();
});



function errorSubmit(){
	$('#alert').children().attr('style','display:block')
}

function sucessSubmit(){
	$('#success').children().attr('style','display:block')
}

function submitAndValidate(){
initPage();
var number = $('#inputNumeroAbreviado').val();

	if(number.length>=2&&number.length<=5){
		//window.location.href = "rf01-telainicial-fa.html";
		sucessSubmit();
	}else{
errorSubmit();
}

}

function verifyBackSpaceAndArrow(code){
	if(code == 37 || code == 38 || code == 39 || code == 40 || code == 46 || code == 16 || code == 17|| code ==8 || (code>=97&&code<=105)) {
		return true;
	}
	
	return false;
}

function somenteNumero(event) {
	var code = event.which;
     var regex = new RegExp(/^([0-9])$/);
	 var key = String.fromCharCode(code);
	 
	 if(verifyBackSpaceAndArrow(code)){
		return true;
	 }else{
	 	 if(regex.test(key))
			 {
		
			 return true;
			}else{
				//letra
				return false;
			}
	 }	 
}
