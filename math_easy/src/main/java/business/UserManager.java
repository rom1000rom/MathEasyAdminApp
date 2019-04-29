package business;

import java.util.List;

import dao.*;
import entity.User;
import exception.UserBusinessException;

/**Класс для реализации функций над списком пользователей.
@author Артемьев Р.А.
@version 27.04.2019 */
public class UserManager 
{
    private final UserDAO dao;
    
    public UserManager() 
    {
        dao = UserDAOFactory.getUserDAO();
    }
    
    /**Получить объект класса User по его ID
     * @return объект класса User*/
    public User getUser(Long contactId) 
    {   
        return dao.getUser(contactId);       
    }
    
    /**Получить список пользователей
     * @return список пользователей*/
	public List<User> getUserList() 
	{
		return dao.getUserList();
	}
}
