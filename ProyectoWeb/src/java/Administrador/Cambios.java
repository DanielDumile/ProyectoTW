/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author stark
 */
public class Cambios extends HttpServlet {

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
                        + "        <title>Cambiar</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "        <link rel=\"stylesheet\" href=\"Styles/Style.css\" type=\"text/css\">\n"
                        + "        <script src=\"Frameworks/vue.js\"></script>\n"
                        + "        <script src=\"Scripts/Validaciones.js\"></script>"
                        + "        \n"
                        + "    </head>\n <script>\n"
                        + "        function OnOff() {\n"
                        + "\n"
                        + "            if (document.F1.Activar.checked) {\n"
                        + "\n"
                        + "                document.F1.inicial.disabled = false;\n"
                        + "                document.F1.evaluar.disabled = false;\n"
                        + "                document.F1.correcta.disabled = false;\n"
                        + "                document.F1.incorrecta.disabled = false;\n"
                        + "                document.F1.intentar.disabled = false;\n"
                        + "\n"
                        + "            } else {\n"
                        + "\n"
                        + "                document.F1.inicial.disabled = true;\n"
                        + "                document.F1.evaluar.disabled = true;\n"
                        + "                document.F1.correcta.disabled = true;\n"
                        + "                document.F1.incorrecta.disabled = true;\n"
                        + "                document.F1.intentar.disabled = true;\n"
                        + "\n"
                        + "            }\n"
                        + "             if(document.F1.ActivarMulti.checked){\n"
                        + "                \n"
                        + "                document.F1.multimedia.disabled=false;\n"
                        + "                \n"
                        + "                \n"
                        + "            }else{\n"
                        + "                \n"
                        + "                document.F1.multimedia.disabled=true;\n"
                        + "                \n"
                        + "            }\n"
                        + "\n"
                        + "        }\n"
                        + "    </script>"
                        + "     <body onload=\"OnOff()\"> ");
                for (int i = 0; i < list.size(); i++) {
                    //Se obtiene el elemento 'user1'
                    Element campo = (Element) list.get(i);
                    String id = campo.getAttributeValue("id");
                    String tipo = campo.getChildTextTrim("tipo");
                    String texto = campo.getChildTextTrim("texto");
                    String respuesta = campo.getChildTextTrim("respuesta");
                    //Nuevos atributos
                    String intentos = campo.getChildTextTrim("intentos");
                    String multimedia = campo.getChildTextTrim("multimedia");
                    String inicial = campo.getChildTextTrim("inicial");
                    String evaluar = campo.getChildTextTrim("evaluar");
                    String correcta = campo.getChildTextTrim("correcta");
                    String incorrecta = campo.getChildTextTrim("incorrecta");
                    String intentar = campo.getChildTextTrim("intentar");
                    //Se obtiene el valor que esta entre los tags
                    if (id_pregunta.equals(id)) {
                        if (tipo.equals("TrueFalse")) {
                            sesion.setAttribute("tipo", "TrueFalse");

                            out.println("<form action=\"../AltaTF\" method=\"post\" name=\"F1\" enctype='multipart/form-data'>\n"
                                    + "            <div id=\"Preguntas2\">\n"
                                    + "\n"
                                    + "                <div id=\"Contenido2\">\n"
                                    + "\n"
                                    + "                    <div id=\"Top2\">\n"
                                    + "                        <br />\n"
                                    + "                        <b>\n"
                                    + "                            <p class=\"Subtitulos3\">Crear Nueva Pregunta True or False</p> \n"
                                    + "                        </b>\n"
                                    + "\n"
                                    + "                    </div>\n"
                                    + "\n"
                                    + "                    <br />\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black\">ID de Pregunta:   \n"
                                    + "                        <input class=\"Form2\n"
                                    + "                               \" v-model=\"ID\" name=\"ID\" style=\"width: 30px;\" disabled value=\"" + id + "\"></p>\n"
                                    + " <input type = 'text' value='" + id + "' name='IDV' hidden>"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: right; position: relative; top: -70px; left: -70px;\">Numero de Intentos:\n"
                                    + "\n"
                                    + "                        <input class=\"Form2\n"
                                    + "                               \" v-model=\"intentos\" name=\"intentos\" style=\"width: 30px;\" required value=\"" + intentos + "\"></p>\n"
                                    + "                     \n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: right; position: relative; top: -20px; left: 120px;\">Activar Multimedia <input type=\"checkbox\" onclick=\"OnOff()\" name=\"ActivarMulti\" />\n"
                                    + "                     <br>"+multimedia+"</p>\n"
                                    + "\n"
                                    + "                   <br><p class=\"Subtitulos3\" style=\"color: black; float: right; position: relative; top: 10px; left: 390px;\">\n"
                                    + "                        <input class=\"Form2\n"
                                    + "                               \" type=\"file\" name=\"multimedia\" style=\"width: 270px; height: 25px;\"></p>\n"
                                    + "                    \n"
                                    + "                   \n"
                                    + "                    \n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: -90px; left: 20px;\">Pregunta: </p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: -50px; left: -60px;\"><textarea cols=\"40\" rows=\"3\" v-model=\"pregunta\" required name=\"pregunta\" >" + texto + "</textarea></p>\n"
                                    + "\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: -75px; left: 20px; \">Seleccione en cual respuesta sera la correcta</p>\n"
                                    + "\n");
                            if (respuesta.equals("Verdadero")) {
                                out.println("                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: -30px; left: -350px; \"><input type=\"radio\" value=\"Verdadero\" name=\"Correcta\" checked/> Verdadero</p>\n"
                                        + "\n"
                                        + "                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: 10px; left: -465px; \"><input type=\"radio\" value=\"Falso\" name=\"Correcta\"/> Falso</p>\n"
                                );
                            } else {
                                out.println("                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: -30px; left: -350px; \"><input type=\"radio\" value=\"Verdadero\" name=\"Correcta\" /> Verdadero</p>\n"
                                        + "\n"
                                        + "                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: 10px; left: -465px; \"><input type=\"radio\" value=\"Falso\" name=\"Correcta\"/ checked> Falso</p>\n"
                                );
                            }
                            out.println("\n"
                                    + "                </div>\n"
                                    + "\n"
                                    + "                <div id=\"Left2\">\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black\"><b>FeedBack</b></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black\">Proporcione retroalimentación en las diferentes situaciones de la pregunta</p>\n"
                                    + "                    <hr style=\"border-color: green;\"/>\n"
                                    + "\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\"><b>El FeedBack es opcional pero muy importante:</b></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">Activar FeedBack <input type=\"checkbox\" onclick=\"OnOff()\"name=\"Activar\" /></p>\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack inicial: <input class=\"Form2\n"
                                    + "                                                                                                           \" name=\"inicial\" value='" + inicial + "' style=\"width: 200px; position: relative; left: 70px\"></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack al evaluar: <input class=\"Form2\n"
                                    + "                                                                                                              \" name=\"evaluar\" value='" + evaluar + "' style=\"width: 200px; position: relative; left: 35px\"></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack correcto: <input class=\"Form2\n"
                                    + "                                                                                                            \" name=\"correcta\" value='" + correcta + "' style=\"width: 200px; position: relative; left: 44px\"></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack incorrecto: <input class=\"Form2\n"
                                    + "                                                                                                              \" name=\"incorrecta\" value='" + incorrecta + "' style=\"width: 200px; position: relative; left: 30px\"> </p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack al intentar: <input class=\"Form2\n"
                                    + "                                                                                                               \" name=\"intentar\" value='" + intentar + "' style=\"width: 200px; position: relative; left: 33px\"></p>\n"
                                    + "                    <input type=\"submit\" class=\"button3\"value=\"Subir Pregunta\" style=\"float: right; position: relative; right: 600px; width: 300px; top: 80px;\">\n"
                                    + "                    <input type=\"button\" class=\"button1\"value=\"Regresar\" onclick=\"Regresar()\" style=\"float: right; position: relative; right: 300px; width: 300px; top: 130px;\">\n"
                                    + "                </div>\n"
                                    + "\n"
                                    + "\n"
                                    + "            </div>\n"
                                    + "\n"
                                    + "\n"
                                    + "        </form>");
                        } else {
                            sesion.setAttribute("tipo", "HotObject");
                            List lista = campo.getChildren("opcion");
                            //((Element) lista.get(0)).getTextTrim()
                            out.println("");
                            
                            out.println("<form action=\"../AltaHot\" method=\"post\" name=\"F1\" enctype='multipart/form-data'>\n"
                                    + "\n"
                                    + "            <div id=\"Preguntas2\">\n"
                                    + "\n"
                                    + "                <div id=\"Contenido2\" style=\"border-bottom: 6px solid #f0b60f;height: 700px; position: relative; top: 40px\">\n"
                                    + "\n"
                                    + "                    <div id=\"Top2\" style=\"background-color: #f0b60f\">\n"
                                    + "                        <br />\n"
                                    + "                        <b>\n"
                                    + "                            <p class=\"Subtitulos3\">Crear Nueva Pregunta Hot Object</p> \n"
                                    + "                        </b>\n"
                                    + "\n"
                                    + "                    </div>\n"
                                    + "\n"
                                    + "                    <br />\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black\">ID de Pregunta:   \n"
                                    + "                        <input class=\"Form2\n"
                                    + "                               \" v-model=\"ID\" name=\"ID\" style=\"width: 30px;\" disabled value='" + id + "'></p>\n"
                                    + " <input type = 'text' value='" + id + "' name='IDV' hidden>"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: right; position: relative; top: -70px; left: -70px;\">Numero de Intentos:\n"
                                    + "\n"
                                    + "                        <input class=\"Form2\n"
                                    + "                               \" v-model=\"intentos\" name=\"intentos\" style=\"width: 30px;\" required value=\"" + intentos + "\"></p>\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: right; position: relative; top: -20px; left: 120px;\">Activar Multimedia <input type=\"checkbox\" onclick=\"OnOff()\" name=\"ActivarMulti\" />\n"
                                    + "                    </p>\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: right; position: relative; top: 10px; left: 390px;\">\n"
                                    + "                        <input class=\"Form2\n"
                                    + "                               \" type=\"file\" name=\"multimedia\" style=\"width: 270px; height: 25px;\"></p>\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: -90px; left: 20px;\">Pregunta: </p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: -50px; left: -60px;\"><textarea cols=\"40\" rows=\"3\" v-model=\"pregunta\" required name=\"pregunta\" > " + texto + "</textarea></p>\n"
                                    + "\n"
                                    + "                    <!--<img class=\"Ad4\" src=\"../Imagenes/VF.png\" alt=\"Examen\" />-->\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black; float: left; position: relative; top: -75px; left: 20px;\">Suba imagenes de las opciones y seleccione cuales seran correctas:</p>\n"
                                    + "\n"
                                    + "                    <p><input class=\"Form2\" v-model=\"H1\" type=\"file\" required name=\"opciones\" placeholder=\"Opción 1\" style=\"width: 600px; height: 25px; position: relative; left: 16px; top: -50px;\"> "
                                    + "                    <input type=\"checkbox\" name=\"Correcta\" value=\"1\" style=\"position: relative; left: 20px; top: -50px;\"></p>\n"
                                    
                                    + "                    <p><input class=\"Form2\" v-model=\"H2\" type=\"file\" required name=\"opciones\" placeholder=\"Opción 2\"style=\"width: 600px; height: 25px; position: relative; left: 16px; top: -50px;\" > "
                                    + "                    <input type=\"checkbox\" name=\"Correcta\" value=\"2\" style=\"position: relative; left: 20px;top: -50px;\"></p>\n"
                                    
                                    + "                    <p><input class=\"Form2\" v-model=\"H1\" type=\"file\" required name=\"opciones\" placeholder=\"Opción 3\" style=\"width: 600px; height: 25px; position: relative; left: 16px; top: -50px;\"> <input type=\"checkbox\" name=\"Correcta\" value=\"3\" style=\"position: relative; left: 20px; top: -50px;\"></p>\n"
                                    + "                    <p><input class=\"Form2\" v-model=\"H2\" type=\"file\" required name=\"opciones\" placeholder=\"Opción 4\"style=\"width: 600px; height: 25px; position: relative; left: 16px; top: -50px;\" > <input type=\"checkbox\" name=\"Correcta\" value=\"4\" style=\"position: relative; left: 20px;top: -50px;\"></p>\n"
                                    + "                    <p><input class=\"Form2\" v-model=\"H1\" type=\"file\" required name=\"opciones\" placeholder=\"Opción 5\" style=\"width: 600px; height: 25px; position: relative; left: 16px; top: -50px;\"> <input type=\"checkbox\" name=\"Correcta\" value=\"5\" style=\"position: relative; left: 20px;top: -50px;\"></p>\n"
                                    + "                    <p><input class=\"Form2\" v-model=\"H2\" type=\"file\" required name=\"opciones\" placeholder=\"Opción 6\"style=\"width: 600px; height: 25px; position: relative; left: 16px; top: -50px;\" > <input type=\"checkbox\" name=\"Correcta\" value=\"6\" style=\"position: relative; left: 20px;top: -50px;\"></p>\n"
                                    + "                    <p><input class=\"Form2\" v-model=\"H1\" type=\"file\" required name=\"opciones\" placeholder=\"Opción 7\" style=\"width: 600px; height: 25px; position: relative; left: 16px; top: -50px;\"> <input type=\"checkbox\" name=\"Correcta\" value=\"7\" style=\"position: relative; left: 20px;top: -50px;\"></p>\n"
                                    + "                    <p><input class=\"Form2\" v-model=\"H2\" type=\"file\" required name=\"opciones\" placeholder=\"Opción 8\"style=\"width: 600px; height: 25px; position: relative; left: 16px; top: -50px;\" > <input type=\"checkbox\" name=\"Correcta\" value=\"8\" style=\"position: relative; left: 20px;top: -50px;\"></p>\n"
                                    + "\n"
                                    + "\n"
                                    + "                </div>\n"
                                    + "\n"
                                    + "                <div id=\"Left2\"style=\"border:4px solid #f0b60f; top: -50px;\">\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black\"><b>FeedBack</b></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"color: black\">Proporcione retroalimentación en las diferentes situaciones de la pregunta</p>\n"
                                    + "                    <hr style=\"border-color: #f0b60f;\"/>\n"
                                    + "\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\"><b>El FeedBack es opcional pero muy importante:</b></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">Activar FeedBack <input type=\"checkbox\" onclick=\"OnOff()\"name=\"Activar\" /></p>\n"
                                    + "\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack inicial: <input class=\"Form2\n"
                                    + "                                                                                                           \" name=\"inicial\" value='"+inicial+"' style=\"width: 200px; position: relative; left: 70px\"></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack al evaluar: <input class=\"Form2\n"
                                    + "                                                                                                              \" name=\"evaluar\" value='"+evaluar+"' style=\"width: 200px; position: relative; left: 35px\"></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack correcto: <input class=\"Form2\n"
                                    + "                                                                                                            \" name=\"correcta\" value='"+correcta+"' style=\"width: 200px; position: relative; left: 44px\"></p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack incorrecto: <input class=\"Form2\n"
                                    + "                                                                                                              \" name=\"incorrecta\" value='"+incorrecta+"' style=\"width: 200px; position: relative; left: 30px\"> </p>\n"
                                    + "                    <p class=\"Subtitulos3\" style=\"text-align: left; color:black;\">FeedBack al intentar: <input class=\"Form2\n"
                                    + "                                                                                                               \" name=\"intentar\" value='"+intentar+"' style=\"width: 200px; position: relative; left: 33px\"></p>\n"
                                    + "                    <input type=\"submit\" class=\"button3\"value=\"Subir Pregunta\" style=\"float: right; position: relative; right: 200px; width: 300px; top: 80px;\">\n"
                                    + "                    <input type=\"button\" class=\"button1\"value=\"Regresar\" onclick=\"Regresar()\"style=\"float: right; position: relative; right: -100px; width: 300px; top: 130px;\">\n"
                                    + "                </div>\n"
                                    + "\n"
                                    + "\n"
                                    + "            </div>\n"
                                    + "\n"
                                    + "\n"
                                    + "        </form>");
                            
                        }
                        break;
                    }
                }

                out.println("</center>"
                        + " <script src=\"Scripts/index.js\"></script>\n"
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
