/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.w3c.dom.NodeList;


public class AltaTF extends HttpServlet {

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
            String ruta=context.getRealPath("/")+"XML/PreguntaTF.xml";
            
            HttpSession sesion=request.getSession();
            //sesion.setAttribute("user",usuario);
            sesion.setAttribute("rutaXML",ruta);
            String id = request.getParameter("ID");
            String respuestaCorrecta= request.getParameter("Correcta");
            String pregunta = request.getParameter("pregunta");
            File fichero=new File(ruta);
            if (fichero.isFile())
            {
                try{
                    SAXBuilder builder=new SAXBuilder();
                    Document doc=(Document) builder.build(fichero);
                    
                    Element raiz=doc.getRootElement();
                    Element ePregunta=new Element("pregunta");
                    Element eTexto = new Element("texto");
                    Element eRespuesta = new Element("respuesta");
                    ePregunta.setAttribute("id", id);
                    eTexto.setText(pregunta);
                    eRespuesta.setText(respuestaCorrecta);
                    ePregunta.addContent(eTexto);
                    ePregunta.addContent(eRespuesta);
                    
                    raiz.addContent(ePregunta);
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    xmlOutput.output(doc, new FileWriter(ruta));
                    System.out.println("EXITO ");
                    } catch (JDOMException ex) {
                    Logger.getLogger(AltaTF.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else {
                    System.out.println("error");
            }
            response.sendRedirect("/ProyectoWeb/Vistas/Administrador.html");
            // TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AltaTF</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AltaTF at " + respuestaCorrecta + " "+id+" "+pregunta+"</h1>");
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
