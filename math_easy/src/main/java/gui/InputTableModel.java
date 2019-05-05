package gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.User;
import entity.UserInput;
import entity.UserTheme;


/**Класс представляет модель таблицы входов пользователя в программу.
@author Артемьев Р.А.
@version 04.05.2019 */
public class InputTableModel extends AbstractTableModel
{
	/**Количество столбцов в таблице*/
	   public static final int COLUMN_COUNT = 3;
	/**Список названий столбцов в таблице*/
    private static final String[] HEADERS = 
    {
        "Дата входа",  "Решено правильно", "Решено неправильно"
    };
	/**Список входов пользователя в программу*/
    private List<UserInput> userInput;
    
    public InputTableModel(List<UserInput> userInput)
    {
    	this.userInput = userInput;
    }
    
	@Override
	public int getColumnCount() 
	{
		return COLUMN_COUNT;
	}

	@Override
	public int getRowCount() 
	{
		return userInput.size();
	}

	public Object getValueAt(int row, int col) 
	{
		UserInput userIn = userInput.get(row);
        // В зависимости от номера колонки возвращаем то или иное поле темы у пользователя
        switch (col) 
        {
            case 0:
                return userIn.getInputDate();
            case 1:
            	Integer[] arrCor = userIn.getTasksSolvedCorrectly();
            	String strCor = new String("");
            	if(arrCor != null)
            	{
            		for(int i = 0; i < arrCor.length; i++)          	
            	    {
            		   strCor = strCor + (arrCor[i] + " ");
            	    }
            	}
                return strCor;
            case 2:
            	Integer[] arrInCor = userIn.getTasksSolvedInCorrectly();
            	String strInCor = new String("");
            	if(arrInCor != null)
            	{
            		for(int i = 0; i < arrInCor.length; i++)          	
            	    {
            			strInCor = strInCor + (arrInCor[i] + " ");
            	    }
            	}
                return strInCor;       
            default:
                return "";
        }
	}
	
	@Override
    // Вернуть загловок колонки - мы его берем из массива headers
    public String getColumnName(int col) 
	{
        return HEADERS[col];
    }
}
