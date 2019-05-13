package dao;

import java.util.List;
import java.util.Map;
import entity.Task;
import entity.Theme;
import exception.ThemeDaoException;


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
    /**Удалить задание по его идентификационному номеру*/
    public void deleteTask(Long id) throws ThemeDaoException;
    /**Удалить тему по её идентификационному номеру*/
    public void deleteTheme(Long id) throws ThemeDaoException;
    /**Удалить подтему по её идентификационному номеру*/
    public void deleteSubtheme(Long id) throws ThemeDaoException;
}
