package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import connection.ConnectionBuilder;
import connection.ConnectionBuilderFactory;
import entity.User;
import entity.UserInput;
import entity.UserTheme;
import exception.UserDaoException;



/**Класс-реализация интерфейса UserDAO для работы с базой данных.
@author Артемьев Р.А.
@version 27.04.2019 */
public class UserDbDAO implements UserDAO
{
	/**SQL-команда для получения всех пользователей из базы данных*/
	private static final String SELECT
    = "SELECT user_id, first_name, last_name, school_class, school_number, date_of_registration FROM me_user ORDER BY last_name;";
	
	/**SQL-команда для получения всех тем у пользователя по его ID*/
	private static final String SELECT_USER_THEME
	= "SELECT aa.theme_id, aa.theme_title, a.solve_task, a.actual, a.last_solved_task\r\n" + "FROM( \r\n" +  
			"  SELECT theme_id, solve_task, actual, last_solved_task\r\n" + 
			"  FROM me_user_theme\r\n" + 
			"  WHERE user_id = ?\r\n" + 
			") AS a \r\n" + 
			"JOIN me_theme AS aa ON a.theme_id = aa.theme_id;";
	
	/**SQL-команда для получения всех входов в программу у пользователя по его ID*/
	private static final String SELECT_USER_INPUT
	= "SELECT input_date, tasks_solved_correctly, tasks_solved_incorrectly\r\n" + 
			"FROM me_input_user \r\n" + 
			"WHERE user_id = ? ORDER BY input_date;";
	
	/**Список пользователей*/
	private List<User> listUser;
	
	
	private ConnectionBuilder builder = ConnectionBuilderFactory.getConnectionBuilder();
    private Connection getConnection() throws SQLException 
    {
        return builder.getConnection();
    }
    
    public UserDbDAO() throws UserDaoException
    {
    	listUser = findUsers();//Получаем список пользователей
    	findUsersTheme(listUser);//Получаем для каждого пользователя список текущих и пройденных тем
    	findUsersInput(listUser);//Получаем для каждого пользователя список его входов в программу
    }
    
    /**Метод создаёт и заполняет и возвращает экземпляр класса User.
    @param rs данные полученные из базы данных
    @return объект класса User */
    private User fillUser(ResultSet rs) throws SQLException 
    {
    	User user = new User();
    	user.setUserId(rs.getLong("user_id"));
    	user.setFirstName(rs.getString("first_name"));
    	user.setLastName(rs.getString("last_name"));
    	user.setSchoolClass(rs.getInt("school_class"));
    	user.setSchoolNumber(rs.getInt("school_number"));
    	user.setDateOfRegistration(rs.getString("date_of_registration"));
        return user;
    }
    
    /**Метод создаёт и заполняет и возвращает экземпляр класса UserTheme.
    @param rs данные полученные из базы данных
    @return объект класса UserTheme */
    private UserTheme fillUserTheme(ResultSet rs) throws SQLException 
    {
    	UserTheme userTheme = new UserTheme();
    	userTheme.setTheme_id(rs.getLong("theme_id"));
    	userTheme.setTheme_title(rs.getString("theme_title"));
    	userTheme.setSolve_task(rs.getInt("solve_task"));
    	userTheme.setActual(rs.getBoolean("actual"));
    	userTheme.setLast_solved_task(rs.getString("last_solved_task"));
        return userTheme;
    }
    
    /**Метод создаёт и заполняет и возвращает экземпляр класса UserInput.
    @param rs данные полученные из базы данных
    @return объект класса UserInput */
    private UserInput fillUserInput(ResultSet rs) throws SQLException 
    {
    	UserInput userInput = new UserInput();
    	userInput.setInputDate(rs.getString("input_date"));
    	Array arrayCor = rs.getArray("tasks_solved_correctly");//Получаю массив данных
    	if(arrayCor != null)
    	{
    		userInput.setTasksSolvedCorrectly((Integer[])arrayCor.getArray());  	    
    	}		
    	
    	Array arrayInCor = rs.getArray("tasks_solved_incorrectly");//Получаю массив данных
    	if(arrayInCor != null)
    	{
    		userInput.setTasksSolvedInCorrectly((Integer[])arrayInCor.getArray());  	    
    	}	   	
  	
        return userInput;
    }
    
    /**Метод создаёт и заполняет данными из базы данных и возвращает список экземпляров класса User.
    @return список объектов класса User */
    private List<User> findUsers() throws UserDaoException 
    {
        List<User> list = new LinkedList<>();
        try (Connection con = getConnection();
                PreparedStatement pst = con.prepareStatement(SELECT);
                ResultSet rs = pst.executeQuery()) 
        {
            while (rs.next()) 
            {
                list.add(fillUser(rs));
            }
            rs.close();
        } 
        catch (Exception e) 
        {
            throw new UserDaoException(e);
        }
        return list;
    }
    
    /**Метод заполняет список пользователей данными о их текущих и пройденных темах.
    @param listUser список пользователей*/
    private void findUsersTheme(List<User> listUser) throws UserDaoException 
    {
    	for(User user : listUser)//Проходим по списку пользователей
    	{
    	   List<UserTheme> listActualTheme = new LinkedList<>();
    	   List<UserTheme> listNotActualTheme = new LinkedList<>();
           try (Connection con = getConnection();
                PreparedStatement pst = con.prepareStatement(SELECT_USER_THEME)) 
           {
        	   pst.setLong(1, user.getUserId());
               ResultSet rs = pst.executeQuery();
               while (rs.next()) 
               {
            	   UserTheme us = fillUserTheme(rs);
                   if(us.getActual())//Если задание актуально
                   {
                	   listActualTheme.add(us);
                   }
                   else//Если нет
                   {
                	   listNotActualTheme.add(us);
                   }   
               }
              rs.close(); 
              user.setActualTheme(listActualTheme);
              user.setNotActualTheme(listNotActualTheme);
           } 
           catch (Exception e) 
           {
              throw new UserDaoException(e);
           }
    	}
    }
    
    /**Метод находит экземпляр класса User из списка по его ID(идентификационному номеру) .
    @param id идентификационный номер
    @return экземпляр класса User, или null, если такого нет */
    @Override
    public User getUser (Long id)  
    {
    	User user = null;
        for(User us : listUser)
        {
        	if(us.getUserId() == id)
        	{
        		user = us;
        		break;
        	}
        }
        return user;
    }
    
    /**Получить список пользователей
     * @return список пользователей*/
	@Override
	public List<User> getUserList() 
	{
		return listUser;
	}
	
	/**Метод заполняет список пользователей данными о их водах в программу.
    @param listUser список пользователей*/
    private void findUsersInput(List<User> listUser) throws UserDaoException 
    {
    	for(User user : listUser)//Проходим по списку пользователей
    	{
    	   List<UserInput> listInput = new LinkedList<>();
    
           try (Connection con = getConnection();
                PreparedStatement pst = con.prepareStatement(SELECT_USER_INPUT)) 
           {
        	   pst.setLong(1, user.getUserId());
               ResultSet rs = pst.executeQuery();
               while (rs.next()) 
               {
            	   UserInput us = fillUserInput(rs);                 
            	   listInput.add(us);                   
               }
              rs.close(); 
              user.setUserInput(listInput);            
           } 
           catch (Exception e) 
           {
              throw new UserDaoException(e);
           }
    	}
    }
}
