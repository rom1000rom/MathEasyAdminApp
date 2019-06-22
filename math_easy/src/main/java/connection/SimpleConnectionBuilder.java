package connection;

import config.GlobalConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**Класс служит для создания построителей соединений с базой данных.
@author Артемьев Р.А.
@version 24.04.2019 */
public class SimpleConnectionBuilder implements ConnectionBuilder
{
    public SimpleConnectionBuilder() 
    {
        try 
        {
        	//Class.forName("org.postgresql.Driver");
            Class.forName(GlobalConfig.getProperty("db.driver.class"));
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
    }
    /**Метод позволяет получить соединение с базой данных.
    @return готовое соединение с базой данных*/
    @Override
    public Connection getConnection() throws SQLException 
    {
        String url = GlobalConfig.getProperty("db.url");
        String login = GlobalConfig.getProperty("db.login");
        String password = GlobalConfig.getProperty("db.password");
        return DriverManager.getConnection(url, login, password);
    }
    
}
