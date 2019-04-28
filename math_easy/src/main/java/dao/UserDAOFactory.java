package dao;

import config.GlobalConfig;
import connection.ConnectionBuilder;

/**Класс служит фабрикой для создания экземпляров класса, реализующего интерфейс 
 * UserDAO и приведённых к его функциональности.
@author Артемьев Р.А.
@version 27.04.2019 */
public class UserDAOFactory 
{   
	/**Метод позволяет получить экземпляр класса, реализующего интерфейс 
    UserDAO и приведённых к его функциональности.
    @return экземпляр UserDAO*/
    public static UserDAO getUserDAO() 
    {
        try 
        {
            Class dao = Class.forName(GlobalConfig.getProperty("dao.user.class"));
            return (UserDAO)dao.newInstance();
        } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) 
        {
            ex.printStackTrace();
        }
        return null;
    }
}

