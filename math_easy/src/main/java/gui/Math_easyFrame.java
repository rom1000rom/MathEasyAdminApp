package gui;

import javax.swing.*;
import javax.swing.table.*;

import business.ThemeManager;
import business.UserManager;
import entity.User;
import entity.UserTheme;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**Класс представляет основной фрейм приложения.
@author Артемьев Р.А.
@version 24.04.2019 */

public class Math_easyFrame extends JFrame
{
	   /**Высота строк таблиц.*/
	   public static final int ROW_HEIGHT = 20;
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
	   /**Панель для отображения дерева тем, подтем и заданий*/
	   private final  JPanel treePanel = new JPanel();
	   /**Менеджер по работе со списком тем*/
	   private final ThemeManager  themeManager = new  ThemeManager();
	   /**Менеджер по работе со списком пользователей*/
	   private final UserManager  userManager = new  UserManager();
	   /**Таблица актуальных тем*/
	   private static final JTable  actualThemeTable = new JTable();
	   /**Таблица пройденных тем*/
	   private static final JTable  notActualThemeTable = new JTable();
	   /**Таблица входов пользователя в программу*/
	   private static final JTable  userInputTable = new JTable();
	   
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
	      userJList.setVisibleRowCount(20);
	      //Регистрируем обработчик событий списка пользователей
	      userJList.addListSelectionListener(event ->
	      {
	    	  if(event.getValueIsAdjusting())
	    	  {
	    	      User us = listUser.get(userJList.getSelectedIndex());
	    	      fillUserInformationPanel(firstPanel, us);
	    	      tabbetPane.updateUI();
	    	      actualThemeTable.setModel(new ActualThemeTableModel(us.getActualTheme()));
	    	      notActualThemeTable.setModel(new NotActualThemeTableModel(us.getNotActualTheme()));
	    	      userInputTable.setModel(new InputTableModel(us.getUserInput()));
	    	  }
	      });
	      JScrollPane userScrollPane = new JScrollPane(userJList);
	      
	      //Заполняем  панель вкладками
	      tabbetPane.add("Общая информация", firstPanel);
	      tabbetPane.add("Темы", secondPanel);
	      tabbetPane.add("История входов", thirdPanel);
	      
	      //Заполняем  панель с актуальными и пройденными темами
	      fillUserThemePanel(secondPanel);
	      
	      //Заполняем  панель с входами пользователя в программу
	      fillUserInputPanel(thirdPanel);
	      
	      //Заполняем  панель с деревом тем, подтем и заданий
	      fillTreePanel(treePanel);	            
	      
