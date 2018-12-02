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
        PrintWriter out = response.getWriter();
        
        String ruta=context.getRealPath("/")+"XML/PruebaLogin.xml";
        String usuario = request.getParameter("user");
        String contrasena= request.getParameter("pass");        
        
        HttpSession sesion=request.getSession();
        sesion.setAttribute("user",usuario);
        sesion.setAttribute("rutaXML",ruta);
        
        
        LoginBean lb = new LoginBean();
        // Checamos si hay un usuario con dicho usuario y contrasena
        String tipoUsuario = lb.validarUsuario(usuario,contrasena,ruta);
        
        if(tipoUsuario.equals("Administrador")){
        
            response.sendRedirect("/ProyectoWeb/Vistas/Opciones.html");
        
        }else if (tipoUsuario.equals("Estudiante")){
              
        }else{
        
            out.print("<script>alert('Datos Incorrectos');</script>");
            response.sendRedirect("index.html");
        
        }
    }
}
