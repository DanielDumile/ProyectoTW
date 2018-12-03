/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrador;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author USER
 */
public class ValidacionId {
    
    public boolean validar(String id,String ruta) throws IOException{
        //HttpSession sesion=request.getSession();
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta);
        try{//Se crea el documento a traves del archivo  
            Document document = (Document) builder.build( xmlFile );
            //Se obtiene la raiz 
            Element rootNode = document.getRootElement();
            //Se obtiene la lista de hijos de la raiz 'usuarios'
            List list = rootNode.getChildren( "pregunta" );
            for ( int i = 0; i < list.size(); i++ )
            {
               //Se obtiene el elemento 'user1'
                Element campo = (Element) list.get(i);
                //Se obtiene el valor que esta entre los tags
                if(id.equals(campo.getAttributeValue("id"))){
                    return false;
                }
            }  
        }catch ( JDOMException io ) {
            System.out.println(io.getMessage());
        }
        return true;
    }
    
    public boolean validarExamen(String id,String ruta) throws IOException{
        //HttpSession sesion=request.getSession();
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta);
        try{//Se crea el documento a traves del archivo  
            Document document = (Document) builder.build( xmlFile );
            //Se obtiene la raiz 
            Element rootNode = document.getRootElement();
            //Se obtiene la lista de hijos de la raiz 'usuarios'
            List list = rootNode.getChildren( "examen" );
            for ( int i = 0; i < list.size(); i++ )
            {
               //Se obtiene el elemento 'user1'
                Element campo = (Element) list.get(i);
                //Se obtiene el valor que esta entre los tags
                if(id.equals(campo.getAttributeValue("id"))){
                    return false;
                }
            }  
        }catch ( JDOMException io ) {
            System.out.println(io.getMessage());
        }
        return true;
    }
}
