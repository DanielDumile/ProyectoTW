/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrador;

import inicio.LoginBean;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author USER
 */
public class MostrarPregunta extends HttpServlet {

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
            String ruta = context.getRealPath("/") + "XML/PreguntaTF.xml";

            HttpSession sesion = request.getSession();
            sesion.setAttribute("rutaXML", ruta);

            String id_pregunta = request.getParameter("ID");

            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(ruta);
            try {//Se crea el documento a traves del archivo  
                Document document = (Document) builder.build(xmlFile);
                //Se obtiene la raiz 
                Element rootNode = document.getRootElement();
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
                        + "  <style>\n" +
"            \n" +
"            .checkeable input {\n" +
"                display: none;\n" +
"                float: left;\n" +
"            }\n" +
"            \n" +
"            .checkeable img {\n" +
"                width: 100px;\n" +
"                height: 100px;\n" +
"                border: 5px solid transparent;\n" +
"                position: relative;\n" +
"                left: 35px;\n" +
"                bottom: 20px;\n" +
"      \n" +
"            }\n" +
"            \n" +
"            .checkeable input {\n" +
"                display: none;\n" +
"                float: left;\n" +
"            }\n" +
"            \n" +
"            .checkeable input:checked  + img {\n" +
"              border-color: grey;\n" +
"              opacity: 0.5;\n" +
"             \n" +
"            }\n" +
"            \n" +
"        </style>\n" +
"        \n"
                        + "        \n"
                        + "    </head>\n"
                        + "    <body>");
                
