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
	   /**Список пользователей*/
	   private final JList<String> userList;
	   /**Панель с вкладками*/
	   private final JTabbedPane tabbetPane;
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
	       
	      //Создаём список пользователей
	      userList = new JList<>(new UserListModel(userManager.getUserList()));
	      userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      userList.setPrototypeCellValue("wwwwwwwwwwww");
	      userList.setVisibleRowCount(14);
	      
	      userList.addListSelectionListener(event ->
	      {
	    	  if(event.getValueIsAdjusting())
	    	  {
	    	      System.out.println(userList.getSelectedValue());
	    	  }
	      });
	      JScrollPane userScrollPane = new JScrollPane(userList);
	      
	      //Создаём и заполняем панель с вкладками
	      tabbetPane = new JTabbedPane();
	      JPanel firstPanel = new JPanel();
	      JPanel secondPanel = new JPanel();;
	      JPanel thirdPanel = new JPanel();;
	      tabbetPane.add("Общая информация", firstPanel);
	      tabbetPane.add("Задания", secondPanel);
	      tabbetPane.add("История входов", thirdPanel);
	      
	     
	      TextArea tree = new TextArea();
	      tree.setText("Where will be tree");
	      
	      innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userScrollPane, tabbetPane);
	      outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, tree);
	      add(outerPane, BorderLayout.CENTER);
	   }
}
