package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionID = req.getSession().getId();
        if(AccountService.getInstance().getUserBySession(sessionID) != null){
            resp.sendRedirect("/chat");
        }


        Map<String, Object> something = new HashMap<>();//загулшка
        resp.getWriter().println(PageGenerator.getInstance().getPage("signIN.html",something));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(login.isEmpty() || password.isEmpty()){
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("<h1><a href = \"/signIN\">Fill in all fields </a></h1>");
            return;
        }
        UserProfile userProfile = AccountService.getInstance().getUserByLogin(login);
        if(userProfile == null){
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("<h1><a href = \"/signIN\">A user with this login does not exist</a></h1>");
            return;
        }

        if(!userProfile.getPassword().equals(password)){
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("<h1><a href = \"/signIN\">Wrong password</a></h1>");
            return;
        }


        AccountService.getInstance().addSession(req.getSession().getId(), userProfile);


        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        resp.sendRedirect("/chat");
    }
}
