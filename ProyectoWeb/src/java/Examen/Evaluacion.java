/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author USER
 */
public class Evaluacion extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession sesion = request.getSession();
            //sesion.setAttribute("rutaXML", ruta);
            ArrayList respuestasSesion = new ArrayList<>();
            respuestasSesion = (ArrayList) sesion.getAttribute("respuestas");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Evaluacion</title>");
            out.println("<link rel=\"stylesheet\" href=\"Styles/Style.css\" type=\"text/css\">\n"
                    + "        <script src=\"../Frameworks/vue.js\"></script>"
                    + "<script src=\"Scripts/index.js\"></script>");
            out.println("</head>");
            out.println("<body>");

            out.println("\n"
                    + "        <div id=\"Preguntas\">\n"
                    + "                     <div id=\"Contenido3\">\n"
                    + "                \n"
                    + "                <div id=\"Top\" style=\"background-color: #117db8; position: relative; top: -25px;\">\n"
                    + "                    <p class='Subtitulos3' style=\"position: relative; left: 20px; top:10px\">Resultados de la Evaluación</p>"
                    + "                    <br />\n"
                    + "                \n"
                    + "                </div>\n"
                    + "                \n"
                    + "                <br />\n"
                    + "               \n"
                    + "");
            out.println("<div style=\"overflow: scroll; width:680px; height:450px; \">");
            for (int i = 0; i < respuestasSesion.size(); i++) {
                out.println("<p class='Subtitulos3' style=\"position: relative; left: 20px; color: black;\">Respuesta a la pregunta numero " + (i + 1) + " es: " + respuestasSesion.get(i) + "</p><br>");
            }
            out.println("</div>");
            out.println("<input type='button' value='Regresar' style=\"position: relative; left: 250px; top: 20px;\" class='button3' onclick='RegresarExamen()' />\n"
                    + "                \n"
                    + "                 \n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "            \n"
                    + "            \n"
                    + " \n"
                    + "        <script src=\"../Scripts/index.js\"></script>");

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
