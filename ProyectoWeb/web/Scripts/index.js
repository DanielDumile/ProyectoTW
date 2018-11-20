/*----------------------------------------------------*/
//Login
new Vue({
  el: "#Login",

  data:{
      user: "",
      pass: "",
      log: ""
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