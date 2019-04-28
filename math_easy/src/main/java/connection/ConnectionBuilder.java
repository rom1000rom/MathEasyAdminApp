package connection;

import java.sql.Connection;
import java.sql.SQLException;

/**Интерфейс служит для определения функций класса-поставщика соединений с базой данных.
@author Артемьев Р.А.
@version 24.04.2019 */
public interface ConnectionBuilder
{
	/**Метод позволяет получить соединение с базой данных.
    @return готовое соединение с базой данных*/
    Connection getConnection() throws SQLException;
}