                for (int i = 0; i < list.size(); i++) {
                    //Se obtiene el elemento 'user1'
                    Element campo = (Element) list.get(i);
                    String id = campo.getAttributeValue("id");
                    String tipo = campo.getChildTextTrim("tipo");
                    String texto = campo.getChildTextTrim("texto");
                    String respuesta = campo.getChildTextTrim("respuesta");
                    
                    //NUEVOS ELEMENTOS AQUI
                    String multimedia = campo.getChildTextTrim("multimedia");
                    String inicial = campo.getChildTextTrim("inicial");
                    String intentos = campo.getChildTextTrim("intentos");
                    String evaluar = campo.getChildTextTrim("evaluar");
                    String correcta = campo.getChildTextTrim("correcta");
                    String incorrecta = campo.getChildTextTrim("incorrecta");
                    String intentar = campo.getChildTextTrim("intentar");
                    //Se obtiene el valor que esta entre los tags
                    //out.println(id);
                    System.out.println(id);
                    if (id_pregunta.equals(id)) {
                        
                        if (tipo.equals("TrueFalse")) {
                           out.println("");
                            
                            
                            out.println("<div id=\"Preguntas\">\n" +
"            \n" +
                                   
"            <div id=\"Contenido\">\n" +
"                \n" +
"                <div id=\"Top\">\n" +
"                    \n" +
"                    <br />\n" +
"                    <Encabezado Pregunta=\""+texto+"\" ></Encabezado> \n" +
"                    <input type=\"text\" value=\""+id+"\" hidden />\n" +
"                </div>\n" +
"                \n" +
"                <br />\n" +
"                <br />\n" +
"                \n" +
"                <p class=\"Subtitulos3\" style=\"color: black\"><input type=\"radio\" value=\"Verdadero\" name=\"Valor\"/> Verdadero</p>\n" +
"                <p class=\"Subtitulos3\" style=\"color: black\"><input type=\"radio\" value=\"Falso\" name=\"Valor\"/> Falso</p>\n" +
"                 \n" +
"                <img class=\"Ad4\" src=\""+multimedia+"\" alt=\"Examen\" />\n" +
"                \n" +
"                \n" +
"                 <div id=\"Bottom\" style=\"position: relative; left: 0px; top:190px;\" >\n" +
"                     <br />\n" +
"                     <p class=\"Subtitulos3\"><b>Intentos : </b><input type='text' value="+intentos+" disabled name='try' class='Subtitulos3' id='try' width:30px; style='border: none; background-color:#4CAF50; color: white'/></p>\n" +
"                     <input type=\"button\" class=\"button2\"style=\"position: relative; left: 120px; top:-70px;\" onclick=\"Calificar2()\" id=\"Calificar\" value=\"Calificar\" />"
                                    + "<input type=\"button\" class=\"button2\"style=\"position: relative; left: 120px; top:-70px;\" onclick=\"Regresar()\" id=\"Calificar\" value=\"Regresar\" />\n" +
"                </div>\n" +
"            </div>\n" +
"            \n");
                            
                            
                            out.println("<input type='text' value='"+inicial+"' name='inicial' id='inicial' hidden />");
                            out.println("<input type='text' value='"+evaluar+"' name='evaluar' id='evaluar' hidden />");
                            out.println("<input type='text' value='"+correcta+"' name='correcta' id='correcta' hidden />");
                            out.println("<input type='text' value='"+incorrecta+"' name='incorrecta' id='incorrecta' hidden />");
                            out.println("<input type='text' value='"+intentar+"' name='intentar' id='intentar' hidden />");
                            out.println("<input type='text' value='No lo ocupo' name='answer' id='answer' hidden />");
                           
                            out.println("<input type='text' value='"+respuesta+"' name='Resp' id='Resp' hidden />"+
                            
"            <div id=\"Left\" style=\"position: relative; left: 100px; top:-100px;\">\n" +
"                <p>Lea cuidadosamente la pregunta\n" +
"                y seleccione si es verdadera o falsa</p>\n" +
"                <p>Seleccione <b>Calificar</b> para obtener puntaje</p>\n" +
"                <p>Seleccione <b>Siguiente</b> para pasar a la otra pregunta</p>\n" +
"            </div>\n" +
"            \n" +
"            <div id=\"Left\" style=\"position: relative; left: -205px; top:130px;\">\n" +
"                <p><b>FeedBack</b></p>\n" +
"                <p><textarea id='feed' readonly style='width: 290px; height: 120px; background-color: white; text­align: center; border:none;'>"+inicial+"</textarea></p>\n" +
"            </div>\n" +
"        </div>");
                            
                        } else {
                            List lista = campo.getChildren("opcion");
                            out.println("<input type='text' value='"+inicial+"' name='inicial' id='inicial' hidden />");
                            out.println("<input type='text' value='"+evaluar+"' name='evaluar' id='evaluar' hidden />");
                            out.println("<input type='text' value='"+correcta+"' name='correcta' id='correcta' hidden />");
                            out.println("<input type='text' value='"+incorrecta+"' name='incorrecta' id='incorrecta' hidden />");
                            out.println("<input type='text' value='"+intentar+"' name='intentar' id='intentar' hidden />");
                            out.println("<input type='text' value='No lo ocupo' name='answer' id='answer' hidden />");
                           
                            out.println("<input type='text' value='"+respuesta+"' name='Resp' id='Resp' hidden />");
                            
                            out.println("<div id=\"Preguntas\">\n" +
"            \n" +
"            <div id=\"Contenido\" style=\"border-bottom: 6px solid #f0b60f;\">\n" +
"                \n" +
"                <div id=\"Top\" style=\"background-color:#f0b60f\">\n" +
"                    \n" +
"                    <br />\n" +
"                    <Encabezado Pregunta=\""+texto+"\" ></Encabezado> \n" +
"                    <input type=\"text\" value=\""+id+"\" hidden />\n" +
"                </div>\n" +
"                \n" +
"                <br />\n" +
"                <br />\n" +
"                \n" +
"                 <p>\n" +
"                    <label class=\"checkeable\">\n" +
"                        <input type=\"checkbox\" name=\"Respuesta\" value='1'/>\n" +
"                        <img src=\""+((Element) lista.get(0)).getTextTrim()+"\" />\n" +
"                    </label>\n" +
"\n" +
"                    <label class=\"checkeable\">\n" +
"                      <input type=\"checkbox\" name=\"Respuesta\" value='2'/>\n" +
"                      <img src=\""+((Element) lista.get(1)).getTextTrim()+"\" />\n" +
"                    </label>\n" +
"\n" +
"                    <label class=\"checkeable\">\n" +
"                        <input type=\"checkbox\" name=\"Respuesta\" value='3'/>\n" +
"                        <img src=\""+((Element) lista.get(2)).getTextTrim()+"\" />\n" +
"                    </label>\n" +
"\n" +
"                    <label class=\"checkeable\">\n" +
"                      <input type=\"checkbox\" name=\"Respuesta\" value='4'/>\n" +
"                      <img src=\""+((Element) lista.get(3)).getTextTrim()+"\" />\n" +
"                    </label>\n" +
"                </p>\n" +
"                \n" +
"                <p>\n" +
"                    <label class=\"checkeable\">\n" +
"                        <input type=\"checkbox\" name=\"Respuesta\" value='5'/>\n" +
"                        <img src=\""+((Element) lista.get(4)).getTextTrim()+"\" />\n" +
"                    </label>\n" +
"\n" +
"                    <label class=\"checkeable\">\n" +
"                      <input type=\"checkbox\" name=\"Respuesta\" value='6'/>\n" +
"                      <img src=\""+((Element) lista.get(5)).getTextTrim()+"\" />\n" +
"                    </label>\n" +
"                    <label class=\"checkeable\">\n" +
"                        <input type=\"checkbox\" name=\"Respuesta\" value='7'/>\n" +
"                        <img src=\""+((Element) lista.get(6)).getTextTrim()+"\" />\n" +
"                    </label>\n" +
"\n" +
"                    <label class=\"checkeable\">\n" +
"                      <input type=\"checkbox\" name=\"Respuesta\" value='8'/>\n" +
"                      <img src=\""+((Element) lista.get(7)).getTextTrim()+"\" />\n" +
"                    </label>\n" +
"                    \n" +
"                    <img class=\"Ad6\" src=\""+multimedia+"\" alt=\"Examen\" />\n" +
"                </p>\n" +
"                \n" +
"                \n" +
"                \n" +
"                \n" +
"                 <div id=\"Bottom\" style=\"position: relative; left: 0px; top:20px; background-color:#f0b60f\" >\n" +
"                     <br />\n" +
"                     <p class=\"Subtitulos3\"><b>Intentos : </b><input type='text' value="+intentos+" disabled name='try' class='Subtitulos3' id='try' width:30px; style='border: none; background-color: #f0b60f; color: white'/></p>\n" +
"                     <input type=\"button\" class=\"button2\"style=\"position: relative; left: -10px; top:-70px;\" onclick=\"Calificar3()\" id=\"Calificar\" value=\"Calificar\" />"
                                    + "<input type=\"button\" class=\"button2\"style=\"position: relative; left: -10px; top:-70px;\" onclick=\"Regresar()\" id=\"Calificar\" value=\"Regresar\" />\n" +
"               </div>\n" +
"            </div>\n" +
"            \n" +
"            <div id=\"Left\" style=\"position: relative; left: 100px; top: -100px; border: 4px solid #f0b60f;\">\n" +
"                <p>Lea cuidadosamente la pregunta\n" +
"                y seleccione si es verdadera o falsa</p>\n" +
"                <p>Seleccione <b>Calificar</b> para obtener puntaje</p>\n" +
"                <p>Seleccione <b>Siguiente</b> para pasar a la otra pregunta</p>\n" +
"            </div>\n" +
"            \n" +
"            <div id=\"Left\" style=\"position: relative; left: -205px; top:130px; border: 4px solid #f0b60f;\">\n" +
"                <p><b>FeedBack</b></p>\n" +
"                <p><textarea id='feed' readonly style='width: 290px; height: 120px; background-color: white; text­align: center; border:none;'>"+inicial+"</textarea></p>\n" +
"            </div>\n" +
"        </div>");                         
                            
                            
                        }
                        break;
                    }
                }

                out.println("<script src=\"Scripts/index.js\"></script>\n"
                        + "    </body>\n"
                        + "</html>\n"
                        + "");
            } catch (JDOMException io) {
                System.out.println(io.getMessage());
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
