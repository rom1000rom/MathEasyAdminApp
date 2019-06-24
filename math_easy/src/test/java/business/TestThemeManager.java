package business;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import config.GlobalConfig;
import dao.*;
import entity.Task;
import entity.Theme;
import entity.User;
import exception.UserDaoException;

/**Класс предназначен для тестирования методов класса ThemeManager.
@author Артемьев Р.А.
@version 24.06.2019 */
public class TestThemeManager
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
	
	//Тестируем метод получения данных о теме по её ID
    @Test
    public void getThemeTest() throws UserDaoException 
    {      	
    	 System.out.println("Started: " + CurrentMethodName.getMethodName());
    	 Theme theme = new ThemeManager().getTheme((long)0);
    	 //System.out.println(theme);
         Assert.assertTrue(theme.getTheme_id() == (long)0); 
         System.out.println("Finished: " + CurrentMethodName.getMethodName() + "\n");
    }
 
    //Тестируем метод получения списка тем
    @Test
    public void getThemeListTest() throws UserDaoException 
    {  	    
    	System.out.println("Started: " + CurrentMethodName.getMethodName());
    	List<Theme> list = new ThemeManager().getThemeList();
    	//System.out.println(list.get(0));
        Assert.assertFalse(list.isEmpty());    
        System.out.println("Finished: " + CurrentMethodName.getMethodName() + "\n");
    }
    
    //Тестируем метод получения отображения списка заданий
    @Test
    public void getTaskMapTest() throws UserDaoException 
    {  	    
    	System.out.println("Started: " + CurrentMethodName.getMethodName());
    	Map<Long, Task> map = new ThemeManager().getTaskMap();
    	//System.out.println(map.get((long)0));
        Assert.assertFalse(map.isEmpty());    
        System.out.println("Finished: " + CurrentMethodName.getMethodName() + "\n");
    }
    
   //Тестируем методы добавления, обновления и удаления темы
    @Test
    public void addUpdateDeleteThemeTest() throws UserDaoException 
    {  	 
    	//Тестируем метод добавления темы
    	System.out.println("Started: " + CurrentMethodName.getMethodName());
    	new ThemeManager().addTheme((long)-1, "Название_новой_темы", "Описание_новой_темы");
    	Theme theme = new ThemeManager().getTheme((long)-1);
    	//System.out.println(theme);
        Assert.assertNotNull(theme);
        
        //Тестируем метод обновления темы
        new ThemeManager().updateTheme("Обновлённое_название_новой_темы", "Обновлённое_описание_новой_темы", (long)-1);  
        theme = new ThemeManager().getTheme((long)-1);
    	//System.out.println(theme);
        Assert.assertTrue(theme.getTheme_title().equals("Обновлённое_название_новой_темы"));       
        
        //Тестируем метод удаления темы
        new ThemeManager().deleteTheme((long)-1);  
        theme = new ThemeManager().getTheme((long)-1);
    	//System.out.println(theme);
        Assert.assertNull(theme);
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