function cambiar(e){
	document.getElementById("hola").innerHTML = "Hola Mundo!";
	return false;
}

function validacaracter(e) {
	var key = window.Event ? e.which : e.keyCode;
	return (key !== 39 && key !== 44);
}

function soloNumeros(e) {
	var key = window.Event ? e.which : e.keyCode;
	return (key >= 48 && key <= 57);
}