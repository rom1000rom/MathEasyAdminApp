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

/**Класс очищает базу от данных используемых для unit-тестирования.
@author Артемьев Р.А.
@version 22.06.2019 */
public class CleanDB
{
	
	/**Метод считывает SQL-скрипты из файла для очистки таблиц базы данных.*/
    public static void clean() throws Exception 
    {  
        //Получаем указатель на sql-скрипт
        URL url = new URL("file:\\C:\\Users\\Роман\\git\\MathEasyAdminApp\\math_easy\\resources\\cleanDB.sql");        
        
        List<String> str = Files.readAllLines(Paths.get(url.toURI()));
        String sql = str.stream().collect(Collectors.joining());
   
        try (Connection con = ConnectionBuilderFactory.getConnectionBuilder().getConnection();
               Statement stmt = con.createStatement();) 
        {
            stmt.executeUpdate(sql);      
        }
                
    }
}
