import Classes.Constants;
import Classes.DBLevel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>Hello World!</h3>");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("login");
        String pass = request.getParameter("password");
        try {
            DBLevel dbLevel = new DBLevel();
            String roleQuery = "SELECT r.NAME from users u left join Roles r on r.ID = u.ROLE where u.name = '" + name + "' and u.PASSWORD = '" + pass + "' limit 1;";
            Statement statement = dbLevel.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(roleQuery);
            String role = "";
            while (resultSet.next()) {
                role = resultSet.getString(Constants.NAME);
            }
            HttpSession session = request.getSession(false);
            if (role.equals(Constants.CLIENT_ROLE)) {
                session.setAttribute("role", role);
                session.setMaxInactiveInterval(-1);
                String path = "client/client.jsp";
                response.sendRedirect(path);
            } else if (role.equals(Constants.ADMIN_ROLE)) {
                session.setAttribute("role", role);
                session.setMaxInactiveInterval(-1);
                String path = "admin/admin.jsp";
                response.sendRedirect(path);
            } else {
                String path = "index.jsp";
                request.setAttribute("login", "bad");
                session.invalidate();
                response.sendRedirect(path + "?login=bad");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
