/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen;

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
 * @author USER
 */
public class ModE extends HttpServlet {

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
            String ruta=context.getRealPath("/")+"XML/Examen.xml";        
        
            HttpSession sesion=request.getSession();
            sesion.setAttribute("rutaXML",ruta);
            
            //Consulta de preguntas
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Consulta Examen</title>"); 
            out.println("<link rel=\"stylesheet\" href=\"Styles/Style.css\" type=\"text/css\" />");
            out.println("</head>");
            out.println("<body>");

            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(ruta);
            try{//Se crea el documento a traves del archivo  
                Document document = (Document) builder.build( xmlFile );
                //Se obtiene la raiz 
                Element rootNode = document.getRootElement();
                //Se obtiene la lista de hijos de la raiz 'usuarios'
                List list = rootNode.getChildren( "examen" );
                out.println("<form method='post' name='f1' id='f1' action='CambiosExamen'>");
                    out.println("<p class='Titulos'>Lista de examenes</p>");
                    out.println("<p class='Subtitulos'>Seleccione el examen que desee modificar</p>");
                    out.println("<br />");
                    out.println("<hr />");
                    for ( int i = 0; i < list.size(); i++ )
                    {
                       //Se obtiene el elemento 'user1'
                        Element campo = (Element) list.get(i);
                        String id = campo.getAttributeValue("id");
                        //Se obtiene el valor que esta entre los tags
                        String texto = campo.getChildTextTrim("texto");  
                        
                        out.println("");
                  
                        out.print("<input type='button' value='Seleccionar' id="+i+" onclick='Poner("+id+","+i+","+list.size()+")'class='button4'>");
                        out.println("<p class='Subtitulos'><b>Examen "+(i+1)+":</b> "+texto+"<br></p>");

                        out.println("<hr />");    
                    
                }
                    out.println("<center><input type='text' class='text1' name='ID' id='ID' value=' ' hidden/><center>");
                    out.println("</form>");
                    out.println("<br />");
                    
                    out.println("<br />");
        
                    
                    out.print("<center><input type='button' value='Modificar el examen seleccionado' class='button5' onclick='ModificarPregunta()' /></center>");
                    out.println("<br />");
      
                    out.print("<center><input type='button' value='Regresar' class='button5' onclick='RegresarExamen()' /></center>");
                    out.println("<script src=\"Scripts/index.js\"></script>");
            }catch ( JDOMException io ) {
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
