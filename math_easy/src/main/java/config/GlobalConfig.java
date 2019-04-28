package config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**Класс служит для работы с конфигурационным файлом. 
@author Артемьев Р.А.
@version 29.06.2018 */
public class GlobalConfig
{
	/**Имя конфигурационного файла*/
    private static final String CONFIG_NAME = "resources\\dao.properties";
    /**Свойство для работы с конфигурационным файлом*/
    private static final Properties GLOBAL_COFIG = new Properties();

    /**Метод делает начальную загрузку параметров из конфигурационного файла по умолчанию*/
    public static void initGlobalConfig() throws IOException 
    {
        initGlobalConfig(null);
    }

    /**Метод делает начальную загрузку параметров из конфигурационного файла, 
     * имя которого передано в виде параметра. Если имя null или пустое 
     * - берем файл по умолчанию.
    @param name  имя файла */
    public static void initGlobalConfig(String name) throws IOException 
    {
        if (name != null && !name.trim().isEmpty()) 
        {
            GLOBAL_COFIG.load(new FileReader(name));
        } 
        else 
        {
            GLOBAL_COFIG.load(new FileReader(CONFIG_NAME));
        }
    }

    /**Метод позволяет получить значение параметра из конфигурационного файла по имени.
    @param property имя параметра
    @return значение параметра*/
    public static String getProperty(String property) 
    {
        return GLOBAL_COFIG.getProperty(property);
    }
}