/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen;

import Administrador.AltaTF;
import Administrador.ValidacionId;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
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
public class CambiosExamen extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            String ruta = context.getRealPath("/") + "XML/PreguntaTF.xml";
            String ruta2 = context.getRealPath("/") + "XML/Examen.xml";

            HttpSession sesion = request.getSession();
            sesion.setAttribute("rutaXML", ruta);
            
            String idExamen = request.getParameter("ID");
            String textoExamen="";

            HashSet<String> idPreguntas = new HashSet<>();
            //CONSULTAMOS AL EXAMEN Y A LAS PREGUNTAS QUE TIENE DENTRO
            SAXBuilder builder2 = new SAXBuilder();
            File xmlFile2 = new File(ruta2);
            try {//Se crea el documento a traves del archivo  
                Document document2 = (Document) builder2.build(xmlFile2);
                //Se obtiene la raiz 
                Element rootNode = document2.getRootElement();
                //Se obtiene la lista de hijos de la raiz 'usuarios'
                List list = rootNode.getChildren("examen");
                for (int i = 0; i < list.size(); i++) {
                    //Se obtiene el elemento 'user1'
                    Element campo = (Element) list.get(i);
                    String id = campo.getAttributeValue("id");
                    textoExamen = campo.getChildTextTrim("texto");
                    if (idExamen.equals(id)) {
                        List lista = campo.getChildren("idp");
                        for (int j = 0; j < lista.size(); j++) {
                            Element aux = ((Element) lista.get(j));
                            String id_aux = aux.getTextTrim();
                            idPreguntas.add(id_aux);
                        }
                        break;
                    }
                }
            } catch (JDOMException io) {
                System.out.println(io.getMessage());
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Nuevo Examen</title>");
            out.println("<meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "        \n"
                    + "        <link rel=\"stylesheet\" href=\"Styles/Style.css\" type=\"text/css\">\n"
                    + "        <script src=\"Frameworks/vue.js\"></script>\n"
                    + "        <script src=\"Scripts/Validaciones.js\"></script>"
                    + "<script src=\"Scripts/index.js\"></script>");

            out.println("</head>");
            out.println("<body>");

            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(ruta);
            try {//Se crea el documento a traves del archivo  
                Document document = (Document) builder.build(xmlFile);
                //Se obtiene la raiz 
                Element rootNode = document.getRootElement();
                //Se obtiene la lista de hijos de la raiz 'usuarios'
                List list = rootNode.getChildren("pregunta");

                out.println("<center>\n"
                        + "        <div id=\"TrueFalse\">\n"
                        + "            <p class=\"Titulos\">Creación de un nuevo examen</p>\n"
                        + "                <img class=\"Ad22\" src=\"Imagenes/preguntas.png\" alt=\"Examen\"/>\n"
                        + "            \n"
                        + "            \n"
                        + "            <form v-on:submit=\"TF\" action=\"ModificarExamen\" method=\"post\">\n"
                        + "                \n"
                        + "                <hr />\n"
                        + "       \n"
                        + "                <p class=\"Subtitulos\">ID del examen:   \n"
                        + "                <input class=\"Form2\n"
                        + "                       \" v-model=\"ID\" name=\"ID\" value='"+idExamen+"' disabled required></p>\n"
                        + "                <p class=\"Subtitulos\">Este servira como identifiador del examen</p>\n"
                        + "                \n"
                        + " <input type='text' name='IDV' value='"+idExamen+"' hidden>"
                        + "                <p class=\"Subtitulos\">Nombre del Examen: </p>\n"
                        + "                <p><textarea cols=\"40\" rows=\"5\" v-model=\"texto\" required name=\"texto\" placeholder=\""+textoExamen+"\"></textarea></p>\n"
                        + "                \n"
                        + "                <p class=\"Subtitulos\">Preguntas disponibles</p>\n"
                        + "                <p>Seleccione las preguntas que quiera en su examen</p>\n");

                for (int i = 0; i < list.size(); i++) {
                    //Se obtiene el elemento 'user1'
                    Element campo = (Element) list.get(i);
                    String id = campo.getAttributeValue("id");

                    //Se obtiene el valor que esta entre los tags
                    String texto = campo.getChildTextTrim("texto");
                    String tipo = campo.getChildTextTrim("tipo");
                    if(idPreguntas.contains(id)){
                        out.println("<p><input class=\"Form\" v-model=\"Pregunta\" required name=\"Pregunta\" style=\"width: 800px\" placeholder=\"" + texto + "\" disabled value=\"Pregunta: " + texto + " --> Tipo: " + tipo + "\"> \n"
                            + "<input type=\"checkbox\" name=\"idPreguntas\" value=\"" + id + "\" checked </p>");
                    }
                    else{
                        out.println("<p><input class=\"Form\" v-model=\"Pregunta\" required name=\"Pregunta\" style=\"width: 800px\" placeholder=\"" + texto + "\" disabled value=\"Pregunta: " + texto + " --> Tipo: " + tipo + "\"> \n"
                            + "<input type=\"checkbox\" name=\"idPreguntas\" value=\"" + id + "\"  </p>");
                    }
                    

                }
                out.println("<br />"
                        + "<br />\n"
                        + "                \n"
                        + "                <button class=\"button1\" type=\"submit\">Modificar examen</button>   \n"
                        + "                \n"
                        + "            </form>\n"
                        + "                <br />\n"
                        + "                <button class=\"button3\" onclick=\"RegresarExamen()\">Regresar</button>   \n"
                        + "                \n"
                        + "            \n"
                        + "        </div>\n"
                        + "    </center>\n"
                        + "    <script src=\"../Scripts/index.js\"></script>");

            } catch (JDOMException io) {
                System.out.println(io.getMessage());
            }
            out.println("</body>");
            out.println("</html>");
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
