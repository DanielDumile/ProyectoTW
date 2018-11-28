/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            String pregunta = request.getParameter("pregunta");
            String respuesta = request.getParameter("respuesta");
            
              
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cambios</title>"); 
            out.println("<link rel=\"stylesheet\" href='Styles/Style.css' type=\"text/css\" />\n" +
"        <script src=\"../Frameworks/vue.js\"></script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>\n" +
"         <div id=\"TrueFalse\">\n" +


"            \n" +
"            <form v-on:submit=\"TF\" action=\"ModificarPregunta\" method=\"post\">\n" +
"                \n" +
"                <p class=\"Titulos\">Modificar Pregunta</p>\n" +
"                \n" +
"                <hr />\n" +
"       \n" +
"                <p class=\"Subtitulos\">ID de Pregunta:   \n" +
"                <input class=\"Form2\n" +
"                       \" v-model=\"ID\" name=\"ID\" required></p>\n" +
"                \n" +
"                <p class=\"Subtitulos\">Pregunta: </p>\n" +
"                <p><textarea cols=\"40\" rows=\"5\" v-model=\"pregunta\" required name=\"pregunta\" value="+pregunta+"></textarea></p>\n" +
"                \n" +
"                <p class=\"Subtitulos\">Distractores:</p>\n" +
"                <p>Seleccione en cual respuesta sera la correcta</p>\n");
                     
            if(respuesta == "Verdadero"){
                
                out.println("      <p><input class=\"Form\" v-model=\"T\" required name=\"T\" placeholder=\"Opción 1\" disabled value=\"True\"> <input type=\"radio\" name=\"Correcta\" value=\"Verdadero\" checked></p>\n" +
"                <p><input class=\"Form\" v-model=\"F\" required name=\"F\" placeholder=\"Opción 2\" disabled value=\"False\"> <input type=\"radio\" name=\"Correcta\" value=\"Falso\"></p>\n");
        
            }else{
             out.println("      <p><input class=\"Form\" v-model=\"T\" required name=\"T\" placeholder=\"Opción 1\" disabled value=\"True\"> <input type=\"radio\" name=\"Correcta\" value=\"Verdadero\" ></p>\n" +
"                <p><input class=\"Form\" v-model=\"F\" required name=\"F\" placeholder=\"Opción 2\" disabled value=\"False\"> <input type=\"radio\" name=\"Correcta\" value=\"Falso\"></p> checked\n");
        
            
            }
                    
                 
out.println(" <br />\n" +
"                \n" +
"                <button class=\"button1\" type=\"submit\">Subir pregunta</button>   \n" +
"                \n" +
"            </form>\n" +
"                <br />\n" +
"                <button class=\"button3\" onclick=\"Regresar()\">Regresar</button>   \n" +
"                \n" +
"            \n" +
"        </div>\n" +
"    </center>\n" +
"    <script src=\"../Scripts/index.js\"></script>");
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
