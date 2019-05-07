package dao;

import java.util.List;
import java.util.Map;
import entity.Task;
import entity.Theme;


/**Интерфейс служит для определения функций хранилища данных о темах.
@author Артемьев Р.А.
@version 05.05.2019 */
public interface ThemeDAO 
{
	/**Получить объект класса Theme по его ID*/
    public Theme getTheme(Long id);
    /**Получить список тем*/
    public List<Theme> getThemeList();
    /**Получить отображение списка заданий*/
    public Map<Long, Task> getTaskMap();
    
}
