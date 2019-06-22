package test;

import java.awt.*;
import javax.swing.*;
import config.GlobalConfig;
import gui.Math_easyFrame;
import gui.PasswordEntryDialogPane;

/**Класс служит для запуска приложения.
@author Артемьев Р.А.
@version 23.04.2019 */
public class Math_easyStart 
{
    public static void main( String[] args ) 
    {
    	try 
    	{
			GlobalConfig.initGlobalConfig(); //Делаем начальную загрузку параметров
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    	
    	//Создаём фрейм и делаем его видимым
	    EventQueue.invokeLater(()->
	    {
	    	PasswordEntryDialogPane passwordPane = new PasswordEntryDialogPane();//Создаём панель ввода пароля
	    	if(passwordPane.isAccessConfirmed())
	    	{
	    	   JFrame myframe = new Math_easyFrame();
	    	   myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	   myframe.setTitle("Math Easy");
	    	   myframe.setResizable(false);	    	
	    	   myframe.setVisible(true);
	    	}
	    });   
        
    }
}
