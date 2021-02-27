import Classes.Constants;
import Classes.DBLevel;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("login");
        String pass = request.getParameter("password");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;
        try {
            DataSource dataSource = DBLevel.setupDataSource();
            logger.info("create datasource");
            String roleQuery = String.format("SELECT r.NAME from users u left join Roles r on r.ID = u.ROLE where u.name = '%1$s' and u.PASSWORD = '%2$s' limit 1;", name, pass);
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery(roleQuery);
            String role = "";
            while (rset.next()) {
                role = rset.getString(Constants.NAME);
            }
            HttpSession session = request.getSession(false);
            if (role.equals(Constants.CLIENT_ROLE)) {
                session.setAttribute(Constants.DB_SOURCE, dataSource);
                session.setAttribute("role", role);
                session.setMaxInactiveInterval(-1);
                String path = "client.jsp";
                response.sendRedirect(path);
            } else if (role.equals(Constants.ADMIN_ROLE)) {
                session.setAttribute(Constants.DB_SOURCE, dataSource);
                session.setAttribute("role", role);
                session.setMaxInactiveInterval(-1);
                String path = "admin.jsp";
                response.sendRedirect(path);
            } else {
                String path = "index.jsp";
                session.invalidate();
                response.sendRedirect(path + "?login=bad");
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Sorry, something wrong!", e);
        } finally {
            try {
                if (conn != null) conn.close();
                logger.info("Close connection");
            } catch (Exception e) {
                logger.error("Close connection error", e);
            }
            try {
                if (stmt != null) stmt.close();
                logger.info("Close statement");
            } catch (Exception e) {
                logger.error("Close statement error", e);
            }
            try {
                if (rset != null) rset.close();
                logger.info("Close resultSet");
            } catch (Exception e) {
                logger.error("Close resultSet error", e);
            }
        }
    }
}
