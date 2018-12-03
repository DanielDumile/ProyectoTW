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
                        + "    </head>\n"
                        + "    <body> "
                        + "     <center>");
                for (int i = 0; i < list.size(); i++) {
                    //Se obtiene el elemento 'user1'
                    Element campo = (Element) list.get(i);
                    String id = campo.getAttributeValue("id");
                    String tipo = campo.getChildTextTrim("tipo");
                    String texto = campo.getChildTextTrim("texto");
                    String respuesta = campo.getChildTextTrim("respuesta");
                    //Se obtiene el valor que esta entre los tags
                    if (id_pregunta.equals(id)) {
                        if (tipo.equals("TrueFalse")) {
                            sesion.setAttribute("tipo", "TrueFalse");
                            
                            out.println("<div id=\"TrueFalse\">\n"
                                    + "            <img class=\"Ad\" src=\"Imagenes/TF.png\" alt=\"Examen\"/>\n"
                                    + "            \n"
                                    + "            \n"
                                    + "            <form v-on:submit=\"TF\" action=\"ModificarPregunta\" method=\"post\">\n"
                                    + "                \n"
                                    + "                <p class=\"Titulos\">Preguntas tipo: True or False</p>\n"
                                    + "                \n"
                                    + "                <hr />\n"
                                    + "       \n"
                                    + "                <p class=\"Subtitulos\">ID de Pregunta:   \n"
                                    + "                <input class=\"Form2\n"
                                    + "                       \" v-model=\"ID\" name=\"ID\" value='" + id + "' disabled></p>\n"
                                    + " <input type = 'text' value='" + id + "' name='IDV' hidden>"
                                    + "                \n"
                                    + "                <p class=\"Subtitulos\">Pregunta: </p>\n"
                                    + "                <p><textarea cols=\"40\" rows=\"5\" v-model=\"pregunta\" required name=\"pregunta\" placeholder=\"" + texto + "\"></textarea></p>\n"
                                    + "                \n"
                                    + "                <p class=\"Subtitulos\">Distractores:</p>\n"
                                    + "                <p>Seleccione en cual respuesta sera la correcta</p>\n");
                            if (respuesta.equals("Verdadero")) {
                                out.println("                <p><input class=\"Form\" v-model=\"T\" required name=\"T\" placeholder=\"Opción 1\" disabled value=\"True\"> <input type=\"radio\" name=\"Correcta\" value=\"Verdadero\" checked></p>\n"
                                        + "                <p><input class=\"Form\" v-model=\"F\" required name=\"F\" placeholder=\"Opción 2\" disabled value=\"False\"> <input type=\"radio\" name=\"Correcta\" value=\"Falso\"></p>\n"
                                );
                            } else {
                                out.println("                <p><input class=\"Form\" v-model=\"T\" required name=\"T\" placeholder=\"Opción 1\" disabled value=\"True\"> <input type=\"radio\" name=\"Correcta\" value=\"Verdadero\" ></p>\n"
                                        + "                <p><input class=\"Form\" v-model=\"F\" required name=\"F\" placeholder=\"Opción 2\" disabled value=\"False\"> <input type=\"radio\" name=\"Correcta\" value=\"Falso\" checked></p>\n"
                                );
                            }
                            out.println("                \n"
                                    + "                <br />\n"
                                    + "                \n"
                                    + "                <button class=\"button1\" type=\"submit\">Subir pregunta</button>   \n"
                                    + "                \n"
                                    + "            </form>\n"
                                    + "                <br />\n"
                                    + "                <button class=\"button3\" onclick=\"Regresar()\">Regresar</button>   \n"
                                    + "                \n"
                                    + "            \n"
                                    + "        </div>");
                        } else {
                            sesion.setAttribute("tipo", "HotObject");
                            List lista = campo.getChildren("opcion");
                            out.println("<div id=\"Hot\">\n"
                                    + "            <img class=\"Ad1\" src=\"Imagenes/Object.png\" alt=\"Examen\"/>\n"
                                    + "            \n"
                                    + "            <form v-on:submit=\"Hot\" action=\"ModificarPregunta\" method=\"post\">\n"
                                    + "                \n"
                                    + "                <p class=\"Titulos\">Preguntas tipo: Hot Object</p>\n"
                                    + "                \n"
                                    + "                <hr />\n"
                                    + "                \n"
                                    + "                <p class=\"Subtitulos\">ID de Pregunta:   \n"
                                    + "                <input class=\"Form2\n"
                                    + "                       \" v-model=\"ID\" name=\"ID\" value ='"+id+"' disabled></p>\n"
                                    + " <input type = 'text' value='" + id + "' name='IDV' hidden>"
                                    + "                \n"
                                    + "                <p class=\"Subtitulos\">Pregunta: </p>\n"
                                    + "                <p><textarea cols=\"40\" rows=\"5\" v-model=\"pregunta\" required name=\"pregunta\" placeholder=\""+texto+"\"></textarea></p>\n"
                                    + "                \n"
                                    + "                <p class=\"Subtitulos\">Hot Object:</p>\n"
                                    + "                <p>Seleccione que respuestas seran correctas</p>\n"
                                    + "                <p><input class=\"Form\" v-model=\"H1\" type=\"file\" required name=\"opciones\" placeholder=\"" + ((Element) lista.get(0)).getTextTrim() + "\" > <input type=\"checkbox\" name=\"Correcta\" value=\"1\"></p>\n"
                                    + "                <p><input class=\"Form\" v-model=\"H2\" type=\"file\" required name=\"opciones\" placeholder=\"" + ((Element) lista.get(1)).getTextTrim() + "\" > <input type=\"checkbox\" name=\"Correcta\" value=\"2\"></p>\n"
                                    + "                <p><input class=\"Form\" v-model=\"H3\" type=\"file\" required name=\"opciones\" placeholder=\"" + ((Element) lista.get(2)).getTextTrim() + "\" > <input type=\"checkbox\" name=\"Correcta\" value=\"3\"></p>\n"
                                    + "                <p><input class=\"Form\" v-model=\"H4\" type=\"file\" required name=\"opciones\" placeholder=\"" + ((Element) lista.get(3)).getTextTrim() + "\" > <input type=\"checkbox\" name=\"Correcta\" value=\"4\"></p>\n"
                                    + "                <p><input class=\"Form\" v-model=\"H5\" type=\"file\" required name=\"opciones\" placeholder=\"" + ((Element) lista.get(4)).getTextTrim() + "\" > <input type=\"checkbox\" name=\"Correcta\" value=\"5\"></p>\n"
                                    + "                <p><input class=\"Form\" v-model=\"H6\" type=\"file\" required name=\"opciones\" placeholder=\"" + ((Element) lista.get(5)).getTextTrim() + "\" > <input type=\"checkbox\" name=\"Correcta\" value=\"6\"></p>\n"
                                    + "                <p><input class=\"Form\" v-model=\"H7\" type=\"file\" required name=\"opciones\" placeholder=\"" + ((Element) lista.get(6)).getTextTrim() + "\" > <input type=\"checkbox\" name=\"Correcta\" value=\"7\"></p>\n"
                                    + "                <p><input class=\"Form\" v-model=\"H8\" type=\"file\" required name=\"opciones\" placeholder=\"" + ((Element) lista.get(7)).getTextTrim() + "\" > <input type=\"checkbox\" name=\"Correcta\" value=\"8\"></p>\n"
                                    + "                \n"
                                    + "                <br />\n"
                                    + "                \n"
                                    + "                <button class=\"button1\" type=\"submit\">Subir pregunta</button>   \n"
                                    + "                \n"
                                    + "            </form>\n"
                                    + "                <br />\n"
                                    + "                <button class=\"button3\" onclick=\"Regresar()\">Regresar</button>   \n"
                                    + "                \n"
                                    + "            \n"
                                    + "        </div>");
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
