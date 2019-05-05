package dao;

import config.GlobalConfig;
import connection.ConnectionBuilder;

/**Класс служит фабрикой для создания экземпляров класса, реализующего интерфейс 
 * ThemeDAO и приведённых к его функциональности.
@author Артемьев Р.А.
@version 05.05.2019 */
public class ThemeDAOFactory 
{   
	/**Метод позволяет получить экземпляр класса, реализующего интерфейс 
    ThemeDAO и приведённых к его функциональности.
    @return экземпляр ThemeDAO*/
    public static ThemeDAO getThemeDAO() 
    {
        try 
        {
            Class dao = Class.forName(GlobalConfig.getProperty("dao.theme.class"));
            return (ThemeDAO)dao.newInstance();
        } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) 
        {
            ex.printStackTrace();
        }
        return null;
    }
}

