/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen;

import static com.sun.faces.facelets.util.Path.context;
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
 * @author stark
 */
public class NewExamen extends HttpServlet {

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

            HttpSession sesion = request.getSession();
            sesion.setAttribute("rutaXML", ruta);

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

                out.println("<div id=\"Preguntas\">\n" +
"            <form action=\"AltaExamen\" method=\"post\" > \n" +
"            <div id=\"Contenido3\">\n" +
"                \n" +
"                <div id=\"Top\" style=\"background-color: #117db8;\">\n" +
"                    \n" +
"                    <br />\n" +
"                    <b>\n" +
"                        <p class=\"Subtitulos3\">Crear Nuevo Examen</p> \n" +
"                    </b> \n" +
"                   \n" +
"                </div>\n" +
"                \n" +
"                <br />\n" +
"               \n" +
"                <p class=\"Subtitulos3\" style=\"color: black\">ID de Examen:   \n" +
"                <input class=\"Form2\n" +
"                       \" v-model=\"ID\" name=\"ID\" style=\"width: 30px;\" required placeholder=\"#\"></p>\n" +
"                 \n" +
"                <p class=\"Subtitulos3\" style=\"color: black\">Nombre del Examen </p>\n" +
"                <p class=\"Subtitulos3\"><textarea cols=\"40\" rows=\"3\" v-model=\"pregunta\" required name=\"pregunta\" placeholder=\"Escribe una pregunta de cualquier tema\"></textarea></p>\n" +
"                \n" +
"                <p class=\"Subtitulos3\" style=\"color: black\">Preguntas Disponibles</p>\n" +
"                <p class=\"Subtitulos3\" style=\"color: black\">Seleccione las preguntas que quiera en su examen</p>");

                for (int i = 0; i < list.size(); i++) {
                    //Se obtiene el elemento 'user1'
                    Element campo = (Element) list.get(i);
                    String id = campo.getAttributeValue("id");

                    //Se obtiene el valor que esta entre los tags
                    String texto = campo.getChildTextTrim("texto");
                    String tipo = campo.getChildTextTrim("tipo");

                    out.println("<p style=\"position: relative; left: 20px;\"><input type=\"checkbox\" name=\"idPreguntas\" value=\""+id+"\"> Pregunta: "+texto+" --> Tipo:"+tipo+"\n" +
"</p>");

                }
                out.println("</div>"
                        + "<input type=\"submit\" class=\"button2\" value=\"Subir Examen\" style=\"position: relative; top: 20px; left: -30px;\" />\n" +
"                <input type=\"button\" class=\"button3\" value=\"Regresar\" style=\"position: relative; top: 20px; left: 10px;\" />"
                        + "</form>"
                        + "</div>"
                        + "<script src=\"../Scripts/index.js\"></script>");

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
