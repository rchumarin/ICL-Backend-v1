package ru.icl.backend.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "ShowMsg", urlPatterns = {"/ShowMsg"})
public class ShowMsg extends HttpServlet {
      
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet: Show All Messages</title>");
            out.println("</head>");
            out.println("<body>");

            HttpSession session = request.getSession(true);
            
            Object attr = session.getAttribute("messages");

            if (attr instanceof ArrayList){
                ArrayList list = (ArrayList) attr;
                out.println("<b style=\"color:blue\">Ваши сообщения</b>");
                out.println("<br>");
                out.println("<b>" + session.getId() + ":</b>");
                out.println("<br>");
                for (Object str : list) {
                    out.println("<li>"+str+"</li>");
                }
                out.println("<br>");
            }
            else { 
                out.println("<b style=\"color:red\">Сообщения не найдены</b>");
                out.println("<br>");
            }
            
            out.println("<b style=\"color:red\">Сообщения других пользователей</b>");  
            out.println("<br>");
                        
            HashMap<String, List> strMap = (HashMap<String,List>)getServletContext().getAttribute(BackendServlet.SESSION_MAP);                
                        
            // сообщения других клиентов       
            for (Map.Entry<String, List> entry : strMap.entrySet()) {
                String sessionId = entry.getKey();
                List listMsg = entry.getValue();
            
                //сообщения текущего пользователя пропускаются 
                if (sessionId.equals(session.getId())) continue;
                
                out.println("<b>" + sessionId +": </b>");
                //out.println("<br>");
                for (Object str : listMsg) out.println("<li>" + str + "</li>");
                
            }
            
            out.println("</body>");
            out.println("</html>");
            
        }    
}    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
