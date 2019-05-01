package gui;

import javax.swing.*;

import business.UserManager;
import entity.User;
import entity.UserTheme;

import java.awt.*;
import java.util.List;

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
	   private final JList<String> userJList;
	   /**Панель с вкладками*/
	   private final JTabbedPane tabbetPane = new JTabbedPane();
	   /**Панель для отображения общих данных о пользователе*/
	   private final  JPanel firstPanel = new JPanel();
	   /**Панель для отображения текущих и пройденных тем пользователя*/
	   private final  JPanel secondPanel = new JPanel();
	   /**Панель для отображения истории входов пользователя*/
	   private final  JPanel thirdPanel = new JPanel();
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
	      List<User> listUser = userManager.getUserList();
	      userJList = new JList<>(new UserListModel(listUser));
	      userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      userJList.setPrototypeCellValue("wwwwwwwwwwww");
	      userJList.setVisibleRowCount(14);
	      //Регистрируем обработчик событий списка пользователей
	      userJList.addListSelectionListener(event ->
	      {
	    	  if(event.getValueIsAdjusting())
	    	  {
	    	      User us = listUser.get(userJList.getSelectedIndex());
	    	      fillPanel(firstPanel, us);
	    	      tabbetPane.updateUI();
	    	  }
	      });
	      JScrollPane userScrollPane = new JScrollPane(userJList);
	      
	      //Заполняем  панель вкладками
	      tabbetPane.add("Общая информация", firstPanel);
	      tabbetPane.add("Темы", secondPanel);
	      tabbetPane.add("История входов", thirdPanel);
	      
	      User user = userManager.getUser((long)2);
	      if(user.getActualTheme() != null)
	      {
	         for(UserTheme us : user.getActualTheme())
	         {
	    	    System.out.println(us);
	         }
	      }
	      TextArea tree = new TextArea();
	      tree.setText("Where will be tree");
	      
	      innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userScrollPane, tabbetPane);
	      outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, tree);
	      add(outerPane, BorderLayout.CENTER);
	   }
	   
	   /**Метод очищает и заполняет панель, полученную в качестве первого параметра,
	    *  данными пользователя, полученного в качестве второго параметра.
	    @param panel панель 
	    @param user пользователь*/
	   private static void fillPanel(JPanel panel, User user)
	   {
		   panel.removeAll();//Очищаем панель
		   panel.setLayout(new GridLayout(6, 0));
		 //Выводим полное имя пользователя
		   JLabel name = new JLabel("Пользователь:  " + user.getLastName() + " " + user.getFirstName(), JLabel.LEFT);	
		   name.setFont(new Font(null, Font.BOLD, 14));
		   name.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));		   		   
		   panel.add(name);
		 //Выводим номер класса пользователя
		   JLabel schoolClass = new JLabel("Номер класса:  " + user.getSchoolClass(), JLabel.LEFT);	
		   schoolClass.setFont(new Font(null, Font.BOLD, 14));
		   schoolClass.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));		   	   
		   panel.add(schoolClass);
		 //Выводим номер школы пользователя
		   JLabel schoolNumber = new JLabel("Номер школы:  " + user.getSchoolNumber(), JLabel.LEFT);	
		   schoolNumber.setFont(new Font(null, Font.BOLD, 14));
		   schoolNumber.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));		   	   
		   panel.add(schoolNumber);
		 //Выводим дату регистрации пользователя
		   JLabel dateOfRegistration = new JLabel("Дата регистрации:  " + user.getDateOfRegistration(), JLabel.LEFT);	
		   dateOfRegistration.setFont(new Font(null, Font.BOLD, 14));
		   dateOfRegistration.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));		   	   
		   panel.add(dateOfRegistration);
	   }
}
