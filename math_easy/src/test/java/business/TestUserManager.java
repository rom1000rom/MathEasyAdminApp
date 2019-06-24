package business;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import config.GlobalConfig;
import dao.*;
import entity.User;
import exception.UserDaoException;

/**Класс предназначен для тестирования методов класса UserManager.
@author Артемьев Р.А.
@version 22.06.2019 */
public class TestUserManager
{
	//Загружаем параметры и заполняем БД тестовыми данными
	@BeforeClass
    public static void startUp() throws Exception 
	{
		try 
    	{
			GlobalConfig.initGlobalConfig(); //Делаем начальную загрузку параметров
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
        InitDB.init();//Заполняем БД тестовыми данными 
        System.out.println("All tests started\n");
    }
	
	//Тестируем метод получения данных о пользователе по его ID
    @Test
    public void getUserTest() throws UserDaoException 
    {      	
    	 System.out.println("Started: " + CurrentMethodName.getMethodName());
    	 User user = new UserManager().getUser((long)0);
    	 //System.out.println(user);
         Assert.assertTrue(user.getUserId() == (long)0); 
         System.out.println("Finished: " + CurrentMethodName.getMethodName() + "\n");
    }
 
    //Тестируем метод получения списка пользователей
    @Test
    public void getUserListTest() throws UserDaoException 
    {  	    
    	System.out.println("Started: " + CurrentMethodName.getMethodName());
    	List<User> list = new UserManager().getUserList();
    	//System.out.println(list.get(0));
        Assert.assertFalse(list.isEmpty());    
        System.out.println("Finished: " + CurrentMethodName.getMethodName() + "\n");
    }
    
    //Тестируем методы добавления и удаления актуальной темы у пользователя
    @Test
    public void addDeleteUserThemeTest() throws UserDaoException 
    {  	 
    	//Тестируем метод добавления актуальной темы пользователю
    	System.out.println("Started: " + CurrentMethodName.getMethodName());
    	new UserManager().addUserTheme((long)0, (long)0, -1);
    	User user = new UserManager().getUser((long)0);
    	//System.out.println(user.getActualTheme());
        Assert.assertTrue(user.getActualTheme().get(0).getSolve_task() == -1);      
        
        //Тестируем метод удаления актуальной темы у пользователя        
    	new UserManager().deleteUserTheme((long)0, (long)0);
    	user = new UserManager().getUser((long)0);
    	//System.out.println(user.getActualTheme());
        Assert.assertTrue(user.getActualTheme().isEmpty()); 
        System.out.println("Finished: " + CurrentMethodName.getMethodName() + "\n");
    }
    
    //Очищаем БД от тестовых данных
  	@AfterClass
    public static void cleanUp() throws Exception 
  	{  	
  		System.out.println("All tests finished");
        CleanDB.clean();//Очищаем БД       
    }
    
}