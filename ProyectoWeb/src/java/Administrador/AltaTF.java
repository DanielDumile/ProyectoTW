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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;

    //Variables globlales a utilizar
    String id = "";
    String respuestaCorrecta = "";
    String pregunta = "";
    //Nuevos
    String multimedia="TF.png";
    String intentos = "";
    String inicial = "NO", evaluar = "", correcta = "", incorrecta = "", intentar = "";

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
                multimedia=fileName;
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
                switch(fieldName){
                    case "ID":{
                        id=fieldValue;
                        break;
                    }
                    case "Correcta":{
                        respuestaCorrecta=fieldValue;
                        break;
                    }
                    case "pregunta":{
                        pregunta = fieldValue;
                        break;
                    }
                    case "intentos":{
                        intentos = fieldValue;
                        break;
                    }
                    case "inicial":{
                        inicial = fieldValue;
                        break;
                    }
                    case "evaluar":{
                        evaluar = fieldValue;
                        break;
                    }
                    case "correcta":{
                        correcta = fieldValue;
                        break;
                    }
                    case "incorrecta":{
                        incorrecta = fieldValue;
                        break;
                    }
                    case "intentar":{
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
                getValores(fileItems);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            String ruta = context.getRealPath("/") + "XML/PreguntaTF.xml";

            HttpSession sesion = request.getSession();
            sesion.setAttribute("rutaXML", ruta);

            ValidacionId obj = new ValidacionId();
            if (!obj.validar(id, ruta)) {
                response.sendRedirect("/ProyectoWeb/Vistas/Administrador.html");
            } else {
                File fichero = new File(ruta);
                if (fichero.isFile()) {
                    try {
                        SAXBuilder builder = new SAXBuilder();
                        Document doc = (Document) builder.build(fichero);

                        Element raiz = doc.getRootElement();
                        //Elementos normales
                        Element ePregunta = new Element("pregunta");
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

                        ePregunta.addContent(eTipo);
                        ePregunta.addContent(eTexto);
                        ePregunta.addContent(eRespuesta);
                        ePregunta.addContent(eIntentos);
                        
                        Element eMultimedia = new Element("multimedia");
                        eMultimedia.setText(multimedia);
                        ePregunta.addContent(eMultimedia);

                        if (!inicial.equals("NO")) {
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
                } else {
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
                out.println("<h1>Servlet AltaTF at " + respuestaCorrecta + " " + id + " " + pregunta + "</h1>");
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
