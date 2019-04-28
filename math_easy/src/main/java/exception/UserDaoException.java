package exception;

/**Класс исключения, возникающего при работе класса UserDAO.
@author Артемьев Р.А.
@version 27.04.2019 */
public class UserDaoException extends Exception
{
    public UserDaoException() 
    { }

    public UserDaoException(String message) 
    {
        super(message);
    }

    public UserDaoException(Throwable cause) 
    {
        super(cause);
    }

    public UserDaoException(String message, Throwable cause) 
    {
        super(message, cause);
    }
}
