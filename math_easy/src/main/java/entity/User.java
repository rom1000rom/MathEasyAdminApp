package entity;

import java.util.List;

/**Класс для хранения данных пользователя.
@author Артемьев Р.А.
@version 27.04.2019 */
public class User 
{
	/**Идентификатор пользователя*/
    private Long user_id;
    /**Имя пользователя*/
    private String first_name;
    /**Фамилия пользователя*/
    private String last_name;
    /**Класс(школьный) пользователя*/
    private int school_class;
    /**Номер школы пользователя*/
    private int school_number;
    /**Дата регистрации пользователя*/
    private String date_of_registration;
    /**Список актуальных тем*/
    private List<UserTheme> actual_theme;
    /**Список неактуальных тем*/
    private List<UserTheme> not_actual_theme;
    
    
    
    /**Конструктор без параметров*/
    public User() 
    { }
    
    /**Конструктор с параметрами
    @param user_id идентификатор пользователя 
    @param first_name имя пользователя
    @param last_name фамилия пользователя
    @param school_class номер класса(в школе) пользователя
    @param school_number номер школы пользователя
    @param date_of_registration дата регистрации пользователя*/
    public User(Long user_id, String first_name, String last_name, int school_class, int school_number, String date_of_registration) 
    {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.school_class = school_class;
        this.school_number = school_number;
        this.date_of_registration = date_of_registration;
    }
    
    public Long getUserId() 
    {
        return user_id;
    }

    public void setUserId(Long user_id) 
    {
        this.user_id = user_id;
    }
    
    public String getFirstName() 
    {
        return first_name;
    }

    public void setFirstName(String first_name) 
    {
        this.first_name = first_name;
    }
    
    public String getLastName() 
    {
        return last_name;
    }

    public void setLastName(String last_name) 
    {
        this.last_name = last_name;
    }
    
    public int getSchoolClass() 
    {
        return school_class;
    }

    public void setSchoolClass(int school_class) 
    {
        this.school_class = school_class;
    }
    
    public int getSchoolNumber() 
    {
        return school_number;
    }

    public void setSchoolNumber(int school_number) 
    {
        this.school_number = school_number;
    }
    
    public String getDateOfRegistration() 
    {
        return date_of_registration;
    }

    public void setDateOfRegistration(String date_of_registration) 
    {
        this.date_of_registration = date_of_registration;
    }
    
    public List<UserTheme> getActualTheme() 
    {
        return actual_theme;
    }
    
    public void setActualTheme(List<UserTheme> actual_theme) 
    {
        this.actual_theme = actual_theme;
    }
    
    public List<UserTheme> getNotActualTheme() 
    {
        return not_actual_theme;
    }
    
    public void setNotActualTheme(List<UserTheme> not_actual_theme) 
    {
        this.not_actual_theme = not_actual_theme;
    }
    
    @Override
    public String toString() 
    {
        return "User{" + "user_id=" + user_id + 
                ", first_name=" + first_name + 
                ", last_name=" + last_name + 
                ", school_class=" + school_class + 
                ", school_number=" + school_number + 
                ", date_of_registration=" + date_of_registration +'}';
    }
}
