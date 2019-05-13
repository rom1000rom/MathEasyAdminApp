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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import entity.Task;

/**Класс представляет панель ввода данных о новой теме.
@author Артемьев Р.А.
@version 13.05.2019 */
public class ThemeAddDialogPane extends JDialog 
{
	/**Название темы*/
	private String title = null;
	/**Краткая теоретическая справка*/
	private String briefTheoreticalInformation = null;
	
	/**Метка для ввода названия темы*/
    private final JLabel titleLabel = new JLabel();	
	/**Поле для ввода названия темы*/
    private final JTextField titleField = new JTextField();
    /**Поле для ввода краткой теоретической справки о теме*/
    private final JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
    /**Кнопка для подтверждения ввода*/
    private final JButton okButton = new JButton("OK") ;
    /**Кнопка для отмены ввода*/
    private final JButton cancelButton = new JButton("Cancel") ;
    
    /**Количество символов в поле ввода названия темы*/
    public static final int FIELD_COLUMNS = 15;
    /**Количество строк в поле ввода текста*/
    public static final int TEXTAREA_ROWS = 8;
    /**Количество символов в поле ввода текста*/
    public static final int TEXTAREA_COLUMNS = 20;
    
    
    
    public ThemeAddDialogPane() 
    {   	  
    	  //Задаём  положение панели
	      Toolkit kit = Toolkit.getDefaultToolkit();
	      Dimension screenSize = kit.getScreenSize();
	      int screenHeight = screenSize.height + (screenSize.height/4);
	      int screenWidth = screenSize.width;
	      setLocation(screenWidth / 3, screenHeight / 4);
	      setTitle("Описание темы");	      
          // Запрещаем изменение размеров
          setResizable(false);                      
          setLayout(new BorderLayout());
          setModal(true);
          
          //Создаём и заполняем панель для ввода названия темы
          JPanel northPanel= new JPanel();
	      northPanel.setLayout(new GridLayout(1, 2));		      
	      //Создаём метку для ввода названия
	      titleLabel.setText("Название темы:");
	      titleLabel.setFont(new Font(null, Font.BOLD, 14));
	      titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
	      northPanel.add(titleLabel);
	      titleField.setColumns(FIELD_COLUMNS);	      
	      titleField.setFont(new Font(null, Font.BOLD, 13));	     
	      northPanel.add(titleField);
	      add(northPanel, BorderLayout.NORTH);	      
	      
          //Добавляем поле для ввода краткой теоретической справки	      
	      textArea.setFont(new Font(null, Font.PLAIN, 13));
	      textArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	      textArea.setText("Краткая теоретическая справка");
	      textArea.setLineWrap(true);
	      JScrollPane scrollPane = new JScrollPane(textArea);	    
	      add(scrollPane, BorderLayout.CENTER);	    	    	
	    		  
          //Создаём и добавляем панель для кнопок
	      JPanel southPanel = new JPanel();	    		 	      
	      southPanel.add(okButton);
	      okButton.addActionListener(event ->
	      {
	    	  
	      });
	      	      
	      southPanel.add(cancelButton);
	      cancelButton.addActionListener(event ->
	      {
	    	  
	      });
	      
	      add(southPanel, BorderLayout.SOUTH);
	      
	      //Делаем панель видимой
	      pack() ;		  
          setVisible(true);
    }

    

}
