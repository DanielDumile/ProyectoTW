/*----------------------------------------------------*/
//Login
new Vue({
  el: "#men",

  data:{
      login: "",
      senha: "",
      log: ""
  },
  
  methods: {
      
      sub: function(event){
          
          if(this.login == "" || this.senha == ""){
            
            this.log = "Preencha o campo para login.";
            event.preventDefault();
          }else{
            this.log = "Go";
          }   
      }
  }
  
});
/*----------------------------------------------------*/