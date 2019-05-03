package gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.User;
import entity.UserTheme;


/**Класс представляет модель таблицы актуальных тем у пользователя.
@author Артемьев Р.А.
@version 29.04.2019 */
public class ActualThemeTableModel extends AbstractTableModel
{
	/**Количество столбцов в таблице*/
	   public static final int COLUMN_COUNT = 3;
	/**Список названий столбцов в таблице*/
    private static final String[] HEADERS = 
    {
        "ID",  "Название", "Решить заданий"
    };
	/**Список актуальных тем*/
    private List<UserTheme> actualTheme;
    
    public ActualThemeTableModel(List<UserTheme> actualTheme)
    {
    	this.actualTheme = actualTheme;
    }
    
	@Override
	public int getColumnCount() 
	{
		return COLUMN_COUNT;
	}

	@Override
	public int getRowCount() 
	{
		return actualTheme.size();
	}

	public Object getValueAt(int row, int col) 
	{
		UserTheme userTheme = actualTheme.get(row);
        // В зависимости от номера колонки возвращаем то или иное поле темы у пользователя
        switch (col) 
        {
            case 0:
                return userTheme.getTheme_id();
            case 1:
                return userTheme.getTheme_title();
            case 2:
                return userTheme.getSolve_task();        
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
