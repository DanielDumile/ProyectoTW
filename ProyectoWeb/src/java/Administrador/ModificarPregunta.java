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
import java.util.ArrayList;
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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.NodeList;

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
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;
    //Variables globlales a utilizar
    String idPregunta;
    String tipo;

    String respuestaTF;
    ArrayList<String> respuestaCorrecta = new ArrayList<>();
    ArrayList<String> opciones = new ArrayList<>();
    String pregunta;
    //Nuevos
    String multimedia;
    String intentos;
    String inicial, evaluar , correcta, incorrecta, intentar;
    
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

    private void addElementTF(Document doc) {
        Element raiz = doc.getRootElement();
        //Elementos normales
        Element ePregunta = new Element("pregunta");
        //Adentro de pregunta
        Element eTexto = new Element("texto");
        Element eRespuesta = new Element("respuesta");
        Element eTipo = new Element("tipo");
        Element eIntentos = new Element("intentos");

        ePregunta.setAttribute("id", idPregunta);

        eTexto.setText(pregunta);
        eRespuesta.setText(respuestaTF);
        eTipo.setText("TrueFalse");
        eIntentos.setText(intentos);

        ePregunta.addContent(eTipo);
        ePregunta.addContent(eTexto);
        ePregunta.addContent(eRespuesta);
        ePregunta.addContent(eIntentos);
        //if (!multimedia.equals("NO")) {
        Element eMultimedia = new Element("multimedia");
        eMultimedia.setText(multimedia);
        ePregunta.addContent(eMultimedia);
        //}

        //if (!inicial.equals("NO")) {
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
        //}
        raiz.addContent(ePregunta);

    }

    private void addElementHot(Document doc) {
        Element raiz = doc.getRootElement();
        Element ePregunta = new Element("pregunta");
        Element eTexto = new Element("texto");
        Element eRespuesta = new Element("respuesta");
        Element eTipo = new Element("tipo");
        Element eIntentos = new Element("intentos");

        ePregunta.setAttribute("id", idPregunta);
        eTexto.setText(pregunta);
        eTipo.setText("HotObject");
        String auxRespuesta = "";
        for (int i = 0; i < respuestaCorrecta.size(); i++) {
            auxRespuesta += respuestaCorrecta.get(i);
            if (i != respuestaCorrecta.size() - 1) {
                auxRespuesta += ',';
            }
        }
        System.out.println(auxRespuesta);
        eRespuesta.setText(auxRespuesta);

        ePregunta.addContent(eTipo);
        ePregunta.addContent(eTexto);

        for (int i = 0; i < opciones.size(); i++) {
            Element aux = new Element("opcion");
            aux.setAttribute("id", String.valueOf(i + 1));
            aux.setText(opciones.get(i));
            ePregunta.addContent(aux);
        }
        ePregunta.addContent(eRespuesta);
        eIntentos.setText(intentos);
        ePregunta.addContent(eIntentos);

        //if (!multimedia.equals("NO")) {
        Element eMultimedia = new Element("multimedia");
        eMultimedia.setText(multimedia);
        ePregunta.addContent(eMultimedia);
        //}

        //Feedback
        //if (!inicial.equals("NO")) {
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
        //}

        raiz.addContent(ePregunta);

    }

    public void getValores(List fileItems) throws Exception {
        Iterator i = fileItems.iterator();

        while (i.hasNext()) {
            FileItem fi = (FileItem) i.next();
            if (!fi.isFormField()) {
                // Get the uploaded file parameters
                String fieldName = fi.getFieldName();
                //out.println("fieldName: " + fieldName + "<br />");
                String fileName = fi.getName();
                //out.println("fileName: " + fileName + "<br />");
                String contentType = fi.getContentType();
                //out.println("contentType: " + contentType + "<br />");
                boolean isInMemory = fi.isInMemory();
                long sizeInBytes = fi.getSize();
                if (fieldName.equals("opciones")) {
                    opciones.add(fileName);
                } else {
                    multimedia = fileName;
                }

                // Write the file
                if (fileName.lastIndexOf("\\") >= 0) {
                    file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                } else {
                    file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                }
                fi.write(file);
                //out.println("Archivo subido: " + fileName + "<br />");

            } else {
                String fieldName = fi.getFieldName();
                String fieldValue = fi.getString();
                switch (fieldName) {
                    case "IDV": {
                        idPregunta = fieldValue;
                        break;
                    }
                    case "Correcta": {
                        //respuestaCorrecta=fieldValue;
                        if (tipo.equals("TrueFalse")) {
                            respuestaTF = fieldValue;
                        } else {
                            respuestaCorrecta.add(fieldValue);
                        }
                        break;
                    }
                    case "pregunta": {
                        pregunta = fieldValue;
                        break;
                    }
                    case "intentos": {
                        intentos = fieldValue;
                        break;
                    }
                    case "inicial": {
                        inicial = fieldValue;
                        break;
                    }
                    case "evaluar": {
                        evaluar = fieldValue;
                        break;
                    }
                    case "correcta": {
                        correcta = fieldValue;
                        break;
                    }
                    case "incorrecta": {
                        incorrecta = fieldValue;
                        break;
                    }
                    case "intentar": {
                        intentar = fieldValue;
                        break;
                    }
                }
                //out.println("Archivo subido: " + fieldName + " cosas " + fieldValue + "<br />");
            }
        }
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
            tipo="";
            tipo = (String) sesion.getAttribute("tipo");
            if (tipo.equals("TrueFalse")) {
                multimedia = "TF.png";
            } else {
                multimedia = "Hot.png";
            }

            // Check that we have a file upload request
            filePath = request.getRealPath("/");
            isMultipart = ServletFileUpload.isMultipartContent(request);

            if (!isMultipart) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet upload</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<p>No se subio el archivo</p>");
                out.println("</body>");
                out.println("</html>");
                return;
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();

            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                // Parse the request to get file items.
                List fileItems = upload.parseRequest(request);
                //request.getParameter("file")
                // Process the uploaded file items
                idPregunta = "";
                pregunta = "";
                intentos = "";
                respuestaTF = "";
                inicial = "Mensaje Inicial";
                evaluar = "Momento de saber tu calificacion";
                correcta = "Respuesta Correcta";
                incorrecta = "Trata de nuevo";
                intentar = "Buen intento";
                //multimedia = "Hot.png";
                respuestaCorrecta.clear();
                opciones.clear();
                getValores(fileItems);
            } catch (Exception ex) {
                System.out.println(ex);
            }

            deleteElement(idPregunta, ruta);

            File fichero = new File(ruta);
            if (fichero.isFile()) {
                try {
                    SAXBuilder builder = new SAXBuilder();
                    Document doc = (Document) builder.build(fichero);

                    if (tipo.equals("TrueFalse")) {
                        addElementTF(doc);
                    } else {
                        addElementHot(doc);
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
