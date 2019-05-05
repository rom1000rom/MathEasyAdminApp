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
import entity.Theme;
import entity.User;
import entity.UserInput;
import entity.UserTheme;
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
	
	/**Список тем*/
	private List<Theme> listTheme;
	
	
	private ConnectionBuilder builder = ConnectionBuilderFactory.getConnectionBuilder();
    private Connection getConnection() throws SQLException 
    {
        return builder.getConnection();
    }
    
    public ThemeDbDAO() throws ThemeDaoException
    {
    	listTheme = findThemes();    	
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
		
	
}
