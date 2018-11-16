var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue.js!'
  },
  methods: {
    reverseMessage: function () {
      this.message = this.message.split('').reverse().join('')
    }
  }
})

function cambiar(e){
	document.getElementById("hola").innerHTML = "Hola Mundo!";
	document.getElementById('hola').style="color:blue"
	return true;
}

function validacaracter(e) {
	var key = window.Event ? e.which : e.keyCode;
	return (key !== 39 && key !== 44);
}

function soloNumeros(e) {
	var key = window.Event ? e.which : e.keyCode;
	return (key >= 48 && key <= 57);
}
