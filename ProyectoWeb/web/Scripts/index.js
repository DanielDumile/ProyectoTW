new Vue({
  el: '#Botones',

  data: {
    show: false,
    show2: false,
    show3: false,
    show4: false
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
    
   location.href ="/ProyectoWeb/Vistas/Opciones.html"; 

}

function TrueorFalse(){
    
   location.href ="/ProyectoWeb/Vistas/TrueFalse.html"; 
   
    
}

function Regresar(){
    
   location.href ="/ProyectoWeb/Vistas/Administrador.html"; 

}

function HotObject(){
    
   location.href ="/ProyectoWeb/Vistas/HotObject.html"; 
   
    
}

function Modificar(){
    
   location.href ="/ProyectoWeb/Modificar"; 
   
    
}

function Eliminar(){
    
   location.href = "/ProyectoWeb/Eliminar"; 
   
    
}

function Ver(){
    
   location.href = "/ProyectoWeb/Ver"; 
   
    
}

function Poner(id,i){
    
    var Sel = document.getElementById('ID');
    var elem = document.getElementById('Visible');
    elem.value = i;
    Sel.value=id;
    

    
}

function EliminarPregunta(){
    
    if(confirm("Esta seguro de eliminar la pregunta")){
    document.getElementById("f1").submit(); 
    }
}

function Index(){
    
     location.href = "/ProyectoWeb/index.html"; 
    
}

