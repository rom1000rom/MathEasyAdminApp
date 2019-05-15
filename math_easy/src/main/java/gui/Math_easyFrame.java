package gui;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import business.ThemeManager;
import business.UserManager;
import entity.Subtheme;
import entity.Task;
import entity.Theme;
import entity.User;
import entity.UserTheme;
import exception.UserDaoException;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**Класс представляет основной фрейм приложения.
@author Артемьев Р.А.
@version 24.04.2019 */

public class Math_easyFrame extends JFrame
{
	   /**Высота строк таблиц.*/
	   public static final int ROW_HEIGHT = 20;
	   /**Внутренняя разделяемая панель для отображения списка пользователей и информации о них*/
	   private final JSplitPane  northInnerPane;
	   /**Внутренняя разделяемая панель для отображения дерева тем, подтем и заданий*/
	   private final JSplitPane  southInnerPane;
	   /**Внешняя разделяемая панель*/
	   private final JSplitPane outerPane;
	   /**Список пользователей(ГПИ)*/
	   private static JList<String> userJList;
	   /**Список пользователей*/
	   private static List<User> listUser;
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
	   /**Панель для отображения выбранного задания*/
	   private static final  JPanel taskDescriptionPanel = new JPanel();
	   /**Менеджер по работе со списком тем*/
	   private static final ThemeManager  themeManager = new  ThemeManager();
	   /**Менеджер по работе со списком пользователей*/
	   private static final UserManager  userManager = new  UserManager();
	   /**Таблица актуальных тем*/
	   private static final JTable  actualThemeTable = new JTable();
	   /**Таблица пройденных тем*/
	   private static final JTable  notActualThemeTable = new JTable();
	   /**Таблица входов пользователя в программу*/
	   private static final JTable  userInputTable = new JTable();
	   /**Дерево тем, подтем и заданий*/
	   private static  JTree  tree;
	   
	   
	   public Math_easyFrame() 
	   {		  
	      //Задаём размеры и положение фрейма
	      Toolkit kit = Toolkit.getDefaultToolkit();
	      Dimension screenSize = kit.getScreenSize();
	      int screenHeight = screenSize.height + (screenSize.height/4);
	      int screenWidth = screenSize.width;
	      setLocation(screenWidth / 4, screenHeight / 11);
	      setSize(screenWidth / 2, screenHeight / 2);
	       
	      //Создаём список пользователей
	      listUser = userManager.getUserList();
	      userJList = new JList<>(new UserListModel(listUser));
	      userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      userJList.setFont(new Font(null, Font.BOLD, 13));
	      userJList.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
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
	      fillUserInputPanel(thirdPanel, themeManager.getTaskMap());
	      	      
	      //Заполняем  панель с деревом тем, подтем и заданий
	      fillTreePanel(treePanel, themeManager.getThemeList());
	      	          	      
	      //Создаём и заполняем разделяемые панели
	      northInnerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userScrollPane, tabbetPane);
	      southInnerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, taskDescriptionPanel);
	      southInnerPane.setDividerLocation(screenWidth / 6);//Задаём положение разделителя
	      
	      outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,  northInnerPane, southInnerPane);
	      outerPane.setDividerLocation(screenHeight / 4);//Задаём положение разделителя
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
			   {
				   int row = userJList.getSelectedIndex();
				   TreePath path = tree.getSelectionPath();//Получаем путь к выбранному узлу дерева
				   if(path == null) 
				   {   
					   JOptionPane.showMessageDialog(null, "Выделите строку с именем пользователя и названием темы"
							   , " ", JOptionPane.WARNING_MESSAGE);
					   return;
				   }
				   DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode)path.getLastPathComponent();
				   //Если выбран узел с темой(его предок - корень)
				   if(selectionNode.getParent() != null)
				   {
				      if((selectionNode.getParent().getParent() == null)&&(row >= 0))
				      {			       
				    	  User user = listUser.get(row);//Пользователь, которому нужно добавить тему
				    	  Theme theme =  (Theme)selectionNode.getUserObject();//Тема, которую нужно добавить
				    	  Boolean f = true;
				    	  for(UserTheme t : user.getActualTheme())//Проверяем, не присутствует ли эта тема у пользователя
				    	  {
				    		  if(t.getTheme_id() ==  theme.getTheme_id())
				    		  {
				    			  f = false;
				    		  }
				    	  }
				    	  if(f)
				    	  {  				    		  
				    		  String str = JOptionPane.showInputDialog(null,"Введите количество заданий", 
				    				  "", JOptionPane.QUESTION_MESSAGE);				    		 
				    		  int count = 0;
				    		  try
				    		  {
				    		      count = Integer.parseInt(str);
				    		  }
				    		  catch(NumberFormatException e)
				    		  {
				    			  JOptionPane.showMessageDialog(null, "Введите число", " ", JOptionPane.WARNING_MESSAGE);
				    			  return;
				    		  }
				    	      userManager.addUserTheme(user.getUserId(), theme.getTheme_id(), count);//Добавляем тему пользователю
				    	      listUser = userManager.getUserList();
					          User us = userManager.getUser(user.getUserId());	
					          //Загружаем в таблицу обновлённую модель
				    	      actualThemeTable.setModel(new ActualThemeTableModel(us.getActualTheme()));
				    	  }
				    	  else
				    	  {
				    		  JOptionPane.showMessageDialog(null, "Данная тема уже добавлена", " ", JOptionPane.WARNING_MESSAGE);
				    	  }
				      }
				      else
				      {
				    	  JOptionPane.showMessageDialog(null, "Выделите строку с именем пользователя и названием темы",
				    			  " ", JOptionPane.WARNING_MESSAGE);
				      }
				   }
				   else
				   {
				       JOptionPane.showMessageDialog(null, "Выделите строку с именем пользователя и названием темы"
				    		   , " ", JOptionPane.WARNING_MESSAGE);
				   }				  				   
			   });
		      
		      JButton deleteButton = new JButton("Удалить тему");
			  //Регистрируем обработчик для событий кнопки deleteButton
		      deleteButton.addActionListener(event ->
			   {
				   int row =  actualThemeTable.getSelectedRow();
				   if(row >= 0)
				   {
					   //Получаем id темы, которую нужно удалить
				       Long themeId = (Long)actualThemeTable.getModel().getValueAt(row, 0);
				       //Получаем id пользователя
				       Long userId = listUser.get(userJList.getSelectedIndex()).getUserId();				     
					   userManager.deleteUserTheme(userId, themeId);//Удаляем тему из базы данных				  
				       listUser = userManager.getUserList();
				       User us = userManager.getUser(userId);	
				       //Загружаем в таблицу обновлённую модель
			    	   actualThemeTable.setModel(new ActualThemeTableModel(us.getActualTheme()));
				   }
				   else
				   {				
				      JOptionPane.showMessageDialog(null, "Выделите строку с актуальной темой"
				    		  , " ", JOptionPane.WARNING_MESSAGE);
				   }	
			   });
		      buttonPanel.add(addButton);
		      buttonPanel.add(deleteButton);
		      userThemePanel.add(buttonPanel, BorderLayout.SOUTH);		      
	   }

	   /**Метод заполняет панель полученную в качестве параметра информацией
	    * о входах пользователя в программу.
	    @param userInputPanel панель с информацией о входах пользователя в программу */
	   private static void fillUserInputPanel(JPanel userInputPanel, Map<Long,Task> taskMap)
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
		   //Регистрируем обработчик для событий кнопки viewButton
		   viewButton.addActionListener(event ->
		   {
			   List<Task> list = new ArrayList<>();	
			   int row = userInputTable.getSelectedRow();
			   int column = userInputTable.getSelectedColumn();
			   if((row >= 0)&&(column > 0))
			   {
			       String str;	
			       List<String> arrayStr = new ArrayList<>();
				   str = (String)userInputTable.getModel().getValueAt(row, column);//Получаем строку из выделенной ячейки
				   int from = 0;
				   int to = str.indexOf(" ");
				   while(from < str.length())
				   {
					    String s = str.substring(from, to);//Получаем подстроку с очередным числом					   
				        arrayStr.add(s);//Добавляем полученную подстроку в списочный массив
				        from = to + 1;
				        to = str.indexOf(" ", from);
				   }
				   for(String s : arrayStr)
				   {	
					   Long l = Long.parseLong(s);//Получаем из подстроки число
					   if(taskMap.containsKey(l))//Если хешь-отображение содержит такой ключ
					   {
					       list.add(taskMap.get(l));//Получаем задание по его ключу
					   }
				   }
				   if(!list.isEmpty())
				   {
				       new TaskDialogPane(list);
				   }
				   else
				   {
					   JOptionPane.showMessageDialog(null, "Ячейка пуста");
				   }
			   }
			   else
			   {				
			      JOptionPane.showMessageDialog(null, "Выделите ячейку со списком заданий");
			   }	   	   				   
		   });		   
		   buttonPanel.add(viewButton);		   
		   userInputPanel.add(buttonPanel, BorderLayout.SOUTH);	
	   }
	   
	   /**Метод заполняет панель полученную в качестве параметра деревом тем, подтем и
	    * заданий.
	    @param treePanel панель с информацией о темах, подтемах и заданиях
	    @param listTheme список тем */
	   private static void fillTreePanel(JPanel treePanel, List<Theme> listTheme)
	   {	
		   //Создаём дерево тем, подтем и заданий
		   DefaultMutableTreeNode root = new DefaultMutableTreeNode("Темы");//Корневой узел дерева
		   DefaultMutableTreeNode theme;//Переменная для узлов с темами
		   DefaultMutableTreeNode subtheme;//Переменная для узлов с подтемами
		   DefaultMutableTreeNode task;//Переменная для узлов с заданиями
		   for(Theme th : listTheme)
		   {
			   theme = new DefaultMutableTreeNode(th);
			   theme.setAllowsChildren(true);
			   root.add(theme);
			   for(Subtheme s : th.getSubtheme())
			   {
				   subtheme = new DefaultMutableTreeNode(s);
				   subtheme.setAllowsChildren(true);
				   theme.add(subtheme);
				   for(Task ta : s.getTask())
				   {
					   task = new DefaultMutableTreeNode(ta);					  
					   task.setAllowsChildren(false);
					   subtheme.add(task);				   
				   }
			   }
		   }
		   
		   //Создаём дерево и регистрируем обработчик выбора его узлов
		   tree = new JTree(root, true);
		   tree.setFont(new Font(null, Font.BOLD, 13));
		   tree.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		   int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
		   tree.getSelectionModel().setSelectionMode(mode);//Выбрать можно только один узел
		   tree.addTreeSelectionListener(event -> 
		   {
			   TreePath path = tree.getSelectionPath();//Получаем путь к выбранному узлу дерева
			   if(path == null) 
			   {   
				   return;
			   }
			   DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode)path.getLastPathComponent();
			   
			   //Если выбран узел с темой(его предок - корень)
			   if(selectionNode.getParent() != null)
			   {				  
			      if(selectionNode.getParent().getParent() == null)
			      {		
			    	 chooseVisibleJPopupMenu(tree, true, true, true);//Задаём видимость пунктов контекстного меню			    	   
				     fillThemeInformationPanel(taskDescriptionPanel, (Theme)selectionNode.getUserObject());
				     taskDescriptionPanel.updateUI(); 
			      }	
			      else//Если выбран узел с подтемой
			      {
			    	 chooseVisibleJPopupMenu(tree, true, true, true);//Задаём видимость пунктов контекстного меню		    	   
			      }
			   }
			   else//Если был выбран корень дерева
			   {
				   chooseVisibleJPopupMenu(tree, true, false, false);//Задаём видимость пунктов контекстного меню				   
			   }
			 //Если выбран узел с заданием(без потомков)
			   if(!selectionNode.getAllowsChildren())
			   {	
				   chooseVisibleJPopupMenu(tree, false, true, true);//Задаём видимость пунктов контекстного меню				   
			       fillTaskInformationPanel(taskDescriptionPanel, (Task)selectionNode.getUserObject());			       
			       taskDescriptionPanel.updateUI(); 
			   }
			   
		   });
		   
		   JScrollPane treeScrollPane = new JScrollPane(tree);
		   treePanel.setLayout(new BorderLayout());
		   treePanel.add(treeScrollPane, BorderLayout.CENTER);
		   
		   //Создаём всплывающее меню
		   JPopupMenu popup = new JPopupMenu();
		   
		   //Создаём пункт меню для добавления нового узла дерева
		   JMenuItem addItem = new JMenuItem("Добавить");
		   addItem.addActionListener(event ->
		   {
			   addNode();
		   });
		   
		   //Создаём пункт меню для удаления узла дерева
		   JMenuItem deleteItem = new JMenuItem("Удалить");
		   deleteItem.addActionListener(event ->
		   {
			   deleteNode();
		   });
		   
		   //Создаём пункт меню для редактирования узла дерева
		   JMenuItem editItem = new JMenuItem("Редактировать");
		   editItem.addActionListener(event ->
		   {
			   
		   });
		   popup.add(addItem);	
		   popup.add(deleteItem);
		   popup.add(editItem);		  
		   tree.setComponentPopupMenu(popup);		   
	   }
	   
	   /**Метод задаёт видимость пунктов контекстного меню.	     
	    @param tree дерево, в котором находится контекстное меню
	    @param first первый пункт меню
	    @param second второй пункт
	    @param third третий пункт*/
	   private static void chooseVisibleJPopupMenu(JTree tree, Boolean first, Boolean second, Boolean third)
	   {
		   JPopupMenu menu = tree.getComponentPopupMenu();
		   menu.getComponent(0).setVisible(first);//Делаем видимыми только пункты "удалить" и "редактировать"
		   menu.getComponent(1).setVisible(second);
		   menu.getComponent(2).setVisible(third);
	   }
	   
	   /**Метод очищает и заполняет панель с описанием задания, полученную в качестве первого параметра,
	    *  данными задания, полученного в качестве второго параметра.
	    @param panel панель 
	    @param task задание*/
	   private static void fillTaskInformationPanel(JPanel panel, Task task)
	   {
		   panel.removeAll();//Очищаем панель
		   panel.setLayout(new BorderLayout());
		   //Выводим информацию о задании
		   JTextArea textAria = new  JTextArea();
		   textAria.setEditable(false);
		   textAria.setLineWrap(true);
		   textAria.setText("Задание№ " + task.getTaskId()  + "\n\n" +
				   "Описание задания: \n" + task.getDescription() + "\n\n" +
				   "Ответ: " + task.getAnswer());		   	   
		   JScrollPane taskDescriptionScrollPane = new JScrollPane(textAria);
		   textAria.setFont(new Font(null, Font.BOLD, 13));
		   textAria.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));		   		   
		   panel.add(taskDescriptionScrollPane, BorderLayout.NORTH);
	   }
	   

	   /**Метод очищает и заполняет панель с краткой теоретической информации о теме, 
	    * полученной в качестве второго параметра.	    
	    @param panel панель 
	    @param theme тема*/
	   private static void fillThemeInformationPanel(JPanel panel, Theme theme)
	   {
		   panel.removeAll();//Очищаем панель
		   panel.setLayout(new BorderLayout());
		   //Выводим краткую теоретическую информацию о теме
		   JTextArea textAria = new  JTextArea();
		   textAria.setEditable(false);
		   textAria.setLineWrap(true);
		   textAria.setText("Тема: " + theme.getTheme_title()  + "\n\n" +
				   "Краткая теоретическая информация: \n\r" + theme.getBrief_theoretical_information());		   	   
		   JScrollPane taskDescriptionScrollPane = new JScrollPane(textAria);
		   textAria.setFont(new Font(null, Font.BOLD, 13));
		   textAria.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));		   		   
		   panel.add(taskDescriptionScrollPane, BorderLayout.NORTH);
	   }
	   
	   /**Метод удаляет выбранный узел из дерева и базы данных.*/
	   private static void deleteNode()
	   {
		   TreePath path = tree.getSelectionPath();//Получаем путь к выбранному узлу дерева
		   if(path == null) 
		   {   
			   return;
		   }
		   DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode)path.getLastPathComponent();
		   
		   //Если выбран узел с заданием(без потомков)
		   if(!selectionNode.getAllowsChildren())
		   {	
			   int i = JOptionPane.showConfirmDialog(null, "Вы уверены что хотите удалить выбранное задание?",
					   "", JOptionPane.YES_NO_OPTION);
			   
			   if(i == 0)//Если удаление подтверждено
			   {
		           Task t = (Task)selectionNode.getUserObject();
		           themeManager.deleteTask(t.getTaskId());//Удаляем задание из базы данных
		           DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		           model.removeNodeFromParent(selectionNode);//Удаляем узел с заданием из дерева
		           taskDescriptionPanel.removeAll();//Очищаем панель с описанием задания
		           taskDescriptionPanel.updateUI(); 
			   }
		   }
		   
		   //Если выбран корневой узел
		   if(selectionNode.getParent() == null)
		   {}
		   else
		   {
			   //Если выбран узел с темой
			   if(selectionNode.getParent().getParent() == null)
			   {			       
				   int i = JOptionPane.showConfirmDialog(null, "Вы уверены что хотите удалить выбранную тему?",
						   "", JOptionPane.YES_NO_OPTION);
				   
				   if(i == 0)//Если удаление подтверждено
				   {
			           Theme t = (Theme)selectionNode.getUserObject();
			           themeManager.deleteTheme(t.getTheme_id());//Удаляем тему из базы данных
			           DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
			           model.removeNodeFromParent(selectionNode);//Удаляем узел с темой из дерева
			           taskDescriptionPanel.removeAll();//Очищаем панель с описанием темы
			           taskDescriptionPanel.updateUI(); 
				   }
			   }
			   else
			   {
			      //Если выбран узел с подтемой
			      if(selectionNode.getParent().getParent().getParent() == null)
			      {			       
			    	  int i = JOptionPane.showConfirmDialog(null, "Вы уверены что хотите удалить выбранную подтему?",
							   "", JOptionPane.YES_NO_OPTION);
					   
					   if(i == 0)//Если удаление подтверждено
					   {
				           Subtheme t = (Subtheme)selectionNode.getUserObject();
				           themeManager.deleteSubtheme(t.getSubtheme_id());//Удаляем подтему из базы данных
				           DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				           model.removeNodeFromParent(selectionNode);//Удаляем узел с подтемой из дерева				           
					   }
			      }
			   }
		   }
	   }
	   
	   /**Метод добавляет новый узел в дерево и базу данных.*/
	   private static void addNode()
	   {
		   TreePath path = tree.getSelectionPath();//Получаем путь к выбранному узлу дерева
		   if(path == null) 
		   {   
			   return;
		   }
		   DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode)path.getLastPathComponent();
		   DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		   
		   //Если выбран корневой узел
		   if(selectionNode.getParent() == null)
		   {			   
			   ThemeAddDialogPane panel = new ThemeAddDialogPane();
			   while(panel.isVisible())
			   {}
			   if(panel.isConfirm())//Если ввод данных подтверждён
			   {
				   String title = panel.getTitle();
				   String briefTheoreticalInformation = panel.getBriefTheoreticalInformation();
				   				  
				   Enumeration<DefaultMutableTreeNode> enu = selectionNode.children();
				   Boolean f = false;
				   while(enu.hasMoreElements())
				   {
					   Theme t = (Theme)enu.nextElement().getUserObject();
					   if(t.getTheme_title().equals(title))
					   {
						   f = true;						   
					   }
				   }
				   if(f)//Если тема с таким названием уже добавлена
				   {
					   JOptionPane.showMessageDialog(null, "Тема с таким названием уже есть"
							   , " ", JOptionPane.WARNING_MESSAGE);
				   }
				   else
				   {
					 //Добавляем тему в базу данных
					   Theme theme = themeManager.addTheme(title, briefTheoreticalInformation);
					   DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(theme);
					 //Добавляем узел с темой в дерево
			           model.insertNodeInto(newNode, selectionNode, selectionNode.getChildCount());
				   }
			   }
		   }
		   else
		   {
			   if(selectionNode.getParent().getParent() == null)//Если выбран узел с темой
			   {
				   SubthemeAddDialogPane panel = new SubthemeAddDialogPane();
				   while(panel.isVisible())
				   {}
				   if(panel.isConfirm())//Если ввод данных подтверждён
				   {
					   String title = panel.getTitle();				  
					   					  
					   Enumeration<DefaultMutableTreeNode> enu = selectionNode.children();
					   Boolean f = false;
					   while(enu.hasMoreElements())
					   {
						   Subtheme t = (Subtheme)enu.nextElement().getUserObject();
						   if(t.getSubtheme_title().equals(title))
						   {
							   f = true;						   
						   }
					   }
					   if(f)//Если подтема с таким названием уже добавлена
					   {
						   JOptionPane.showMessageDialog(null, "Подтема с таким названием уже есть"
								   , " ", JOptionPane.WARNING_MESSAGE);
					   }
					   else
					   {
						 //Добавляем тему в базу данных
						   Theme theme = (Theme)selectionNode.getUserObject();
						   Subtheme subtheme = themeManager.addSubtheme(title, theme.getTheme_id());
						   DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(subtheme);
						 //Добавляем узел с темой в дерево
				           model.insertNodeInto(newNode, selectionNode, selectionNode.getChildCount());
					   }
				   }				   
			   }
			   else//Если выбран узел с подтемой(для узлов с заданием пункт "добавить" не отображается)
			   {
				   TaskAddDialogPane panel = new TaskAddDialogPane();
				   while(panel.isVisible())
				   {}
				   if(panel.isConfirm())//Если ввод данных подтверждён
				   {
					   String answer = panel.getAnswer();
					   String description = panel.getDescription();
					   					  					  
				     //Добавляем задание в базу данных
					   Subtheme subtheme = (Subtheme)selectionNode.getUserObject();
					   Task task = themeManager.addTask(description, answer, subtheme.getSubtheme_id());
					   DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(task);
					   newNode.setAllowsChildren(false);
				     //Добавляем узел с заданием в дерево
				       model.insertNodeInto(newNode, selectionNode, selectionNode.getChildCount());					   
				   }			   
			   }
		   }
		   
	   }
}
