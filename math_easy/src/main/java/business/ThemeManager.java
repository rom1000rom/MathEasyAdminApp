package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import dao.*;
import entity.Task;
import entity.Theme;
import entity.User;
import exception.ThemeDaoException;
import exception.UserBusinessException;
import exception.UserDaoException;

/**Класс для реализации функций над списком тем.
@author Артемьев Р.А.
@version 05.05.2019 */
public class ThemeManager 
{
    private final ThemeDAO dao;
    
    public ThemeManager() 
    {
        dao = ThemeDAOFactory.getThemeDAO();
    }
    
    /**Получить объект класса Theme по его ID, если такого нет, то null
     * @return объект класса Theme, или null*/
    public Theme getTheme(Long themeId) 
    {   
        return dao.getTheme(themeId);       
    }
    
    /**Получить список тем
     * @return список тем*/
	public List<Theme> getThemeList() 
	{
		return dao.getThemeList();
	}
	
	/**Получить отображение списка заданий*/
	public Map<Long, Task> getTaskMap() 
	{	
		return dao.getTaskMap();
	}
	
	/**Удалить задание по его идентификационному номеру
     *  @param taskId идентификационный номер задания
     *  @throws ThemeDaoException **/
	public void deleteTask(Long taskId) 
	{	
		try 
		{
			 dao.deleteTask(taskId);;
		} 
		catch (ThemeDaoException e) 
		{
			 e.printStackTrace();
		}	
	}
	
	/**Удалить тему по её идентификационному номеру
     *  @param themeId идентификационный номер темы
     *  @throws ThemeDaoException **/
	public void deleteTheme(Long themeId) 
	{	
		try 
		{
			 dao.deleteTheme(themeId);;
		} 
		catch (ThemeDaoException e) 
		{
			 e.printStackTrace();
		}	
	}
	
	/**Удалить подтему по её идентификационному номеру
     *  @param subthemeId идентификационный номер подтемы
     *  @throws ThemeDaoException **/
	public void deleteSubtheme(Long subthemeId) 
	{	
		try 
		{
			 dao.deleteSubtheme(subthemeId);;
		} 
		catch (ThemeDaoException e) 
		{
			 e.printStackTrace();
		}	
	}
}
