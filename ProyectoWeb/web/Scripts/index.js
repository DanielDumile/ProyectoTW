new Vue({
  el: '#example-1',

  data: {
    show: false
  },
  
});

/* eslint-disable no-new */
var vm = new Vue({
  el: document.body,
  data: {
    a: 0,
    b: 1
  }
})

/*----------------------------------------------------*/
//Login
new Vue({
  el: "#Login",

  data:{
      user: "",
      pass: "",
  },
  
  methods: {
      
      sub: function(event){
          
          if(this.user == "" || this.pass == ""){
            
            alert('Completa los campos');
            event.preventDefault();
            
          } 
      }
  }
  
});
/*----------------------------------------------------*/

function Desarrolladores(){
    
    alert('Proyecto desarrollado por:\n\
            \n\
            Oscar David Ortega Prieto\n\
            \n\
            Daniel Adrian Gonzalez Nuñez\n\
            \n\
            Grupo 2CM7');
    
}

function Cerrar(){
    
   location.href ="/ProyectoWeb/index.html"; 

}

function TrueorFalse(){
    
   location.href ="/ProyectoWeb/Vistas/TrueFalse.html"; 
   
    
}

function Regresar(){
    
   location.href ="/ProyectoWeb/Vistas/Administrador.html"; 

}

