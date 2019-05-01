package entity;

/**Класс для хранения данных темы у пользователя.
@author Артемьев Р.А.
@version 01.05.2019 */
public class UserTheme 
{
	/**Идентификатор темы*/
    private Long theme_id;
    /**Название темы*/
    private String theme_title;
    /**Осталось решить заданий*/
    private int solve_task;
    /**Актуальна ли тема*/
    private boolean actual;
    /**Дата последнего решённого задания*/
    private String last_solved_task;
    
    /**Конструктор с параметрами
    @param theme_id идентификатор темы 
    @param theme_title название темы
    @param solve_task осталось решить заданий
    @param actual актуальна ли тема
    @param last_solved_task дата последнего решённого задания
    */
    
    /**Конструктор без параметров*/
    public UserTheme() 
    { }
    
    public UserTheme(Long theme_id, String theme_title, int solve_task, boolean actual, String last_solved_task ) 
    {
        this.theme_id = theme_id;
        this.theme_title = theme_title;
        this. solve_task = solve_task;  
        this.actual = actual;
        this.last_solved_task = last_solved_task;
    }
     
    public Long getTheme_id() 
    {
        return theme_id;
    }

    public void setTheme_id(Long theme_id) 
    {
        this.theme_id = theme_id;
    }

    public String getTheme_title() 
    {
        return theme_title;
    }

    public void setTheme_title(String theme_title) 
    {
        this.theme_title = theme_title;
    }
    
    public int getSolve_task() 
    {
        return solve_task;
    }

    public void setSolve_task(int solve_task) 
    {
        this.solve_task = solve_task;
    }
    
    public boolean getActual() 
    {
        return actual;
    }

    public void setActual(boolean actual) 
    {
        this.actual = actual;
    }
    
    public String getLast_solved_task() 
    {
        return last_solved_task;
    }

    public void setLast_solved_task(String last_solved_task) 
    {
        this.last_solved_task = last_solved_task;
    }
    
    @Override
    public String toString() 
    {
        return "UserTheme{" + "theme_id=" + theme_id + 
                ", theme_title=" + theme_title + 
                ", solve_task=" + solve_task + 
                ", actual=" + actual + 
                ", last_solved_task=" + last_solved_task + '}';
    }
}
