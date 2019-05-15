package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import entity.Task;

/**Класс представляет панель для отображения списка заданий.
@author Артемьев Р.А.
@version 08.05.2019 */
public class TaskDialogPane extends JDialog 
{
	/**Количество строк в поле ввода текста*/
    public static final int TEXTAREA_ROWS = 15;
    /**Количество символов в поле ввода текста*/
    public static final int TEXTAREA_COLUMNS = 22;
    
    public TaskDialogPane() 
    {}

    public TaskDialogPane( List<Task> taskList) 
    {     
    	//Задаём размеры и положение фрейма
	      Toolkit kit = Toolkit.getDefaultToolkit();
	      Dimension screenSize = kit.getScreenSize();
	      int screenHeight = screenSize.height + (screenSize.height/2);
	      int screenWidth = screenSize.width;	     	      
	      setLocation(screenWidth / 3, screenHeight / 6);
	      setTitle(" ");	      
        // Запрещаем изменение размеров
          setResizable(false);
          
          //Заполняем панель списком заданий
           setLayout(new BorderLayout());
		   //Выводим информацию о задании
		   JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);		   		   
		   textArea.setEditable(false);
		   textArea.setLineWrap(true);
		   for(Task task : taskList)
		   {
		      textArea.append("Задание№ " + task.getTaskId()  + "\n\n" +
				   "Описание задания: \n" + task.getDescription() + "\n\n" +
				   "Ответ: " + task.getAnswer() + "\n-------------------------------------------------\n");	
		   }
		   JScrollPane taskDescriptionScrollPane = new JScrollPane(textArea);
		   textArea.setFont(new Font(null, Font.BOLD, 13));
		   textArea.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));		   		   
		   add(taskDescriptionScrollPane, BorderLayout.NORTH);
          
           //Делаем форму видимой
		   pack();
           setVisible(true);
    }

}
