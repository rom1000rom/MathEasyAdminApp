package gui;

import javax.swing.*;

import business.UserManager;
import entity.User;

import java.awt.*;

/**Класс представляет основной фрейм приложения.
@author Артемьев Р.А.
@version 24.04.2019 */

public class Math_easyFrame extends JFrame
{
	   /**Внутренняя разделяемая панель*/
	   private final JSplitPane innerPane;
	   /**Внешняя разделяемая панель*/
	   private final JSplitPane outerPane;
	   /**Менеджер по работе со списком пользователей*/
	   private final UserManager  userManager = new  UserManager();
	   
	   public Math_easyFrame()
	   {
	      //Задаём размеры и положение фрейма
	      Toolkit kit = Toolkit.getDefaultToolkit();
	      Dimension screenSize = kit.getScreenSize();
	      int screenHeight = screenSize.height + (screenSize.height/2);
	      int screenWidth = screenSize.width;
	      setLocation(screenWidth / 4, screenHeight / 11);
	      setSize(screenWidth / 2, screenHeight / 2);
	      
	      User us = userManager.getContact((long)1);
	      if(us != null)
	      {
	          System.out.println(us.getLastName());
	      }
	      else
	      {
	    	  System.out.println("Пользователя с таким ID-номером нет");
	      }
	      
	      //Создаём разделяемы панели
	      TextArea list = new TextArea();
	      list.setText("Where will be list");
	      TextArea tabPane = new TextArea();
	      tabPane.setText("Where will be tabPane");
	      TextArea tree = new TextArea();
	      tree.setText("Where will be tree");
	      
	      innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, tabPane);
	      outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, tree);
	      add(outerPane, BorderLayout.CENTER);
	   }
}
