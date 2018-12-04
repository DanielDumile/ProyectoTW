/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
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
public class ConsultaExamen extends HttpServlet {

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
            String ruta = context.getRealPath("/") + "XML/Examen.xml";
            String ruta2 = context.getRealPath("/") + "XML/PreguntaTF.xml";

            HttpSession sesion = request.getSession();
            sesion.setAttribute("rutaXML", ruta);

            String idExamen = request.getParameter("ID");
            //String idExamen = "1";

            HashSet<String> idPreguntas = new HashSet<>();
            //CONSULTAMOS AL EXAMEN Y A LAS PREGUNTAS QUE TIENE DENTRO
            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(ruta);
            try {//Se crea el documento a traves del archivo  
                Document document = (Document) builder.build(xmlFile);
                //Se obtiene la raiz 
                Element rootNode = document.getRootElement();
                //Se obtiene la lista de hijos de la raiz 'usuarios'
                List list = rootNode.getChildren("examen");
                for (int i = 0; i < list.size(); i++) {
                    //Se obtiene el elemento 'user1'
                    Element campo = (Element) list.get(i);
                    String id = campo.getAttributeValue("id");
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
            out.println("<title>Eliminar Preguntas</title>");
            out.println("<link rel=\"stylesheet\" href=\"Styles/Style.css\" type=\"text/css\" />");
            out.println("</head>");
            out.println("<body>");

            //SACAMOS A TODAS LAS PREGUNTAS PERO SOLO PONEMOS LAS QUE ESTAN DENTRO DE NUESTRO EXAMEN
            SAXBuilder builder2 = new SAXBuilder();
            File xmlFile2 = new File(ruta2);
            try {//Se crea el documento a traves del archivo  
                Document document2 = (Document) builder2.build(xmlFile2);
                //Se obtiene la raiz 
                Element rootNode2 = document2.getRootElement();
                //Se obtiene la lista de hijos de la raiz 'usuarios'
                List list2 = rootNode2.getChildren("pregunta");
                out.println("<form method='post' name='f1' id='f1' action='MostrarPregunta'>");
                out.println("<p class='Titulos'>Lista de preguntas</p>");
                out.println("<hr />");
                for (int i = 0; i < list2.size(); i++) {
                    //Se obtiene el elemento 'user1'
                    Element campo = (Element) list2.get(i);
                    String id = campo.getAttributeValue("id");
                    if (idPreguntas.contains(id)) {
                        //Se obtiene el valor que esta entre los tags
                        String tipo = campo.getChildTextTrim("tipo");
                        String texto = campo.getChildTextTrim("texto");
                        String respuesta = campo.getChildTextTrim("respuesta");

                        out.println("");

                        out.print("<input type='button' value='Seleccionar' id="+i+" onclick='Poner("+id+","+i+","+list2.size()+")'class='button4'>");
                        out.println("<p class='Subtitulos'><b>Pregunta " + (i + 1) + ":</b> " + texto + "<br></p>");
                        out.println("<p class='Subtitulos'><b>Tipo de Pregunta:</b> " + tipo + "<br></p>");
                        out.print("<p class='Subtitulos'><b>Respuesta:</b> " + respuesta + "<br></p>");

                        out.println("<hr />");
                    }
                }
                out.println("<center><input type='text' class='text1' name='ID' id='ID' value=' ' hidden/><center>");
                out.println("</form>");
                out.println("<br />");

                out.println("<br />");

                out.print("<center><input type='button' value='Visualizar la pregunta seleccionada' class='button5' onclick='VerPregunta()' /></center>");
                out.println("<br />");

                out.print("<center><input type='button' value='Regresar' class='button5' onclick='Regresar()' /></center>");
                out.println("<script src=\"Scripts/index.js\"></script>");
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
