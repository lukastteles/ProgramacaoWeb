$(document).ready(function() {
	  //Clear default placeholder values on form submit
	  $('form').submit(function() {
          $(this).find("input[placeholder], textarea[placeholder]").each(function() {
              if ( this.value == $(this).attr("placeholder") ) {
                  this.value = "";
              }
          });
      });
  });