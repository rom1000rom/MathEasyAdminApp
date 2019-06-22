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
public class UserManagerTest
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
        System.out.println("База данных заполнена тестовыми данными.\n");
    }
	
	//Тестируем метод получения данных о пользователе по его ID
    @Test
    public void testGetUser() throws UserDaoException 
    {   	
    	System.out.println("Тест №1");
    	User user = new UserManager().getUser((long)0);
    	System.out.println(user);
        Assert.assertTrue(user.getUserId() == (long)0);        
    }
 
    //Тестируем метод получения списка пользователей
    @Test
    public void testGetUserList() throws UserDaoException 
    {  	
    	System.out.println("Тест №2");
    	List<User> list = new UserManager().getUserList();
    	System.out.println(list.get(0));
        Assert.assertFalse(list.isEmpty());        
    }
    
    //Тестируем метод добавления актуальной темы пользователю
    @Test
    public void testAddUserTheme() throws UserDaoException 
    {  	
    	System.out.println("Тест №3");
    	new UserManager().addUserTheme((long)0, (long)0, -1);
    	User user = new UserManager().getUser((long)0);
    	System.out.println(user.getActualTheme().get(0));
        Assert.assertTrue(user.getActualTheme().get(0).getSolve_task() == -1);        
    }
    
    //Тестируем метод удаления актуальной темы у пользователя
    @Test
    public void testDeleteUserTheme() throws UserDaoException 
    {  	
    	System.out.println("Тест №4");
    	new UserManager().deleteUserTheme((long)0, (long)0);
    	User user = new UserManager().getUser((long)0);
    	System.out.println(user.getActualTheme());
        Assert.assertTrue(user.getActualTheme().isEmpty());        
    }
    
    //Очищаем БД от тестовых данных
  	@AfterClass
    public static void cleanUp() throws Exception 
  	{
        CleanDB.clean();//Очищаем БД
        System.out.println("\nБаза данных очищена от тестовых данных.");
    }
    
}