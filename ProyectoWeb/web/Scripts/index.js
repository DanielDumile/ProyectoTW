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
            
            this.log = "Completa los campos requeridos";
            event.preventDefault();
          }else{
            this.log = "Go";
          }   
      }
  }
  
});
/*----------------------------------------------------*/