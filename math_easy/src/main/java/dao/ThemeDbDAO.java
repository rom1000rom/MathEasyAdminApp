package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import connection.ConnectionBuilder;
import connection.ConnectionBuilderFactory;
import entity.*;
import exception.ThemeDaoException;
import exception.UserDaoException;




/**Класс-реализация интерфейса ThemeDAO для работы с базой данных.
@author Артемьев Р.А.
@version 05.05.2019 */
public class ThemeDbDAO implements ThemeDAO
{
	/**SQL-команда для получения всех пользователей из базы данных*/
	private static final String SELECT
    = "SELECT theme_id, theme_title, brief_theoretical_information FROM me_theme ORDER BY theme_title;";
	
	/**SQL-команда для получения всех подтем у темы по её ID*/
	private static final String SELECT_THEME_SUBTHEME
	= "SELECT subtheme_id, subtheme_title, theme_id\r\n" + 
			"FROM me_subtheme \r\n" + 
			"WHERE theme_id = ? ORDER BY subtheme_title;";
	
	/**SQL-команда для получения всех заданий у подтемы по её ID*/
	private static final String SELECT_SUBTHEME_TASK
	= "SELECT task_id, subtheme_id, description, answer\r\n" + 
			"FROM me_task \r\n" + 
			"WHERE subtheme_id = ? ORDER BY task_id;";
	
	/**Список тем*/
	private List<Theme> listTheme;
	/**Хешь-отображение списка заданий*/
	Map<Long, Task> taskMap = new HashMap<>(100);
	
	private ConnectionBuilder builder = ConnectionBuilderFactory.getConnectionBuilder();
    private Connection getConnection() throws SQLException 
    {
        return builder.getConnection();
    }
    
    public ThemeDbDAO() throws ThemeDaoException
    {
    	listTheme = findThemes();//Получаем список тем
    	findSubtheme(listTheme);//Получаем список подтем для каждой темы
    	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем  	
    }
    
    /**Метод создаёт и заполняет и возвращает экземпляр класса Theme.
    @param rs данные полученные из базы данных
    @return объект класса Theme */
    private Theme fillTheme(ResultSet rs) throws SQLException 
    {
    	Theme theme = new Theme();
    	theme.setTheme_id(rs.getLong("theme_id"));
    	theme.setTheme_title(rs.getString("theme_title"));
    	theme.setBrief_theoretical_information(rs.getString("brief_theoretical_information"));
    	
        return theme;
    }  
       
    /**Метод создаёт список экземпляров класса Theme, заполняет его данными из базы данных и возвращает.
    @return список объектов класса Theme */
    private List<Theme> findThemes() throws ThemeDaoException 
    {
        List<Theme> list = new LinkedList<>();
        try (Connection con = getConnection();
                PreparedStatement pst = con.prepareStatement(SELECT);
                ResultSet rs = pst.executeQuery()) 
        {
            while (rs.next()) 
            {
                list.add(fillTheme(rs));
            }
            rs.close();
        } 
        catch (Exception e) 
        {
            throw new ThemeDaoException(e);
        }
        return list;
    }
    
    /**Метод создаёт и заполняет и возвращает экземпляр класса Subtheme.
    @param rs данные полученные из базы данных
    @return объект класса Subtheme */
    private Subtheme fillSubtheme(ResultSet rs) throws SQLException 
    {
    	Subtheme subtheme = new Subtheme();
    	subtheme.setSubtheme_id(rs.getLong("subtheme_id"));
    	subtheme.setSubtheme_title(rs.getString("subtheme_title"));
    	subtheme.setTheme_id(rs.getLong("theme_id"));
    	
        return subtheme;
    }  
    
    /**Метод заполняет список тем данными об их подтемах.
    @param listTheme список тем*/
    private void findSubtheme(List<Theme> listTheme) throws ThemeDaoException 
    {
    	for(Theme theme : listTheme)//Проходим по списку тем
    	{
    	   List<Subtheme> listSubtheme = new LinkedList<>();
    	   
           try (Connection con = getConnection();
                PreparedStatement pst = con.prepareStatement(SELECT_THEME_SUBTHEME)) 
           {
        	   pst.setLong(1, theme.getTheme_id());
               ResultSet rs = pst.executeQuery();
               while (rs.next()) 
               {
            	   Subtheme su = fillSubtheme(rs);
            	   listSubtheme.add(su);//Добавлям подтему в список                    
               }
              rs.close(); 
              theme.setSubtheme(listSubtheme);           
           } 
           catch (Exception e) 
           {
              throw new ThemeDaoException(e);
           }
    	}
    }
    
    /**Метод создаёт и заполняет и возвращает экземпляр класса Task.
    @param rs данные полученные из базы данных
    @return объект класса Task */
    private Task fillTask(ResultSet rs) throws SQLException 
    {
    	Task task = new Task();
    	task.setTaskId(rs.getLong("task_id"));
    	task.setSubtheme_Id(rs.getLong("subtheme_id"));
    	task.setDescription(rs.getString("description"));
    	task.setAnswer(rs.getString("answer"));
    	
        return task;
    }  
    
    /**Метод заполняет список тем данными о заданиях их подтем.
    @param listTheme список тем*/
    private void findTask(List<Theme> listTheme) throws ThemeDaoException 
    {
    	for(Theme theme : listTheme)//Проходим по списку тем
    	{
    		for(Subtheme subtheme : theme.getSubtheme())//Проходим по списку подтем
        	{
    	        List<Task> listTask = new LinkedList<>();
    	   
                try (Connection con = getConnection();
                   PreparedStatement pst = con.prepareStatement(SELECT_SUBTHEME_TASK)) 
                {
        	        pst.setLong(1, subtheme.getSubtheme_id());
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) 
                    {
            	        Task t = fillTask(rs);
            	        taskMap.put(t.getTaskId(), t);            	        
            	        listTask.add(t);//Добавлям задание в список                    
                    }
                    rs.close(); 
                    subtheme.setTask(listTask);  
                }
                catch (Exception e) 
                {
                   throw new ThemeDaoException(e);
                }
           } 
           
    	}
    }
    
    /**Метод находит экземпляр класса Theme из списка по его ID(идентификационному номеру) .
    @param id идентификационный номер
    @return экземпляр класса Theme, или null, если такого нет */
    @Override
    public Theme getTheme (Long id)  
    {
    	Theme theme = null;
        for(Theme th : listTheme)
        {
        	if(th.getTheme_id() == id)
        	{
        		theme = th;
        		break;
        	}
        }
        return theme;
    }
    
    /**Получить список тем
     * @return список тем*/
	@Override
	public List<Theme> getThemeList() 
	{
		return listTheme;
	}
	
	/**Получить отображение списка заданий*/

	@Override
	public Map<Long, Task> getTaskMap() 
	{		
		return taskMap;
	}
		
	
}
