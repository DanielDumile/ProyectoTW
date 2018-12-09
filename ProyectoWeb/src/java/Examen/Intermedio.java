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
public class Intermedio extends HttpServlet {

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
            ArrayList respuestasSesion;
            respuestasSesion = (ArrayList) sesion.getAttribute("respuestas");
            
            String idExamen = request.getParameter("ID");//ya
            String answer = request.getParameter("answer");//ya
            int indice = Integer.parseInt(request.getParameter("indice"));//ya
            int cant_preguntas = Integer.parseInt(request.getParameter("cantidad"));//ya
            String direccion = request.getParameter("direccion");
            if(!answer.equals("X")){
                respuestasSesion.set(indice, answer);
            }
            
            sesion.setAttribute("respuestas", respuestasSesion);

            if (direccion.equals("Evaluacion")) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet InicioExamen</title>");
                out.println("<script> function cargar(){ document.getElementById(\"formi\").submit();}</script>");
                out.println("</head>");
                out.println("<body onLoad='cargar();'><form method='post' name='formi' id='formi' action='Evaluacion'>");
                out.println("<input type='text' name='ID' value='" + idExamen + "' hidden>");
                out.println("<input type='text' name='cantidad' value='" + String.valueOf(cant_preguntas) + "' hidden>");
                out.println("<input type='text' name='indice' value='"+String.valueOf(indice)+"' hidden></form>");
                out.println("</body>");
                out.println("</html>");
            }
            else{
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet InicioExamen</title>");
                out.println("<script> function cargar(){ document.getElementById(\"formi\").submit();}</script>");
                out.println("</head>");
                out.println("<body onLoad='cargar();'><form method='post' name='formi' id='formi' action='ResolverExamen'>");
                out.println("<input type='text' name='ID' value='" + idExamen + "' hidden>");
                out.println("<input type='text' name='cantidad' value='" + cant_preguntas + "' hidden>");
                if(direccion.equals("Siguiente"))
                    out.println("<input type='text' name='indice' value='"+(indice+1)+"' hidden>");
                else
                    out.println("<input type='text' name='indice' value='"+(indice-1)+"' hidden>");
                out.println("</form></body>");
                out.println("</html>");
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
