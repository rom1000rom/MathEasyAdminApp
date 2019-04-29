package connection;

import config.GlobalConfig;

/**Класс служит фабрикой для создания экземпляров класса, реализующего интерфейс 
 * ConnectionBuilder и приведённых к его функциональности.
@author Артемьев Р.А.
@version 24.04.2019 */
public class ConnectionBuilderFactory 
{   
	/**Метод позволяет получить экземпляр класса, реализующего интерфейс 
 * ConnectionBuilder и приведённых к его функциональности.
    @return экземпляр ConnectionBuilder*/
    public static ConnectionBuilder getConnectionBuilder() 
    {
        try 
        {
        	//Class dao = Class.forName("connection.SimpleConnectionBuilder");
            Class dao = Class.forName(GlobalConfig.getProperty("dao.connection.class"));
            return (ConnectionBuilder)dao.newInstance();
        } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) 
        {
            ex.printStackTrace();
        }
        return null;
    }
}
