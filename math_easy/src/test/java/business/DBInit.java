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

/**Класс создаёт и инициализирует таблицы базы данных для целей последующего unit-тестирования.
@author Артемьев Р.А.
@version 28.04.2019 */
public class DBInit
{
	
	/**Метод считывает SQL-скрипты из файла для создания и инициализации таблиц базы данных.*/
    public static void startUp() throws Exception 
    {
        URL url = UserManagerTest.class.getClassLoader().getResource("math_easyDB.sql");
        //URL url = new URL("file://localhost/C://Users//Роман//git//MathEasyAdminApp//math_easy//resources//math_easyDB.sql");
         
        if(url!=null)
        {
        List<String> str = Files.readAllLines(Paths.get(url.toURI()));
        String sql = str.stream().collect(Collectors.joining());

        try (Connection con = ConnectionBuilderFactory.getConnectionBuilder().getConnection();
             Statement stmt = con.createStatement();) 
        {
            stmt.executeUpdate(sql);         
        }
        }
        else
        {
        	 System.out.println("URL не найден"); 
        }
    }
}
