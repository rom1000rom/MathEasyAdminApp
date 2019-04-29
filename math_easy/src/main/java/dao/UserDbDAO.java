package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import connection.ConnectionBuilder;
import connection.ConnectionBuilderFactory;
import entity.User;
import exception.UserDaoException;



/**Класс-реализация интерфейса UserDAO для работы с базой данных.
@author Артемьев Р.А.
@version 27.04.2019 */
public class UserDbDAO implements UserDAO
{
	private static final String SELECT
    = "SELECT user_id, first_name, last_name, school_class, school_number, date_of_registration FROM me_user ORDER BY last_name;";
	
	/**Список пользователей*/
	private List<User> listUser;
	
	
	private ConnectionBuilder builder = ConnectionBuilderFactory.getConnectionBuilder();
    private Connection getConnection() throws SQLException 
    {
        return builder.getConnection();
    }
    
    public UserDbDAO() throws UserDaoException
    {
    	listUser = findUsers();
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
}
