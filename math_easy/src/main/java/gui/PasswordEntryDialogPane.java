package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import config.GlobalConfig;
import entity.Task;

/**Класс представляет панель ввода и проверки пароля.
@author Артемьев Р.А.
@version 18.05.2019 */
public class PasswordEntryDialogPane extends JDialog 
{	
	/**Подтверждён ли доступ*/
	private Boolean accessConfirmed = false;
	
	/**Метка для ввода пароля*/
    private final JLabel passwordLabel = new JLabel();	
	/**Поле для ввода пароля*/    
    private final JPasswordField passwordField = new JPasswordField();
    /**Кнопка для подтверждения ввода*/
    private final JButton okButton = new JButton("Войти") ;
    /**Кнопка для отмены ввода*/
    private final JButton cancelButton = new JButton("Отмена");
    
    /**Количество символов в поле ввода пароля*/
    public static final int FIELD_COLUMNS = 15;    
    
    /**Конструктор без параметров*/
    public PasswordEntryDialogPane() 
    {   	  
    	  //Задаём  положение панели
	      Toolkit kit = Toolkit.getDefaultToolkit();
	      Dimension screenSize = kit.getScreenSize();
	      int screenHeight = screenSize.height + (screenSize.height/4);
	      int screenWidth = screenSize.width;
	      setLocation(screenWidth / 3, screenHeight / 4);
	      setTitle(" ");	      
          // Запрещаем изменение размеров
          setResizable(false);                      
          setLayout(new BorderLayout());
          setModal(true);
          
          //Создаём и заполняем панель для ввода пароля
          JPanel northPanel= new JPanel();
	      northPanel.setLayout(new GridLayout(1, 2));		      
	      //Создаём метку для ввода пароля
	      passwordLabel.setText("Введите пароль:");
	      passwordLabel.setFont(new Font(null, Font.BOLD, 15));
	      passwordLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
	      northPanel.add(passwordLabel);
	      passwordField.setColumns(FIELD_COLUMNS);	      
	      passwordField.setFont(new Font(null, Font.PLAIN, 15));	     
	      northPanel.add(passwordField);
	      add(northPanel, BorderLayout.NORTH);	      	                	
	    		  
          //Создаём и добавляем панель для кнопок
	      JPanel southPanel = new JPanel();	    		 	      
	      southPanel.add(okButton);//Добавляем кнопку подтверждения ввода
	      okButton.addActionListener(event ->
	      {	  
	    	  String str = new String(passwordField.getPassword());	    	  
	    	  if(str.toString().equals(""))
	    	  {
	    		  JOptionPane.showMessageDialog(null, "Введите пароль!"
						   , " ", JOptionPane.WARNING_MESSAGE);
	    	  }
	    	  else //Если введены необходимые данные
	    	  { 
	    		  if(str.equals(GlobalConfig.getProperty("sec.entry.password")))
	    		  {
	    		     accessConfirmed = true;
	    		     setVisible(false);
	    		  }
	    		  else
	    		  {
	    		     JOptionPane.showMessageDialog(null, "Пароль не верен!"
						   , " ", JOptionPane.WARNING_MESSAGE);	  
	    		  }
	    	  }
	      });
	      	      
	      southPanel.add(cancelButton);//Добавляем кнопку отмены ввода
	      cancelButton.addActionListener(event ->
	      {
	    	  setVisible(false);
	      });
	      
	      add(southPanel, BorderLayout.SOUTH);
	      
	      //Делаем панель видимой
	      pack() ;		  
          setVisible(true);
    }  
    
    /**Метод возвращает значение, говорящее о том, подтверждён ли доступ
     * @return true - значит подтверждён, false - не подтверждён.*/
    public Boolean isAccessConfirmed() 
    {
        return accessConfirmed;
    }

}
