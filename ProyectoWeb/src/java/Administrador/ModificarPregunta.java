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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author USER
 */
public class ModificarPregunta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static void deleteElement(String id, String ruta) throws IOException {

        File xmlFile = new File(ruta);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("pregunta");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                //node.getNode
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    if (element.getAttribute("id").equals(id)) {
                        Node prev = node.getPreviousSibling();
                        if (prev != null && prev.getNodeType() == Node.TEXT_NODE && prev.getNodeValue().trim().length() == 0) {
                            doc.getDocumentElement().removeChild(prev);
                        }
                        doc.getDocumentElement().removeChild(element);
                    }

                }
            }
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory;
            transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(ruta));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | TransformerException ex) {
            Logger.getLogger(EliminarPregunta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void addElementTF(Document doc, String id,  String pregunta,String respuestaCorrecta,
        String intentos,String checkMultimedia,String multimedia,String checkFeedback,String inicial,String evaluar,String correcta,String incorrecta,String intentar) {
        
        Element raiz=doc.getRootElement();
                        //Elementos normales
                        Element ePregunta=new Element("pregunta");
                        //Adentro de pregunta
                        Element eTexto = new Element("texto");
                        Element eRespuesta = new Element("respuesta");
                        Element eTipo = new Element("tipo");
                        Element eIntentos = new Element("intentos");
                        
                        ePregunta.setAttribute("id", id);
                        
                        eTexto.setText(pregunta);
                        eRespuesta.setText(respuestaCorrecta);
                        eTipo.setText("TrueFalse");
                        eIntentos.setText(intentos);
                        eMultimedia.setText(multimedia);
                        
                        ePregunta.addContent(eTipo);
                        ePregunta.addContent(eTexto);
                        ePregunta.addContent(eRespuesta);
                        ePregunta.addContent(eIntentos);
                        if(!checkMultimedia.equals("NO")){
                            Element eMultimedia = new Element("multimedia");
                            ePregunta.addContent(eMultimedia);
                        }     

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
        
    }

    private static void addElementHot(Document doc, String id, String pregunta, String[] opciones, 
        String[] respuestaCorrecta,String intentos,String checkMultimedia,String multimedia,String checkFeedback,
        String inicial,String evaluar,String correcta,String incorrecta,String intentar) {
        
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
        
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String ruta = context.getRealPath("/") + "XML/PreguntaTF.xml";

            HttpSession sesion = request.getSession();
            //sesion.setAttribute("user",usuario);
            sesion.setAttribute("rutaXML", ruta);
            String tipo =(String) sesion.getAttribute("tipo");
            
            String id = request.getParameter("IDV");
            
            deleteElement(id, ruta);

            File fichero = new File(ruta);
            if (fichero.isFile()) {
                try {
                    SAXBuilder builder = new SAXBuilder();
                    Document doc = (Document) builder.build(fichero);
                    
                    if (tipo.equals("TrueFalse")) {
                        String id = request.getParameter("ID");
                        String respuestaCorrecta= request.getParameter("Correcta");
                        String pregunta = request.getParameter("pregunta");
                        //Nuevos
                        String intentos = request.getParameter("intentos");
                        
                        String multimedia="",checkMultimedia;
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
                        addElementTF(doc, id,pregunta,respuestaCorrecta,intentos,checkMultimedia,multimedia,checkFeedback,inicial,evaluar,correcta,incorrecta,intentar);
                    } else {
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
                        String inicial,evaluar,correcta,incorrecta,intentar;
                        String checkFeedback=request.getParameter("checkFeedback");
                        if(!checkFeedback.equals("NO")){
                            inicial= request.getParameter("inicial");
                            evaluar= request.getParameter("evaluar");
                            correcta= request.getParameter("correcta");
                            incorrecta= request.getParameter("incorrecta");
                            intentar= request.getParameter("intentar");
                        }
                        addElementHot(doc, id,pregunta,opciones,respuestaCorrecta);
                    }
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    xmlOutput.output(doc, new FileWriter(ruta));
                    //System.out.println("EXITO ");
                } catch (JDOMException ex) {
                    Logger.getLogger(AltaTF.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("error");
            }

            response.sendRedirect("/ProyectoWeb/Vistas/Administrador.html");
            // TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EliminarPregunta </title>");
            out.println("</head>");
            out.println("<body>");
            //out.println("<h1>Servlet EliminarPregunta at " + respuesta + " " + id + " " + pregunta + "</h1>");
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
