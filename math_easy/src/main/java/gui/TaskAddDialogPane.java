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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import entity.Task;

/**Класс представляет панель ввода данных о новом задании.
@author Артемьев Р.А.
@version 15.05.2019 */
public class TaskAddDialogPane extends JDialog 
{
	/**Описание задания.*/
	private String description = null;
	/**Ответ на задание.*/
	private String answer = null;
	/**Подтверждён ли ввод данных.*/
	private Boolean confirm = false;
	
	/**Метка для ввода ответа*/
    private final JLabel answerLabel = new JLabel();	
	/**Поле для ввода ответа*/
    private final JTextField answerField = new JTextField();
    /**Поле для ввода описания задания*/
    private final JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
    /**Кнопка для подтверждения ввода*/
    private final JButton okButton = new JButton("Сохранить") ;
    /**Кнопка для отмены ввода*/
    private final JButton cancelButton = new JButton("Отмена") ;
    
    /**Количество символов в поле ввода ответа*/
    public static final int FIELD_COLUMNS = 15;
    /**Количество строк в поле ввода текста*/
    public static final int TEXTAREA_ROWS = 15;
    /**Количество символов в поле ввода текста*/
    public static final int TEXTAREA_COLUMNS = 25;
    /**Текст-подсказка в поле ввода описания задания*/
    public static final String DEFAULT_TEXT = "Описание задания";
    
    /**Конструктор без параметров*/
    public TaskAddDialogPane() 
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
          
          //Создаём и заполняем панель для ввода названия темы
          JPanel northPanel= new JPanel();
	      northPanel.setLayout(new GridLayout(1, 2));		      
	      //Создаём метку для ввода ответа
	      answerLabel.setText("Ответ:");
	      answerLabel.setFont(new Font(null, Font.BOLD, 15));
	      answerLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));	
	      northPanel.add(answerLabel);
	      answerField.setColumns(FIELD_COLUMNS);	      
	      answerField.setFont(new Font(null, Font.PLAIN, 15));	     
	      northPanel.add(answerField);
	      northPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
	      add(northPanel, BorderLayout.CENTER);	      
	      
          //Добавляем поле для ввода описания задания	      
	      textArea.setFont(new Font(null, Font.PLAIN, 14));
	      textArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	      textArea.setText(DEFAULT_TEXT);
	      textArea.setWrapStyleWord(true);
	      textArea.setLineWrap(true);
	      JScrollPane scrollPane = new JScrollPane(textArea);	    
	      add(scrollPane, BorderLayout.NORTH);	    	    	
	    		  
          //Создаём и добавляем панель для кнопок
	      JPanel southPanel = new JPanel();	    		 	      
	      southPanel.add(okButton);//Добавляем кнопку подтверждения ввода
	      okButton.addActionListener(event ->
	      {	    	 
	    	  if(answerField.getText().equals(""))
	    	  {
	    		  JOptionPane.showMessageDialog(null, "Введите ответ на задание"
						   , " ", JOptionPane.WARNING_MESSAGE);
	    	  }
	    	  else
	    	  {
	    		  if((textArea.getText().equals("")) || (textArea.getText().equals(DEFAULT_TEXT)))
		    	  {
		    		  JOptionPane.showMessageDialog(null, "Введите описание задания"
							   , " ", JOptionPane.WARNING_MESSAGE);
		    	  }
	    		  else//Если введены необходимые данные
	    		  {
	    			  answer = answerField.getText();
	    			  description = textArea.getText();
	    			  confirm = true;
	    			  setVisible(false);
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

    /**Конструктор с параметрами
     * @param oldDescription описание задания
     * @param oldAnswer ответ на задание*/
    public TaskAddDialogPane(String oldDescription, String oldAnswer) 
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
          
          //Создаём и заполняем панель для ввода названия темы
          JPanel northPanel= new JPanel();
	      northPanel.setLayout(new GridLayout(1, 2));		      
	      //Создаём метку для ввода ответа
	      answerLabel.setText("Ответ:");
	      answerLabel.setFont(new Font(null, Font.BOLD, 15));
	      answerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
	      northPanel.add(answerLabel);
	      answerField.setColumns(FIELD_COLUMNS);
	      answerField.setText(oldAnswer);
	      answerField.setFont(new Font(null, Font.PLAIN, 15));	     
	      northPanel.add(answerField);
	      northPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
	      add(northPanel, BorderLayout.CENTER);	      
	      
          //Добавляем поле для ввода описания задания	      
	      textArea.setFont(new Font(null, Font.PLAIN, 14));
	      textArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	      textArea.setText(oldDescription);	      
	      textArea.setLineWrap(true);
	      textArea.setWrapStyleWord(true);
	      JScrollPane scrollPane = new JScrollPane(textArea);	    
	      add(scrollPane, BorderLayout.NORTH);	    	    	
	    		  
          //Создаём и добавляем панель для кнопок
	      JPanel southPanel = new JPanel();	    		 	      
	      southPanel.add(okButton);//Добавляем кнопку подтверждения ввода
	      okButton.addActionListener(event ->
	      {	    	 
	    	  if(answerField.getText().equals(""))
	    	  {
	    		  JOptionPane.showMessageDialog(null, "Введите ответ на задание"
						   , " ", JOptionPane.WARNING_MESSAGE);
	    	  }
	    	  else
	    	  {
	    		  if(textArea.getText().equals(""))
		    	  {
		    		  JOptionPane.showMessageDialog(null, "Введите описание задания"
							   , " ", JOptionPane.WARNING_MESSAGE);
		    	  }
	    		  else//Если введены необходимые данные
	    		  {
	    			  answer = answerField.getText();
	    			  description = textArea.getText();
	    			  confirm = true;
	    			  setVisible(false);
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
    
    public String getAnswer() 
    {
        return answer;
    }
    
    public String getDescription() 
    {
        return description;
    }
    
    public Boolean isConfirm() 
    {
        return confirm;
    }

}
