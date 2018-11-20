package inicio;

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

public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        ServletContext context = request.getServletContext();
        response.setContentType("text/html;charset=UTF-8");

        String ruta=context.getRealPath("/")+"PruebaLogin.xml";
        String usuario = request.getParameter("usuario");
        String contrasena= request.getParameter("contrasena");        
        
        HttpSession sesion=request.getSession();
        sesion.setAttribute("user",usuario);
        sesion.setAttribute("rutaXML",ruta);
        
        
        LoginBean lb = new LoginBean();
        // Checamos si hay un usuario con dicho usuario y contrasena
        String tipoUsuario = lb.validarUsuario(usuario,contrasena,ruta);
        response.sendRedirect("Menu"+tipoUsuario);
        /*
        if(tipoUsuario != "ERROR"){
            if(tipoUsuario.equals("Administrador")) response.sendRedirect("welcome");
            else if(tipoUsuario.equals("Profesor")) response.sendRedirect("Profesor");
            else if(tipoUsuario.equals("Alumno")) response.sendRedirect("Alumno");    
        }
        else{
        	response.sendRedirect("Error");
        }*/
    }
}
