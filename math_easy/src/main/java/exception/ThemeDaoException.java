package exception;

/**Класс исключения, возникающего при работе типа ThemeDAO.
@author Артемьев Р.А.
@version 05.05.2019 */
public class ThemeDaoException extends Exception
{
    public ThemeDaoException() 
    { }

    public ThemeDaoException(String message) 
    {
        super(message);
    }

    public ThemeDaoException(Throwable cause) 
    {
        super(cause);
    }

    public ThemeDaoException(String message, Throwable cause) 
    {
        super(message, cause);
    }
}
