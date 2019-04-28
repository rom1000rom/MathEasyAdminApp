package exception;


/**Класс исключения, возникающего при работе класса UserManager.
@author Артемьев Р.А.
@version 27.04.2019 */
public class UserBusinessException extends Exception
{
    public UserBusinessException() 
    {
    }

    public UserBusinessException(String message) 
    {
        super(message);
    }

    public UserBusinessException(Throwable cause) 
    {
        super(cause);
    }

    public UserBusinessException(String message, Throwable cause) 
    {
        super(message, cause);
    }
}
