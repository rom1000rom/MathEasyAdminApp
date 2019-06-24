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
	/**SQL-команда для получения всех тем из базы данных*/
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
	
	/**SQL-команда для удаления задания*/
	private static final String DELETE_TASK
    = "DELETE FROM me_task WHERE task_id=?;";
	
	/**SQL-команда для удаления темы*/
	private static final String DELETE_THEME
    = "DELETE FROM me_theme WHERE theme_id=?;";
	
	/**SQL-команда для удаления подтемы*/
	private static final String DELETE_SUBTHEME
    = "DELETE FROM me_subtheme WHERE subtheme_id=?;";
	
	/**SQL-команда для добавления темы*/
	private static final String ADD_THEME
    = "INSERT INTO me_theme( theme_title, brief_theoretical_information )\r\n" + 
    		"VALUES ( ?, ?);";
	
	/**SQL-команда для добавления темы с указанием её ID*/
	private static final String ADD_THEME_ID
    = "INSERT INTO me_theme(theme_id, theme_title, brief_theoretical_information )\r\n" + 
    		"VALUES (?, ?, ?);";
	
	/**SQL-команда для добавления подтемы*/
	private static final String ADD_SUBTHEME
    = "INSERT INTO me_subtheme( subtheme_title, theme_id )\r\n" + 
    		"VALUES ( ?, ?);";
	
	/**SQL-команда для добавления задания*/
	private static final String ADD_TASK
    = "INSERT INTO me_task( subtheme_id, description, answer )\r\n" + 
    		"VALUES (? , ? , ?);";
	
	/**SQL-команда для обновления темы*/
	private static final String UPDATE_THEME
    = "UPDATE me_theme \r\n" + 
    		"SET theme_title = ?,\r\n" + 
    		"brief_theoretical_information = ?\r\n" + 
    		"WHERE theme_id = ?;";
	
	/**SQL-команда для обновления подтемы*/
	private static final String UPDATE_SUBTHEME
    = "UPDATE me_subtheme \r\n" + 
    		"SET subtheme_title = ?\r\n" +    		
    		"WHERE subtheme_id = ?;";
	
	/**SQL-команда для обновления задания*/
	private static final String UPDATE_TASK
    = "UPDATE me_task \r\n" + 
    		"SET description = ?,\r\n" + 
    		"answer = ?\r\n" + 
    		"WHERE task_id = ?;";
	
	/**Список тем.*/
	private List<Theme> listTheme;
	/**Хешь-отображение списка заданий.*/
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

	/**Удалить задание по его идентификационному номеру
     *  @param taskId идентификационный номер задания**/
	@Override
	public void deleteTask(Long taskId) throws ThemeDaoException 
	{	
	        try (Connection con = getConnection();
	             PreparedStatement pst = con.prepareStatement(DELETE_TASK)) 
	        {
	            pst.setLong(1, taskId);	            
	            pst.executeUpdate();
	        } 
	        catch (Exception e) 
	        {
	            throw new ThemeDaoException(e);
	        }
	        findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем 
	}
		
	/**Удалить тему по её идентификационному номеру
     *  @param themeId идентификационный номер темы**/
	@Override
	public void deleteTheme(Long themeId) throws ThemeDaoException 
	{	
	        try (Connection con = getConnection();
	             PreparedStatement pst = con.prepareStatement(DELETE_THEME)) 
	        {
	            pst.setLong(1, themeId);	            
	            pst.executeUpdate();
	        } 
	        catch (Exception e) 
	        {
	            throw new ThemeDaoException(e);
	        }
	        listTheme = findThemes();//Получаем список тем
	    	findSubtheme(listTheme);//Получаем список подтем для каждой темы
	    	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем 
	}
	
	/**Удалить подтему по её идентификационному номеру
     *  @param subthemeId идентификационный номер подтемы**/
	@Override
	public void deleteSubtheme(Long subthemeId) throws ThemeDaoException 
	{	
	        try (Connection con = getConnection();
	             PreparedStatement pst = con.prepareStatement(DELETE_SUBTHEME)) 
	        {
	            pst.setLong(1, subthemeId);	            
	            pst.executeUpdate();
	        } 
	        catch (Exception e) 
	        {
	            throw new ThemeDaoException(e);
	        }	        
	    	findSubtheme(listTheme);//Получаем список подтем для каждой темы
	    	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем 
	}
	
	/**Добавить тему.
     *  @param title название темы
     *  @param briefTheoreticalInformation краткая теоретическая справка о теме
     *  @return объект, представляющий тему**/
	@Override
	public Theme addTheme(String title, String briefTheoreticalInformation) throws ThemeDaoException 
	{
		try (Connection con = getConnection();
	             PreparedStatement pst = con.prepareStatement(ADD_THEME)) 
	        {
	            pst.setString(1, title);	
	            pst.setString(2, briefTheoreticalInformation);	
	            pst.executeUpdate();
	        } 
	        catch (Exception e) 
	        {
	            throw new ThemeDaoException(e);
	        }	        
		listTheme = findThemes();//Получаем список тем
    	findSubtheme(listTheme);//Получаем список подтем для каждой темы
    	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем
    	Theme theme = null;
    	for(Theme t : listTheme)
    	{
    		if(t.getTheme_title().equals(title))
    		{
    			theme = t;
    			break;
    		}
    	}
		return theme;
	}

	/**Добавить тему с указанием её ID.
	 *  @param id идентификационный номер темы
     *  @param title название темы
     *  @param briefTheoreticalInformation краткая теоретическая справка о теме
     *  @return объект, представляющий тему**/
	@Override
	public Theme addTheme(Long id, String title, String briefTheoreticalInformation) throws ThemeDaoException 
	{
		try (Connection con = getConnection();
	             PreparedStatement pst = con.prepareStatement(ADD_THEME_ID)) 
	        {
			    pst.setLong(1, id);
	            pst.setString(2, title);	
	            pst.setString(3, briefTheoreticalInformation);	
	            pst.executeUpdate();
	        } 
	        catch (Exception e) 
	        {
	            throw new ThemeDaoException(e);
	        }	        
		listTheme = findThemes();//Получаем список тем
    	findSubtheme(listTheme);//Получаем список подтем для каждой темы
    	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем
    	Theme theme = null;
    	for(Theme t : listTheme)
    	{
    		if(t.getTheme_title().equals(title))
    		{
    			theme = t;
    			break;
    		}
    	}
		return theme;
	}
	
	/**Добавить подтему.
     *  @param title название подтемы
     *  @param themeId идентификационный номер темы
     *  @return объект, представляющий подтему**/
	@Override
	public Subtheme addSubtheme(String title, Long themeId) throws ThemeDaoException 
	{
		try (Connection con = getConnection();
	             PreparedStatement pst = con.prepareStatement(ADD_SUBTHEME)) 
	        {
	            pst.setString(1, title);	
	            pst.setLong(2, themeId);	
	            pst.executeUpdate();
	        } 
	        catch (Exception e) 
	        {
	            throw new ThemeDaoException(e);
	        }	        		
    	findSubtheme(listTheme);//Получаем список подтем для каждой темы
    	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем
    	Subtheme subtheme = null;
    	for(Theme t : listTheme)
    	{
    		if(t.getTheme_id().equals(themeId))
    		{
    			for(Subtheme s : t.getSubtheme())
    			{
    				if(s.getSubtheme_title().equals(title))
    	    		{
    	    			subtheme = s;
    	    			break;
    	    		}
    			}
    		}
    	}
		return subtheme;
	}

	/**Добавить задание.
     *  @param description описание задания
     *  @param answer ответ на задание
     *  @param idSubtheme идентификационный номер подтемы
     *  @return объект, представляющий задание**/
	@Override
	public Task addTask(String description, String answer, Long idSubtheme) throws ThemeDaoException 
	{
		try (Connection con = getConnection();
	             PreparedStatement pst = con.prepareStatement(ADD_TASK)) 
	        {	            	
	            pst.setLong(1, idSubtheme);
	            pst.setString(2, description);
	            pst.setString(3, answer);
	            pst.executeUpdate();
	        } 
	        catch (Exception e) 
	        {
	            throw new ThemeDaoException(e);
	        }	        		 	
   	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем
   	Task task = null;
   	for(Theme t : listTheme)
   	{ 		
   		for(Subtheme s : t.getSubtheme())
   		{
   			if(s.getSubtheme_id().equals(idSubtheme))
   	    	{
   				for(Task ta : s.getTask())
   		   		{
   					//Ищем совпадение по описанию и ответу
   		   			if(ta.getAnswer().equals(answer) && (ta.getDescription().equals(description)))
   		   	    	{
   		   	    		task = ta;
   		   	    		break;
   		   	    	}
   		   		}  	
   	    	}
   		}
   		
   	}
		return task;
	}
	
	/**Изменить тему.
	 * @param title новое название темы
     * @param briefTheoreticalInformation новая краткая теоретическая справка о теме
     * @param themeId идентификационный номер темы
     * @return объект, представляющий тему**/
	@Override
    public Theme updateTheme(String newTitle, String newBriefTheoreticalInformation, Long themeId) 
    		throws ThemeDaoException
    		{
    			try (Connection con = getConnection();
    		             PreparedStatement pst = con.prepareStatement(UPDATE_THEME)) 
    		        {   		           	
    		            pst.setString(1, newTitle);	
    		            pst.setString(2, newBriefTheoreticalInformation);
    		            pst.setLong(3, themeId);
    		            pst.executeUpdate();
    		        } 
    		        catch (Exception e) 
    		        {
    		            throw new ThemeDaoException(e);
    		        }	        
    			listTheme = findThemes();//Получаем список тем
    	    	findSubtheme(listTheme);//Получаем список подтем для каждой темы
    	    	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем
    	    	Theme theme = null;
    	    	for(Theme t : listTheme)
    	    	{
    	    		if(t.getTheme_title().equals(newTitle))
    	    		{
    	    			theme = t;
    	    			break;
    	    		}
    	    	}
    			return theme;
    		}
	
	/**Изменить подтему.
	 * @param title новое название подтемы    
     * @param themeId идентификационный номер подтемы
     * @return объект, представляющий тему**/
	@Override
    public Subtheme updateSubtheme(String newTitle, Long subthemeId) 
    		throws ThemeDaoException
    		{
    			try (Connection con = getConnection();
    		             PreparedStatement pst = con.prepareStatement(UPDATE_SUBTHEME)) 
    		        {   		           	
    		            pst.setString(1, newTitle);	   		           
    		            pst.setLong(2, subthemeId);
    		            pst.executeUpdate();
    		        } 
    		        catch (Exception e) 
    		        {
    		            throw new ThemeDaoException(e);
    		        }	           			
    	    	findSubtheme(listTheme);//Получаем список подтем для каждой темы
    	    	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем
    	    	
    	    	Subtheme subtheme = null;
    	    	for(Theme t : listTheme)
    	    	{
    	    			for(Subtheme s : t.getSubtheme())
    	    			{
    	    				if(s.getSubtheme_title().equals(newTitle))
    	    	    		{
    	    	    			subtheme = s;
    	    	    			break;
    	    	    		}
    	    			}
    	    	}   	    	
    			return subtheme;
    		}
	
	/**Изменить задание.
	 * @param newDescription новое описание задания    
     * @param newAnswer новы ответ на задание
     * @param taskId идентификационный номер задания
     * @return объект, представляющий задание
	 * @throws ThemeDaoException **/
	@Override
    public Task updateTask(String newDescription, String newAnswer, Long taskId) 
    		throws ThemeDaoException  		
	{
		try (Connection con = getConnection();
	             PreparedStatement pst = con.prepareStatement(UPDATE_TASK)) 
	        {   		           	
	            pst.setString(1, newDescription);	   		           
	            pst.setString(2, newAnswer);
	            pst.setLong(3, taskId);
	            pst.executeUpdate();
	        } 
	        catch (Exception e) 
	        {
	            throw new ThemeDaoException(e);
	        }	           			   	
    	findTask(listTheme);//Получаем список заданий для каждой подтемы списка тем
    	   	
		return taskMap.get(taskId);
	}
}
