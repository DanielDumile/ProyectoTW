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
        show4: false,
        show5: false
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
    data: {
        user: "",
        pass: "",
    },
    methods: {
        sub: function (event) {

            if (this.user == "" || this.pass == "") {

                alert('Completa los campos');
                event.preventDefault();

            }
        }
    }

});



/*----------------------------------------------------*/
//Plantilla preguntas True False



/*----------------------------------------------------*/

function Desarrolladores() {

    alert('Proyecto desarrollado por:\n\
            \n\
            Oscar David Ortega Prieto\n\
            \n\
            Daniel Adrian Gonzalez Nuñez\n\
            \n\
            Grupo 2CM7');

}

function Cerrar() {

    location.href = "/ProyectoWeb/Vistas/Opciones.html";

}

function TrueorFalse() {

    location.href = "/ProyectoWeb/Vistas/TrueFalse.html";


}

function Regresar() {

    location.href = "/ProyectoWeb/Vistas/Administrador.html";

}

function RegresarExamen() {

    location.href = "/ProyectoWeb/Vistas/Examen.html";

}

function NewExamen() {

    location.href = "/ProyectoWeb/NewExamen";

}

function HotObject() {

    location.href = "/ProyectoWeb/Vistas/HotObject.html";


}

function Modificar() {

    location.href = "/ProyectoWeb/Modificar";


}

function ModificarExamen() {

    location.href = "/ProyectoWeb/ModE";

}

function Eliminar() {

    location.href = "/ProyectoWeb/Eliminar";


}

function RealizarExamen() {

    location.href = "/ProyectoWeb/MostrarResolver";
}

function VerExamen() {

    location.href = "/ProyectoWeb/MostrarExamenes";
}

function EliminarExamen() {

    location.href = "/ProyectoWeb/BorrarExamen";


}

function Ver() {

    location.href = "/ProyectoWeb/Ver";


}

function Poner(id, i, total) {

    var Value;
    for (var j = 0; j < total; j++) {
        Value = document.getElementById(j);
        document.getElementById(j).style.backgroundColor = '#a93226';
        document.getElementById(j).style.color = 'white';
        Value.value = 'Seleccionar';


    }
    Value = document.getElementById(i);
    document.getElementById(i).style.backgroundColor = 'white';
    document.getElementById(i).style.color = 'black';
    Value.value = 'Seleccionada';

    var Sel = document.getElementById('ID');

    Sel.value = id;



}

function EliminarPregunta() {

    var id = document.getElementById("ID").value;
    if (id === " ") {

        alert('No selecciono ninguna pregunta');
    } else {

        if (confirm("Esta seguro de eliminar la pregunta")) {
            alert('Se elimino la pregunta seleccionada');
            document.getElementById("f1").submit();
        }

    }


}


function ModificarPregunta() {
    var id = document.getElementById("ID").value;
    if (id === " ") {

        alert('No selecciono ninguna pregunta');
    } else {
        document.getElementById("f1").submit();
    }
}

function VerPregunta() {
    var id = document.getElementById("ID").value;
    if (id === " ") {

        alert('No selecciono ninguna pregunta');
    } else {
        document.getElementById("f1").submit();
    }
}

function Index() {

    location.href = "/ProyectoWeb/index.html";

}

function Examen() {

    location.href = "/ProyectoWeb/Vistas/Examen.html";

}

function Calificar() {

    var resultado;

    var porNombre = document.getElementsByName("Valor");
    var Respuesta = document.getElementById("Resp").value;
    var Feedback = document.getElementById("feed");
    var correcta =document.getElementById("correcta").value;
    var incorrecta = document.getElementById("incorrecta").value;
    var intentar = document.getElementById("intentar").value;

    for (var i = 0; i < porNombre.length; i++) {
        if (porNombre[i].checked) {
            resultado = porNombre[i].value;
        }
    }
    var ans = document.getElementById("answer");
    if (resultado === Respuesta) {
        ans.value = 'Correcta';
        alert('Respuesta Correcta');
        Feedback.value = correcta;
    } else {
        ans.value = 'Incorrecta';
        alert('Respuesta Incorrecta');
        Feedback.value = incorrecta;
    }
}


function Siguiente() {

    var index = document.getElementById("indice");
    var aux = parseInt(document.getElementById("indice").value);
    //aux = aux + 1; 
    index.value = aux;

    var dir = document.getElementById("direccion");
    dir.value = 'Siguiente';

    document.getElementById("formi").submit();
}

function Evaluacion() {

    var index = document.getElementById("indice");
    var aux = parseInt(document.getElementById("indice").value);
    //aux = aux + 1; 
    index.value = aux;

    var dir = document.getElementById("direccion");
    dir.value = 'Evaluacion';

    document.getElementById("formi").submit();



}

