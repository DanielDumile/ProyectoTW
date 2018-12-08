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
 public class AltaHot extends HttpServlet {
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
            sesion.setAttribute("rutaXML",ruta);
            String id = request.getParameter("ID");
            String[] respuestaCorrecta= request.getParameterValues("Correcta");//request.getParameter("Correcta");
            String[] opciones = request.getParameterValues("opciones");
            String pregunta = request.getParameter("pregunta");  
            //Nuevos
            String intentos = request.getParameter("intentos");

            String multimedia,checkMultimedia;
            checkMultimedia= request.getParameter("checkMultimedia");
            if(!checkMultimedia.equals("NO")){
                multimedia = request.getParameter("multimedia");
            }
            //Opciones del feedback
            String inicial="",evaluar="",correcta="",incorrecta="",intentar="";
            String checkFeedback=request.getParameter("checkFeedback");
            if(!checkFeedback.equals("NO")){
                inicial= request.getParameter("inicial");
                evaluar= request.getParameter("evaluar");
                correcta= request.getParameter("correcta");
                incorrecta= request.getParameter("incorrecta");
                intentar= request.getParameter("intentar");
            }

            ValidacionId obj = new ValidacionId();
            if(!obj.validar(id,ruta)){
                response.sendRedirect("/ProyectoWeb/Vistas/Administrador.html");
            }
            else{
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
                        Element eTipo=new Element("tipo");
                        Element eIntentos = new Element("intentos");

                        ePregunta.setAttribute("id", id);
                        eTexto.setText(pregunta);
                        eTipo.setText("HotObject");
                        String auxRespuesta="";
                        for(int i =0; i < respuestaCorrecta.length; i++){
                            auxRespuesta+=respuestaCorrecta[i];
                            if(i != respuestaCorrecta.length-1)
                                auxRespuesta+=',';
                        }
                        System.out.println(auxRespuesta);
                        eRespuesta.setText(auxRespuesta);

                        ePregunta.addContent(eTipo);
                        ePregunta.addContent(eTexto);

                        for(int i =0; i < opciones.length;i++){
                            Element aux = new Element("opcion");
                            aux.setAttribute("id",String.valueOf(i+1));
                            aux.setText(opciones[i]);
                            ePregunta.addContent(aux);
                        }
                        ePregunta.addContent(eRespuesta);
                        ePregunta.addContent(eIntentos);
                        if(!checkMultimedia.equals("NO")){
                            Element eMultimedia = new Element("multimedia");
                            ePregunta.addContent(eMultimedia);
                        }


                        //Feedback
                        if(!checkFeedback.equals("NO")){
                            Element eInicial = new Element("inicial");
                            eInicial.setText(inicial);
                            ePregunta.addContent(eInicial);

                            Element eEvaluar = new Element("evaluar");
                            eEvaluar.setText(evaluar);
                            ePregunta.addContent(eEvaluar);

                            Element eCorrecta = new Element("correcta");
                            eCorrecta.setText(correcta);
                            ePregunta.addContent(eCorrecta);

                            Element eIncorrecta = new Element("incorrecta");
                            eIncorrecta.setText(incorrecta);
                            ePregunta.addContent(eIncorrecta);

                            Element eIntentar = new Element("intentar");
                            eIntentar.setText(intentar);
                            ePregunta.addContent(eIntentar);
                        }

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
                out.println("<h1>Servlet AltaHot at " + respuestaCorrecta.toString() + " "+id+" "+pregunta+"</h1><br>");
                for(int i =0; i< respuestaCorrecta.length; i++){
                    out.println(respuestaCorrecta[i]+"<br>");
                }
                for(int i =0; i< opciones.length; i++){
                    out.println(opciones[i]+"<br>");
                }
                out.println("</body>");
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