package business;

import java.util.List;
import java.util.Map;

import dao.*;
import entity.Task;
import entity.Theme;
import entity.User;
import exception.UserBusinessException;

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
}
