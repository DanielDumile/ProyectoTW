new Vue({
  el: '#example-1',

  data: {
    show: false
  },
  
});






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
            
          }else{
            alert('Bienvenido');
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

