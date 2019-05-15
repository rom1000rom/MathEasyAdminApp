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

/**Класс представляет панель ввода данных о новой подтеме.
@author Артемьев Р.А.
@version 15.05.2019 */
public class SubthemeAddDialogPane extends JDialog 
{
	/**Название подтемы*/
	private String title = null;	
	/**Подтверждён ли ввод данных*/
	private Boolean confirm = false;
	
	/**Метка для ввода названия подтемы*/
    private final JLabel titleLabel = new JLabel();	
	/**Поле для ввода названия подтемы*/
    private final JTextField titleField = new JTextField();
    /**Кнопка для подтверждения ввода*/
    private final JButton okButton = new JButton("Сохранить") ;
    /**Кнопка для отмены ввода*/
    private final JButton cancelButton = new JButton("Отмена") ;
    
    /**Количество символов в поле ввода названия темы*/
    public static final int FIELD_COLUMNS = 15;    
    
    public SubthemeAddDialogPane() 
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
	      //Создаём метку для ввода названия
	      titleLabel.setText("Название подтемы:");
	      titleLabel.setFont(new Font(null, Font.BOLD, 14));
	      titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
	      northPanel.add(titleLabel);
	      titleField.setColumns(FIELD_COLUMNS);	      
	      titleField.setFont(new Font(null, Font.BOLD, 13));	     
	      northPanel.add(titleField);
	      add(northPanel, BorderLayout.NORTH);	      	                	
	    		  
          //Создаём и добавляем панель для кнопок
	      JPanel southPanel = new JPanel();	    		 	      
	      southPanel.add(okButton);//Добавляем кнопку подтверждения ввода
	      okButton.addActionListener(event ->
	      {	    	 
	    	  if(titleField.getText().equals(""))
	    	  {
	    		  JOptionPane.showMessageDialog(null, "Введите название подтемы"
						   , " ", JOptionPane.WARNING_MESSAGE);
	    	  }
	    	  else //Если введены необходимые данные
	    	  { 
	    		  title = titleField.getText();	    			  
	    		  confirm = true;
	    		  setVisible(false);   		 
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

    public String getTitle() 
    {
        return title;
    }
    
    public Boolean isConfirm() 
    {
        return confirm;
    }

}