function Anterior() {

    var index = document.getElementById("indice");
    var aux = parseInt(document.getElementById("indice").value);
    //aux = aux - 1;
    index.value = aux;

    var dir = document.getElementById("direccion");
    dir.value = 'Anterior';

    document.getElementById("formi").submit();



}

function Inicio() {

    var index = document.getElementById("indice");
    var aux = parseInt(document.getElementById("indice").value);
    //aux = aux - 1;
    index.value = aux;

    var dir = document.getElementById("direccion");
    dir.value = 'Inicio';

    document.getElementById("formi").submit();



}

function Ultima() {

    var index = document.getElementById("indice");
    var aux = parseInt(document.getElementById("indice").value);
    //aux = aux - 1;
    index.value = aux;

    var dir = document.getElementById("direccion");
    dir.value = 'Ultima';

    document.getElementById("formi").submit();



}

function CalificarHotObject() {

    var Marcadas = document.getElementsByName("Respuesta");
    var Correcta = document.getElementById("Resp").value;
    var Array = Correcta.split(",");
    var ans = document.getElementById("answer");
    var Feedback = document.getElementById("feed");
    var correcta =document.getElementById("correcta").value;
    var incorrecta = document.getElementById("incorrecta").value;
    var intentar = document.getElementById("intentar").value;

    var Contador = 0;
    var Check = 0;

    for (var i = 0; i < Marcadas.length; i++) {

        for (var j = 0; j < Array.length; j++) {

            if (Marcadas[i].checked) {
                Check++;

                if (Marcadas[i].value === Array[j]) {

                    Contador++;

                }

            } else {

                break;
            }
        }

    }
    var Division = Check / Array.length;

    if (Contador === Array.length) {

        if (Division === Contador) {
            ans.value = 'Correcta';
            alert('Respuesta Correcta');
            Feedback.value = correcta;
        } else {
            ans.value = 'Incorrecta';
            alert('Respuesta Incorrecta');
            Feedback.value = incorrecta;
        }

    } else {
        ans.value = 'Incorrecta';
        alert('Respuesta Incorrecta');
        Feedback.value = incorrecta;
    }

}


function Calificar2() {

    var resultado;

    var porNombre = document.getElementsByName("Valor");
    var Respuesta = document.getElementById("Resp").value;
    var Intentos = parseInt(document.getElementById("try").value);
    var Feedback = document.getElementById("feed");
    var correcta =document.getElementById("correcta").value;
    var incorrecta = document.getElementById("incorrecta").value;
    var intentar = document.getElementById("intentar").value;
    
    var evaluar = document.getElementById("evaluar").value;
    
    Feedback.value = evaluar;
    
    if(Intentos === 0){
        
        alert('Se acabaron los intentos');
        Feedback.value = intentar;
        
       
    }else{

        for (var i = 0; i < porNombre.length; i++) {

            if (porNombre[i].checked) {
                resultado = porNombre[i].value;
            }
        }
        var ans = document.getElementById("answer");
        if (resultado === Respuesta) {
            alert('Correcta');
            
          ans.value = 'Correcta';
          Feedback.value = correcta;
          
        } else {
            ans.value = 'Incorrecta';
            alert('Incorrecta');
            
            Feedback.value=incorrecta;
            
            document.getElementById("try").value--;

        }
    }
}

function Calificar3() {

    var Marcadas = document.getElementsByName("Respuesta");
    var Correcta = document.getElementById("Resp").value;
    var Array = Correcta.split(",");
    var ans = document.getElementById("answer");
    
    var Intentos = parseInt(document.getElementById("try").value);
    var Feedback = document.getElementById("feed");
    var correcta =document.getElementById("correcta").value;
    var incorrecta = document.getElementById("incorrecta").value;
    var intentar = document.getElementById("intentar").value;
    
    var evaluar = document.getElementById("evaluar").value;
    
    Feedback.value = evaluar;

     if(Intentos === 0){
        
        alert('Se acabaron los intentos');
        Feedback.value = intentar;
        
       
    }else{

    var Contador = 0;
    var Check = 0;

    for (var i = 0; i < Marcadas.length; i++) {

        for (var j = 0; j < Array.length; j++) {

            if (Marcadas[i].checked) {
                Check++;

                if (Marcadas[i].value === Array[j]) {

                    Contador++;

                }

            } else {

                break;
            }
        }

    }
    var Division = Check / Array.length;

    if (Contador === Array.length) {

        if (Division === Contador) {
            ans.value = 'Correcta';
            alert('Respuesta Correcta');
            Feedback.value = correcta;
        } else {
            ans.value = 'Incorrecta';
            alert('Respuesta Incorrecta');
            Feedback.value = incorrecta;
            document.getElementById("try").value--;

        }

    } else {
        ans.value = 'Incorrecta';
        alert('Respuesta Incorrecta');
        Feedback.value = incorrecta;
        document.getElementById("try").value--;
    }
    }
}


