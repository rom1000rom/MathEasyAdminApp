package business;

import java.util.List;

import dao.*;
import entity.User;
import exception.UserDaoException;

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
    
    /**Получить объект класса User по его ID, если такого нет, то null
     * @return объект класса User, или null*/
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
	
	/**Удалить актуальную тему у пользователя
     *  @param userId идентификационный номер пользователя
        @param themeId идентификационный номер темы
	 * @throws UserDaoException **/
	public void deleteUserTheme(Long userId, Long themeId)
	{
		 try 
		 {
			 dao.deleteUserTheme(userId, themeId);
		 } 
		 catch (UserDaoException e) 
		 {
			 e.printStackTrace();
		 }	
	}
	
	/**Добавить актуальную тему пользователю
     *  @param userId идентификационный номер пользователя
        @param themeId идентификационный номер темы
	 * @throws UserDaoException **/
	public void addUserTheme(Long userId, Long themeId, int count)
	{
		 try 
		 {
			 dao.addUserTheme(userId, themeId, count);
		 } 
		 catch (UserDaoException e) 
		 {
			 e.printStackTrace();
		 }	
	}
}
