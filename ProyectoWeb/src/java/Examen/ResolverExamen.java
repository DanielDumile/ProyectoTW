/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author USER
 */
public class ResolverExamen extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String ruta = context.getRealPath("/") + "XML/Examen.xml";
            String ruta2 = context.getRealPath("/") + "XML/PreguntaTF.xml";

            //Obtenemo nuestro arreglo con los valores para cada pregunta
            HttpSession sesion = request.getSession();
            sesion.setAttribute("rutaXML", ruta);
            ArrayList respuestasSesion = new ArrayList<>();
            respuestasSesion = (ArrayList) sesion.getAttribute("respuestas");
            int indice = Integer.parseInt(request.getParameter("indice"));
            sesion.setAttribute("respuestas", respuestasSesion);

            String idExamen = request.getParameter("ID");
            int cant_preguntas = Integer.parseInt(request.getParameter("cantidad"));

            //Variables necesarias
            String id_pregunta = "";
            //String idExamen = "1";
            //CONSULTAMOS AL EXAMEN Y A LAS PREGUNTAS QUE TIENE DENTRO
            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(ruta);
            try {//Se crea el documento a traves del archivo  
                Document document = (Document) builder.build(xmlFile);
                //Se obtiene la raiz 
                Element rootNode = document.getRootElement();
                //Se obtiene la lista de hijos de la raiz 'usuarios'
                List list = rootNode.getChildren("examen");
                for (int i = 0; i < list.size(); i++) {
                    //Se obtiene el elemento 'user1'
                    Element campo = (Element) list.get(i);
                    String id = campo.getAttributeValue("id");
                    if (idExamen.equals(id)) {
                        List lista = campo.getChildren("idp");
                        for (int j = 0; j < lista.size(); j++) {
                            if (indice == j) {
                                Element aux = ((Element) lista.get(j));
                                String id_aux = aux.getTextTrim();//Conseguimos el id de la pregunta
                                id_pregunta = id_aux;
                                break;
                            }
                        }
                        break;
                    }
                }
            } catch (JDOMException io) {
                System.out.println(io.getMessage());
            }

            if (indice >= cant_preguntas) {
                //Consulta de preguntas
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet InicioExamen</title>");
                out.println("<script> function cargar(){ document.getElementById(\"formi\").submit();}</script>");
                out.println("</head>");
                out.println("<body onLoad='cargar();'><form method='post' name='formi' id='formi' action='Evaluacion'>");
                out.println("<input type='text' name='ID' value='" + idExamen + "' hidden>");
                out.println("<input type='text' name='cantidad' value='" + String.valueOf(cant_preguntas) + "' hidden>");
                out.println("<input type='text' name='indice' value='" + String.valueOf(indice) + "' hidden>");
                out.println("</form></body>");
                out.println("</html>");
            } else {
                //SACAMOS A TODAS LAS PREGUNTAS PERO SOLO PONEMOS A LA QUE NOS INTERESA
                SAXBuilder builder2 = new SAXBuilder();
                File xmlFile2 = new File(ruta2);
                try {//Se crea el documento a traves del archivo  
                    Document doc = (Document) builder.build(xmlFile2);
                    //Se obtiene la raiz 
                    Element rootNode = doc.getRootElement();
                    //Se obtiene la lista de hijos de la raiz 'usuarios'
                    List list = rootNode.getChildren("pregunta");
                    //out.println("El id escogido es "+ id_pregunta);
                    //System.out.println("Escogido en consola "+id_pregunta);
                    out.println("<html>\n"
                            + "    <head>\n"
                            + "        <title>Pregunta</title>\n"
                            + "        <meta charset=\"UTF-8\">\n"
                            + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "        <link rel=\"stylesheet\" href=\"Styles/Style.css\" type=\"text/css\">\n"
                            + "        <script src=\"Frameworks/vue.js\"></script>"
                            + "<script src=\"Scripts/index.js\"></script>\n"
                            + "        \n"
                            + "    </head>\n"
                            + "    <body>"
                            + "<form id='formi' action='Intermedio' method='post' >");
                    for (int i = 0; i < list.size(); i++) {
                        Element campo = (Element) list.get(i);
                        String id = campo.getAttributeValue("id");
                        String tipo = campo.getChildTextTrim("tipo");
                        String texto = campo.getChildTextTrim("texto");
                        String respuesta = campo.getChildTextTrim("respuesta");
                        System.out.println(id);

                        String multimedia = campo.getChildTextTrim("multimedia");
                        String inicial = campo.getChildTextTrim("inicial");
                        String intentos = campo.getChildTextTrim("intentos");
                        String evaluar = campo.getChildTextTrim("evaluar");
                        String correcta = campo.getChildTextTrim("correcta");
                        String incorrecta = campo.getChildTextTrim("incorrecta");
                        String intentar = campo.getChildTextTrim("intentar");

                        if (id_pregunta.equals(id)) {
                            if (tipo.equals("TrueFalse")) {

                                out.println("<div id=\"Preguntas\">\n"
                                        + "            \n"
                                        + "            <div id=\"Contenido\">\n"
                                        + "                \n"
                                        + "                <div id=\"Top\">\n"
                                        + "                    \n"
                                        + "                    <br />\n"
                                        + "                    <Encabezado Pregunta=\"" + texto + "\" ></Encabezado> \n"
                                        + "                    <input type=\"text\" value=\"" + idExamen + "\" name='ID' hidden />\n"
                                        + "     <input type='text' id='indice' name='indice' value='" + String.valueOf(indice) + "' hidden>"
                                        + "     <input type='text' id='cantidad' name='cantidad' value='" + String.valueOf(cant_preguntas) + "' hidden>"
                                        + "     <input type='text' id='answer' name='answer' value='X' hidden>"
                                        + "     <input type='text' id='direccion' name='direccion' value='X' hidden>"
                                        + "                </div>\n"
                                        + "                \n"
                                        + "                <br />\n"
                                        + "                <br />\n"
                                        + "                \n"
                                        + "                <p class=\"Subtitulos3\" style=\"color: black\"><input type=\"radio\" value=\"Verdadero\" name=\"Valor\"/> Verdadero</p>\n"
                                        + "                <p class=\"Subtitulos3\" style=\"color: black\"><input type=\"radio\" value=\"Falso\" name=\"Valor\"/> Falso</p>\n"
                                        + "                 \n"
                                        + "                <img class=\"Ad4\" src=\"" + multimedia + "\" alt=\"Examen\" />\n"
                                        + "                \n"
                                        + "                \n"
                                        + "                 <div id=\"Bottom\" style=\"position: relative; left: 0px; top:190px;\" >\n"
                                        + "                     <br />\n"
                                        + "                     \n"
                                        + "                     <input type=\"button\" class=\"button2\"style=\"position: relative; left: 120px; top:-70px;\" onclick=\"Calificar()\" id=\"Calificar\" value=\"Calificar\" />"
                                        + "<input type=\"button\" class=\"button2\"style=\"position: relative; left: 120px; top:-70px;\" onclick=\"Siguiente()\" id=\"Siguiente\" value=\"Siguiente\" />\n"
                                        + "                </div>\n"
                                        + "            </div>\n"
                                        + "            \n");

                                out.println("<input type='text' value='" + inicial + "' name='inicial' id='inicial' hidden />");
                                out.println("<input type='text' value='" + evaluar + "' name='evaluar' id='evaluar' hidden />");
                                out.println("<input type='text' value='" + correcta + "' name='correcta' id='correcta' hidden />");
                                out.println("<input type='text' value='" + incorrecta + "' name='incorrecta' id='incorrecta' hidden />");
                                out.println("<input type='text' value='" + intentar + "' name='intentar' id='intentar' hidden />");
                                out.println("<input type='text' value='No lo ocupo' name='answer' id='answer' hidden />");

                                out.println("<input type='text' value='" + respuesta + "' name='Resp' id='Resp' hidden />"
                                        + "            <div id=\"Left\" style=\"position: relative; left: 100px; top:-100px;\">\n"
                                        + "                <p>Lea cuidadosamente la pregunta\n"
                                        + "                y seleccione si es verdadera o falsa</p>\n"
                                        + "                <p>Seleccione <b>Calificar</b> para obtener puntaje</p>\n"
                                        + "                <p>Seleccione <b>Siguiente</b> para pasar a la otra pregunta</p>\n"
                                        + "            </div>\n"
                                        + "            \n"
                                        + "            <div id=\"Left\" style=\"position: relative; left: -205px; top:130px;\">\n"
                                        + "                <p><b>FeedBack</b></p>\n"
                                        + "                <p><textarea id='feed' readonly style='width: 290px; height: 120px; background-color: white; text­align: center; border:none;'>" + inicial + "</textarea></p>\n"
                                        + "            </div>\n"
                                        + "        </div>");

                                /*
                                out.println("<div id=\"Preguntas\">\n"
                                        + "            \n"
                                        + "            <div id=\"Contenido\">\n"
                                        + "                \n"
                                        + "                <div id=\"Top\">\n"
                                        + "                    \n"
                                        + "                    <br />\n"
                                        + "                    <Encabezado Pregunta=\"" + texto + "\" ></Encabezado> \n"
                                        + "                    <input type=\"text\" value=\"" + idExamen + "\" name='ID' hidden />\n"
                                        + "     <input type='text' id='indice' name='indice' value='" + String.valueOf(indice) + "' hidden>"
                                        + "     <input type='text' id='cantidad' name='cantidad' value='" + String.valueOf(cant_preguntas) + "' hidden>"
                                        + "     <input type='text' id='answer' name='answer' value='X' hidden>"
                                        + "     <input type='text' id='direccion' name='direccion' value='X' hidden>"
                                        + "                </div>\n"
                                        + "                \n"
                                        + "                <br />\n"
                                        + "                <br />\n"
                                        + "                \n"
                                        + "                <p class=\"Subtitulos3\" style=\"color: black\"><input type=\"radio\" value=\"Verdadero\" name=\"Valor\"/> Verdadero</p>\n"
                                        + "                <p class=\"Subtitulos3\" style=\"color: black\"><input type=\"radio\" value=\"Falso\" name=\"Valor\"/> Falso</p>\n"
                                        + "                 \n"
                                        + "                <img class=\"Ad4\" src=\"Imagenes/VF.png\" alt=\"Examen\" /> <input type=\"text\" id=\"Resp\" value=\"" + respuesta + "\" hidden/>\n"
                                        + "                \n"
                                        + "                 <div id=\"Bottom\">\n"
                                        + "                     <br />\n"
                                        + "                     <input type=\"button\" class=\"button2\" onclick='Siguiente()' value=\"Siguiente\" />\n"
                                        + "                    \n"
                                        + "                     <input type=\"button\" class=\"button2\" onclick=\"Calificar()\" value=\"Calificar\" />\n"
                                        + "                </div>\n"
                                        + "            </div>\n"
                                        + "            \n"
                                        + "            <div id=\"Left\">\n"
                                        + "                <p>Lea cuidadosamente la pregunta\n"
                                        + "                y seleccione si es verdadera o falsa</p>\n"
                                        + "                <p>Seleccione <b>Calificar</b> para obtener puntaje</p>\n"
                                        + "                <p>Seleccione <b>Siguiente</b> para pasar a la otra pregunta</p>\n"
                                        + "            </div>\n"
                                        + "        </div>");*/
                            } else {
                                List lista = campo.getChildren("opcion");
                                out.println("<style>\n"
                                        + "            \n"
                                        + "            .checkeable input {\n"
                                        + "                display: none;\n"
                                        + "                float: left;\n"
                                        + "            }\n"
                                        + "            \n"
                                        + "            .checkeable img {\n"
                                        + "                width: 100px;\n"
                                        + "                height: 100px;\n"
                                        + "                border: 5px solid transparent;\n"
                                        + "                position: relative;\n"
                                        + "                left: 35px;\n"
                                        + "                bottom: 20px;\n"
                                        + "      \n"
                                        + "            }\n"
                                        + "            \n"
                                        + "            .checkeable input {\n"
                                        + "                display: none;\n"
                                        + "                float: left;\n"
                                        + "            }\n"
                                        + "            \n"
                                        + "            .checkeable input:checked  + img {\n"
                                        + "              border-color: grey;\n"
                                        + "              opacity: 0.5;\n"
                                        + "             \n"
                                        + "            }\n"
                                        + "            \n"
                                        + "        </style>");
                                out.println("<div id=\"Preguntas\">\n"
                                        + "            \n"
                                        + "            <div id=\"Contenido\" style=\"border-bottom: 6px solid #f0b60f;\">\n"
                                        + "                \n"
                                        + "                <div id=\"Top\" style=\"background-color:#f0b60f\">\n"
                                        + "                    \n"
                                        + "                    <br />\n"
                                        + "                    <Encabezado Pregunta=\"" + texto + "\" ></Encabezado> \n"
                                        + "                    <input type=\"text\" value=\"" + idExamen + "\" name='ID' hidden />\n"
                                        + "     <input type='text' id='indice' name='indice' value='" + String.valueOf(indice) + "' hidden>"
                                        + "     <input type='text' id='cantidad' name='cantidad' value='" + String.valueOf(cant_preguntas) + "' hidden>"
                                        + "     <input type='text' id='answer' name='answer' value='X' hidden>"
                                        + "     <input type='text' id='direccion' name='direccion' value='X' hidden>"
                                        + "     <input type=\"text\" id=\"Resp\" value=\"" + respuesta + "\" hidden/>"
                                        + "                </div>\n"
                                        + "                \n"
                                        + "                <br />\n"
                                        + "                <br />\n"
                                        + "                \n"
                                        + "                <p>\n"
                                        + "                    <label class=\"checkeable\">\n"
                                        + "                        <input type=\"checkbox\" name=\"Respuesta\" value=\"" + 1 + "\"/>\n"
                                        + "                        <img src=\"" + ((Element) lista.get(0)).getTextTrim() + "\" />\n"
                                        + "                    </label>\n"
                                        + "\n"
                                        + "                    <label class=\"checkeable\">\n"
                                        + "                      <input type=\"checkbox\" name=\"Respuesta\" value=\"" + 2 + "\"/>\n"
                                        + "                      <img src=\"" + ((Element) lista.get(1)).getTextTrim() + "\" />\n"
                                        + "                    </label>\n"
                                        + "\n"
                                        + "                    <label class=\"checkeable\">\n"
                                        + "                        <input type=\"checkbox\" name=\"Respuesta\" value=\"" + 3 + "\"/>\n"
                                        + "                        <img src=\"" + ((Element) lista.get(2)).getTextTrim() + "\" />\n"
                                        + "                    </label>\n"
                                        + "\n"
                                        + "                    <label class=\"checkeable\">\n"
                                        + "                      <input type=\"checkbox\" name=\"Respuesta\" value=\"" + 4 + "\"/>\n"
                                        + "                      <img src=\"" + ((Element) lista.get(3)).getTextTrim() + "\" />\n"
                                        + "                    </label>\n"
                                        + "                </p>\n"
                                        + "                \n"
                                        + "                <p>\n"
                                        + "                    <label class=\"checkeable\">\n"
                                        + "                        <input type=\"checkbox\" name=\"Respuesta\" value=\"" + 5 + "\"/>\n"
                                        + "                        <img src=\"" + ((Element) lista.get(4)).getTextTrim() + "\" />\n"
                                        + "                    </label>\n"
                                        + "\n"
                                        + "                    <label class=\"checkeable\">\n"
                                        + "                      <input type=\"checkbox\" name=\"Respuesta\" value=\"" + 6 + "\"/>\n"
                                        + "                      <img src=\"" + ((Element) lista.get(5)).getTextTrim() + "\" />\n"
                                        + "                    </label>\n"
                                        + "                    <label class=\"checkeable\">\n"
                                        + "                        <input type=\"checkbox\" name=\"Respuesta\" value=\"" + 7 + "\"/>\n"
                                        + "                        <img src=\"" + ((Element) lista.get(6)).getTextTrim() + "\" />\n"
                                        + "                    </label>\n"
                                        + "\n"
                                        + "                    <label class=\"checkeable\">\n"
                                        + "                      <input type=\"checkbox\" name=\"Respuesta\" value=\"" + 8 + "\"/>\n"
                                        + "                      <img src=\"" + ((Element) lista.get(7)).getTextTrim() + "\" />\n"
                                        + "                    </label>\n"
                                        + "                    \n"
                                        + "                    <img class=\"Ad6\" src=\"Imagenes/HOT.png\" alt=\"Examen\" />\n"
                                        + "                </p>\n"
                                        + "<div id=\"Bottom2\">\n"
                                        + "                     <br />\n"
                                        + "                     <input type=\"button\" class=\"button2\" onclick='Siguiente()' value=\"Siguiente\" />\n"
                                        + "                    \n"
                                        + "                     <input type=\"button\" class=\"button2\" value=\"Calificar\" onclick='CalificarHotObject()()' />\n"
                                        + "                </div>\n"
                                        + "            </div>\n"
                                        + "            \n"
                                        + "            <div id=\"Left\" style=\"border:4px solid #f0b60f;\">\n"
                                        + "                <p>Lea cuidadosamente la pregunta\n"
                                        + "                y seleccione las imegenes que se relacionen con la pregunta</p>\n"
                                        + "                <p>Seleccione <b>Calificar</b> para obtener puntaje</p>\n"
                                        + "                <p>Seleccione <b>Siguiente</b> para pasar a la otra pregunta</p>\n"
                                        + "            </div>\n"
                                        + "        </div>");
                            }
                            break;
                        }
                    }

                    out.println("</form> <script src=\"Scripts/index.js\"></script>\n"
                            + "    </body>\n"
                            + "</html>\n"
                            + "");
                } catch (JDOMException io) {
                    System.out.println(io.getMessage());
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
