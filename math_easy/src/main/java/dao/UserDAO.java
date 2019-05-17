package dao;

import java.util.List;

import entity.User;
import exception.UserDaoException;

/**Интерфейс служит для определения функций хранилища данных о пользователях.
@author Артемьев Р.А.
@version 26.04.2019 */
public interface UserDAO 
{
	/**Получить объект класса User по его ID*/
    public User getUser(Long id);
    
    /**Получить список пользователей*/
    public List<User> getUserList();
    
    /**Удалить актуальную тему у пользователя*/
    public void deleteUserTheme(Long userId, Long themeId) throws UserDaoException;
    
    /**Добавляем актуальную тему пользователю*/
    public void addUserTheme(Long userId, Long themeId, int count) throws UserDaoException;
}
