package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import dao.*;
import entity.Subtheme;
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
	
	/**Добавить тему.
     *  @param title название темы
     *  @param briefTheoreticalInformation краткая теоретическая справка о теме
     *  @return объект, представляющий тему**/	
	public Theme addTheme(String title, String briefTheoreticalInformation) 
	{	
		Theme t = null;
		try 
		{
			 t = dao.addTheme(title, briefTheoreticalInformation);		 
		} 
		catch (ThemeDaoException e) 
		{			 
			 e.printStackTrace();
		}
		finally
		{
			return t;
		}	
	}
	
	/**Добавить тему с указанием её ID.
	 *  @param id идентификационный номер темы
     *  @param title название темы
     *  @param briefTheoreticalInformation краткая теоретическая справка о теме
     *  @return объект, представляющий тему**/
	public Theme addTheme(Long id, String title, String briefTheoreticalInformation) 
	{	
		Theme t = null;
		try 
		{
			 t = dao.addTheme(id, title, briefTheoreticalInformation);		 
		} 
		catch (ThemeDaoException e) 
		{			 
			 e.printStackTrace();
		}
		finally
		{
			return t;
		}	
	}
	
	
	/**Добавить подтему.
     *  @param title название подтемы
     *  @param themeId идентификационный номер темы
     *  @return объект, представляющий подтему**/	
	public Subtheme addSubtheme(String title, Long themeId) 
	{	
		Subtheme t = null;
		try 
		{
			 t = dao.addSubtheme(title, themeId);		 
		} 
		catch (ThemeDaoException e) 
		{			 
			 e.printStackTrace();
		}
		finally
		{
			return t;
		}
		
	}
	
	/**Добавить задание.
     *  @param description описание задания
     *  @param answer ответ на задание
     *  @param idSubtheme идентификационный номер подтемы
     *  @return объект, представляющий задание**/	
	public Task addTask(String description, String answer, Long idSubtheme)   
	{	
		Task t = null;
		try 
		{
			 t = dao.addTask(description, answer, idSubtheme);	 
		} 
		catch (ThemeDaoException e) 
		{			 
			 e.printStackTrace();
		}
		finally
		{
			return t;
		}
		
	}
	
	/**Изменить тему.
	 * @param newTitle новое название темы
     * @param newBriefTheoreticalInformationn новая краткая теоретическая справка о теме
     * @param themeId идентификационный номер темы
     * @return объект, представляющий тему**/	
    public Theme updateTheme(String newTitle, String newBriefTheoreticalInformation, Long themeId) 
    {	
		Theme t = null;
		try 
		{
			 t = dao.updateTheme(newTitle, newBriefTheoreticalInformation, themeId);		 
		} 
		catch (ThemeDaoException e) 
		{			 
			 e.printStackTrace();
		}
		finally
		{
			return t;
		}		
	}	
    
    /**Изменить подтему.
	 * @param newTitle новое название подтемы    
     * @param subthemeId идентификационный номер подтемы
     * @return объект, представляющий тему**/	
    public Subtheme updateSubtheme(String newTitle, Long subthemeId) 
    {	
		Subtheme t = null;
		try 
		{
			 t = dao.updateSubtheme(newTitle, subthemeId);		 
		} 
		catch (ThemeDaoException e) 
		{			 
			 e.printStackTrace();
		}
		finally
		{
			return t;
		}		
	}
    
    /**Изменить задание.
	 * @param newDescription новое описание задания    
     * @param newAnswer новы ответ на задание
     * @param taskId идентификационный номер задания
     * @return объект, представляющий задание**/	
    public Task updateTask(String newDescription, String newAnswer, Long taskId)  		
	{
    	Task t = null;
		try 
		{
			 t = dao.updateTask(newDescription, newAnswer, taskId);
		} 
		catch (ThemeDaoException e) 
		{			 
			 e.printStackTrace();
		}
		finally
		{
			return t;
		}
	}
	
}