	      //Создаём и заполняем разделяемые панели
	      innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userScrollPane, tabbetPane);
	      outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, treePanel);
	      outerPane.setDividerLocation(screenHeight / 5);//Задаём положение разделителя
	      add(outerPane, BorderLayout.CENTER);
	   }
	   
	   /**Метод очищает и заполняет панель с личной информацией пользователя, полученную в качестве первого параметра,
	    *  данными пользователя, полученного в качестве второго параметра.
	    @param panel панель 
	    @param user пользователь*/
	   private static void fillUserInformationPanel(JPanel panel, User user)
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
	   
	   /**Метод заполняет панель полученную в качестве параметра информацией
	    * об актуальных и пройденных темах.
	    @param userThemePanel панель с актуальными и пройденными темами */
	   private static void fillUserThemePanel(JPanel userThemePanel)
	   {	     
		      JPanel tablePanel = new JPanel(new GridLayout(2, 0));//Панель для таблиц         
		      userThemePanel.setLayout(new BorderLayout());
		      userThemePanel.add(tablePanel, BorderLayout.CENTER);
		      
		      //Заполняем северную панель с актуальными темами
		      JPanel northPanel= new JPanel();
		      northPanel.setLayout(new BorderLayout());		      
		      //Создаём метку для таблицы актуальных тем
		      JLabel northLabel = new JLabel("Список актуальных тем");
		      northLabel.setFont(new Font(null, Font.BOLD, 14));
		      northLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
		      northPanel.add(northLabel, BorderLayout.NORTH);
		      //Задаём параметры таблицы актуальных тем
		      JScrollPane actualThemeScrollPane = new JScrollPane(actualThemeTable);		     
		      actualThemeTable.setRowHeight(ROW_HEIGHT); 		      
		      actualThemeTable.setFont(new Font(null, Font.BOLD, 12));
		      actualThemeTable.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
		      northPanel.add(actualThemeScrollPane, BorderLayout.CENTER);
		      tablePanel.add(northPanel);
		      
		      //Заполняем южную панель с пройденными темами
		      JPanel southPanel= new JPanel();
		      southPanel.setLayout(new BorderLayout());
		      //Создаём метку для таблицы неактуальных тем
		      JLabel southLabel = new JLabel("Список пройденных тем");
		      southLabel.setFont(new Font(null, Font.BOLD, 14));
		      southLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
		      southPanel.add(southLabel, BorderLayout.NORTH);
		      //Задаём параметры таблицы пройденных тем
		      JScrollPane notActualThemeScrollPane = new JScrollPane(notActualThemeTable);
		      notActualThemeTable.setRowHeight(ROW_HEIGHT); 
		      notActualThemeTable.setFont(new Font(null, Font.BOLD, 12));
		      notActualThemeTable.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
		      southPanel.add(notActualThemeScrollPane, BorderLayout.CENTER);
		      tablePanel.add(southPanel);
		      
		      //Создаём и заполняем панель с кнопками
		      JPanel buttonPanel = new JPanel();
		      JButton addButton = new JButton("Добавить тему");
			  //Регистрируем обработчик для событий кнопки addButton
		      addButton.addActionListener(event ->
			   {});
		      JButton deleteButton = new JButton("Удалить тему");
			  //Регистрируем обработчик для событий кнопки deleteButton
		      deleteButton.addActionListener(event ->
			   {});
		      buttonPanel.add(addButton);
		      buttonPanel.add(deleteButton);
		      userThemePanel.add(buttonPanel, BorderLayout.SOUTH);		      
	   }

	   /**Метод заполняет панель полученную в качестве параметра информацией
	    * о входах пользователя в программу.
	    @param userInputPanel панель с информацией о входах пользователя в программу */
	   private static void fillUserInputPanel(JPanel userInputPanel)
	   {	 
		   userInputPanel.setLayout(new BorderLayout());
		   
		   //Создаём метку для таблицы входов пользователя в программу
		   JLabel inputLabel = new JLabel("Входы пользователя");
		   inputLabel.setFont(new Font(null, Font.BOLD, 14));
		   inputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
		   userInputPanel.add(inputLabel, BorderLayout.NORTH);
		   
		   //Задаём параметры таблицы входов пользователя в программу
		   JScrollPane userInputScrollPane = new JScrollPane(userInputTable);
		   userInputTable.setRowHeight(ROW_HEIGHT); 
		   userInputTable.setFont(new Font(null, Font.BOLD, 12));
		   userInputTable.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
		   userInputPanel.add(userInputScrollPane, BorderLayout.CENTER);
		   
		   //Создаём и заполняем панель с кнопками
		   JPanel buttonPanel = new JPanel();
		   JButton viewButton = new JButton("Просмотреть задания");
		   //Регистрируем обработчик для событий кнопки addButton
		   viewButton.addActionListener(event ->
		   {});		   
		   buttonPanel.add(viewButton);		   
		   userInputPanel.add(buttonPanel, BorderLayout.SOUTH);	
	   }
	   
	   /**Метод заполняет панель полученную в качестве параметра деревом тем, подтем и
	    * заданий.
	    @param treePanel панель с информацией о темах, подтемах и заданиях */
	   private static void fillTreePanel(JPanel treePanel)
	   {	
		   treePanel.setLayout(new BorderLayout());
		   TextArea tree = new TextArea();
		   tree.setText("Where will be tree");
		   treePanel.add(tree, BorderLayout.CENTER);
	   }
}
