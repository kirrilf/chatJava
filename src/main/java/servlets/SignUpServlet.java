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

public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        if(login.isEmpty() || password1.isEmpty() || password2.isEmpty() || email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()){
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("<h1><a href = \"/signUP\">Fill in all fields </a></h1>");
            return;
        }
        if(!password1.equals(password2)){
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("<h1><a href = \"/signUP\">Passwords don't match</a></h1>");
            return;
        }

        if(AccountService.getInstance().getUserByLogin(login) != null){
            resp.getWriter().println("<h1><a href = \"/signUP\">The user with this login already registered/a></h1>");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        UserProfile userProfile = new UserProfile(login, password1, email, firstName, lastName);
        AccountService.getInstance().addNewUser(userProfile);
        resp.sendRedirect("/signIN");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionID = req.getSession().getId();
        if(AccountService.getInstance().getUserBySession(sessionID) != null){
            resp.sendRedirect("/chat");
        }

        Map<String, Object> something = new HashMap<>();//загулшка
        resp.getWriter().println(PageGenerator.getInstance().getPage("signUP.html",something));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
