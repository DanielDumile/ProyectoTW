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

    private static void addElementTF(Document doc, String id) {
        /*
        Element raiz = doc.getRootElement();
        Element ePregunta = new Element("pregunta");
        Element eTexto = new Element("texto");
        Element eRespuesta = new Element("respuesta");
        Element eTipo = new Element("tipo");
        ePregunta.setAttribute("id", id);
        eTexto.setText(pregunta);
        eRespuesta.setText(respuestaCorrecta);
        eTipo.setText("TrueFalse");
        ePregunta.addContent(eTipo);
        ePregunta.addContent(eTexto);
        ePregunta.addContent(eRespuesta);
        raiz.addContent(ePregunta);
        */
    }

    private static void addElementHot(Document doc, String id) {
        /*
        Element raiz = doc.getRootElement();
        Element ePregunta = new Element("pregunta");
        Element eTexto = new Element("texto");
        Element eRespuesta = new Element("respuesta");
        Element eTipo = new Element("tipo");
        ePregunta.setAttribute("id", id);
        eTexto.setText(pregunta);
        eTipo.setText("HotObject");
        String auxRespuesta = "";
        for (int i = 0; i < respuestaCorrecta.length; i++) {
            auxRespuesta += respuestaCorrecta[i];
            if (i != respuestaCorrecta.length - 1) {
                auxRespuesta += ',';
            }
        }
        System.out.println(auxRespuesta);
        eRespuesta.setText(auxRespuesta);

        ePregunta.addContent(eTipo);
        ePregunta.addContent(eTexto);

        for (int i = 0; i < opciones.length; i++) {
            Element aux = new Element("opcion");
            aux.setAttribute("id", String.valueOf(i + 1));
            aux.setText(opciones[i]);
            ePregunta.addContent(aux);
        }
        ePregunta.addContent(eRespuesta);

        raiz.addContent(ePregunta);
        */
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
            String id = request.getParameter("id");
            String tipo = request.getParameter("tipo");

            deleteElement(id, ruta);

            File fichero = new File(ruta);
            if (fichero.isFile()) {
                try {
                    SAXBuilder builder = new SAXBuilder();
                    Document doc = (Document) builder.build(fichero);

                    if (tipo.equals("TrueFalse")) {
                        addElementTF(doc, id);
                    } else {
                        addElementHot(doc, id);
                    }
                    /*
                    
                     */
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
