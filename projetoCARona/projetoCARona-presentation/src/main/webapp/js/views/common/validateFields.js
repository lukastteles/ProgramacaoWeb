function checkIsANumber(evt) {
	var regExpr = new RegExp("[0-9:]");
	var evt = evt || window.event;
    if (!regExpr.test(""+String.fromCharCode(evt.keyCode))) {
    	 evt.returnValue = false;
         if (evt.preventDefault) evt.preventDefault();
    }
}

function checkHourInput(element){
    var regExpr = new RegExp("[0-2][0-9]:[0-5][0-9]");
    if (!regExpr.test($(element).val())) {
         alert("O horário deve estar no formato: HH:mm");
		 element.focus();
    }
}