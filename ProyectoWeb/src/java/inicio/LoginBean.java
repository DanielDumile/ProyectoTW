package inicio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

class LoginBean
{
    
    String nombre="";
    String pass="";
    String tipoUsuario="";

    /**
     * Constructor for LoginBean
     * Initializes the list of usernames/passwords
     */
    public LoginBean()
    {
        
    }

    /**
     * determine if the username/password combination are
     * present in the validUsers repository.
     * @param usuario
     * @param contrasena
     * return String with user type
     */
    public String validarUsuario (String usuario, String contrasena, String ruta) throws IOException
    {
        //HttpSession sesion=request.getSession();
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta);
        try{//Se crea el documento a traves del archivo  
            Document document = (Document) builder.build( xmlFile );
            //Se obtiene la raiz 
            Element rootNode = document.getRootElement();
            //Se obtiene la lista de hijos de la raiz 'usuarios'
            List list = rootNode.getChildren( "user" );
            for ( int i = 0; i < list.size(); i++ )
            {
               //Se obtiene el elemento 'user1'
                Element campo = (Element) list.get(i);
                //Se obtiene el valor que esta entre los tags
                nombre = campo.getChildTextTrim("nombre");  
                pass = campo.getChildTextTrim("password");
                if(pass.equals(contrasena)){
                    //Si la contrasena fue correcta, retorna el tipo de usuario.
                    tipoUsuario= campo.getChildTextTrim("tipo");
                    return tipoUsuario;    
                }
            }  
        }catch ( JDOMException io ) {
            System.out.println(io.getMessage());
        }
        return "ERROR";
    }

}
