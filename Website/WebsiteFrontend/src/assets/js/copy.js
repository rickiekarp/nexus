function copy() {
    /* Get the text field */
    var copyText = document.getElementById("myInput");
  
    /* Select the text field */
    copyText.select();
  
    /* Copy the text inside the text field */
    document.execCommand("copy");
  
    showSnackbar(copyText.value + " copied!");
  }

  function showSnackbar(textValue) {
    var elem = document.createElement('div');
    elem.id = "snackbar";
    elem.textContent = textValue
    document.body.appendChild(elem);

    // Get the snackbar DIV
    var x = document.getElementById("snackbar");

    // Add the "show" class to DIV
    x.className = "show";

    // After 3 seconds, remove the show class from DIV
    setTimeout(function(){ 
        x.className = x.className.replace("show", ""); 
        document.body.removeChild(elem);
    }, 3000);
  }