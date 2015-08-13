package ru.icl.backend.servlets;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class BackendServlet extends HttpServlet {
    
    public static final String SESSION_MAP = "sessionMap";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //вводится коллекция для хранения сообщений других клиентов
        HashMap<String, List> sessionMap = (HashMap<String,List>)request.getServletContext().getAttribute(SESSION_MAP);                
        if (sessionMap==null) {
            sessionMap = new HashMap<String, List>();
        }       
        
        response.setContentType("text/html; charset=UTF-8"); //кодировка должна быть до getWriter()
        PrintWriter out = response.getWriter();                       
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Веб ЧАТ</title>");
        out.println("<link href=\"CSS/style.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        
        out.println("<body>");
        out.println("<div class=\"main\">");
        out.println("<div class=\"abzac\">Веб ЧАТ</div>");
        out.println("<div class=\"content\">");
        out.println("<div class=\"small_column\"><img src=\"Images/java.png\" width=\"119\" height=\"222\"></div>");
        out.println("<div class=\"big_column\">");
        out.println("<div>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Id</th>"); 
        out.println("<th>ClientId</th>"); 
        out.println("<th>Message</th>");
        out.println("</tr>");
                
        try {
            
            // считывание параметров
            //double one = Double.valueOf(request.getParameter("one"));
            String msg = request.getParameter("msg");
            
            //если не указан параметр msg
            if (msg.isEmpty()) throw new Exception();

            // определение или создание сессии
            HttpSession session = request.getSession(true);

            ArrayList<String> listMessages;

            // для новой сессии создаем новый список
            if (session.isNew()) {                
                listMessages = new ArrayList<String>();
            } else { // иначе получаем список из атрибутов сессии
                listMessages = (ArrayList<String>) session.getAttribute("messages");
            }

            listMessages.add(msg);            
            session.setAttribute("messages", listMessages);

        int id= 1;    
        
        // вывод сообщений текущего клиента       
        for (String mess : listMessages) {
            out.println("<tr>");
            out.println("<td>" + (id++) + "</td>");
            out.println("<td>" + session.getId() + "</td>");
            out.println("<td>" + mess + "</td>");
            out.println("</tr>");
        }
        
        sessionMap.put(session.getId(), listMessages);
        getServletContext().setAttribute(SESSION_MAP, sessionMap);
        
        // вывод сообщений других клиентов       
        for (Map.Entry<String, List> entry : sessionMap.entrySet()) {
            String sessionId = entry.getKey();
            List listMsg = entry.getValue();
            
            //сообщения текущего пользователя пропускаются 
            if (sessionId.equals(session.getId())) continue;
                       
            for (Object str : listMsg) {
                 out.println("<tr>");
                out.println("<td>" + (id++) + "</td>");
                out.println("<td style=\"color:red\">" + sessionId + "</td>");
                out.println("<td>" + str + "</td>");
                out.println("</tr>");
            }
        }
        
        out.println("</table>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class=\"footer\">© 2015 ICL. Тестовый проект</div>");
        out.println("</div>");


        } catch (Exception ex) {           
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);         
        } finally {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
            
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
