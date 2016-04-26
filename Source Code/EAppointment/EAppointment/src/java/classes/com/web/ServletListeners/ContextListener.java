package classes.com.web.ServletListeners;


import classes.com.model.Database.DbConnector;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContext;


/**
 * Web application Listener to initialize database objects.
 * @author Ekta Khiani
 * @date 03/16/2016
 * 
 */
public class ContextListener implements ServletContextListener {

    @Override
    /**In this method, connection to the database is establised.
     * 
     */
    public void contextInitialized(ServletContextEvent sce) 
    {
            
        ServletContext sc = sce.getServletContext();
        String strDbURL = sc.getInitParameter("dbURL");
        String strDbUserName = sc.getInitParameter("dbUserName");
        String strPassword = sc.getInitParameter("dbPassword");
  
        DbConnector.createConnection(strDbURL, strDbUserName, strPassword);
    } 
            

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DbConnector.closeConnection();
    }
}
