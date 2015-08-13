package ru.icl.backend.servlets;

import java.io.*;
import java.util.ArrayList;
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
                out.println("<h3>Ваш ClientId: </h3>");
                out.println("<b>" + session.getId() + "</b>");
                out.println("<h3>Ваши сообщения:</h3>");
                for (Object str : list) {
                    out.println("<b>"+str+"</b>");
                }            
            }
            else out.println("<h1>Сообщения не найдены</h1>");
            
            out.println("<h3 style=\"color:blue\">Сообщения всех пользователей:</h3>");           
            out.println("<b>" + (String)getServletContext().getAttribute(BackendServlet.SESSION_MAP).toString() + "</b>");
                       
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
