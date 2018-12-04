Vue.component('Encabezado', {
  // declara las propiedades
  props: ['Pregunta'],
  // como 'data', las propiedades pueden ser utilizadas dentro de las
  // plantillas y está disponibles en la vm como this.message
  template: '<p class="Subtitulos3"> {{Pregunta}} </p>'
        
});

new Vue({
 el: '#Top' 
});



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
            
          } 
      }
  }
  
});


 
/*----------------------------------------------------*/
//Plantilla preguntas True False



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

function Poner(id,i,total){
    
    var Value;
    for(var j = 0; j < total; j++){
        Value = document.getElementById(j);
        document.getElementById(j).style.backgroundColor = '#a93226';
        document.getElementById(j).style.color = 'white';
        Value.value='Seleccionar';
        
        
    }
    Value = document.getElementById(i);
    document.getElementById(i).style.backgroundColor = 'white';
    document.getElementById(i).style.color = 'black';
    Value.value='Seleccionada';
    
    var Sel = document.getElementById('ID');
  
    Sel.value=id;
    

    
}

function EliminarPregunta(){
    
    var id = document.getElementById("ID").value;
    if(id ===" "){
        
        alert('No selecciono ninguna pregunta');
    }else{
        
         if(confirm("Esta seguro de eliminar la pregunta")){
            document.getElementById("f1").submit(); 
        }
       
    }
    
   
}


function ModificarPregunta(){
     var id = document.getElementById("ID").value;
   if(id === " "){
        
        alert('No selecciono ninguna pregunta');
    }else{
    document.getElementById("f1").submit(); 
    }
}

function VerPregunta(){
     var id = document.getElementById("ID").value;
   if(id === " "){
        
        alert('No selecciono ninguna pregunta');
    }else{
    document.getElementById("f1").submit(); 
    }
}

function Index(){
    
     location.href = "/ProyectoWeb/index.html"; 
    
}

