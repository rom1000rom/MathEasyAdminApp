package business;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import connection.ConnectionBuilder;
import connection.ConnectionBuilderFactory;

/**Класс позволяет получить имя текущего метода.
@author Артемьев Р.А.
@version 23.06.2019 */
public class CurrentMethodName
{
	
	/**Метод позволяет получить имя текущего метода с именем класса к которому он относится.*/
    public static String getMethodName()  
    {  
    	StackTraceElement trace[] = new Throwable().getStackTrace();//Получаем текущий стек вызовов    	 
   	    StackTraceElement element = trace[1];
        return element.getMethodName() + "[" + element.getClassName() + "]";        
    }
}
