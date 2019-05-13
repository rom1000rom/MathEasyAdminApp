package entity;

import java.util.List;

/**Класс для хранения данных подтемы.
@author Артемьев Р.А.
@version 24.04.2019 */
public class Subtheme 
{
	/**Идентификатор подтемы*/
    private Long subtheme_id;
    /**Идентификатор темы*/
    private Long theme_id;
    /**Название подтемы*/
    private String subtheme_title;
    /**Список заданий*/
    private List<Task> listTask;
    
    /**Конструктор без параметров*/
    public Subtheme() 
    { }
    
    /**Конструктор с параметрами
    @param taskId идентификатор подтемы 
    @param subtheme_Id идентификатор темы
    @param subtheme_title название подтемы*/
    public Subtheme(Long subtheme_id, Long theme_id, String subtheme_title) 
    {
        this.subtheme_id = subtheme_id;
        this.theme_id = theme_id;
        this.subtheme_title = subtheme_title;      
    }

    public Long getSubtheme_id() 
    {
        return subtheme_id;
    }

    public void setSubtheme_id(Long subtheme_id) 
    {
        this.subtheme_id = subtheme_id;
    }

    public Long getTheme_id() 
    {
        return theme_id;
    }

    public void setTheme_id(Long theme_id)
    {
        this.theme_id = theme_id;
    }

    public String getSubtheme_title() 
    {
        return subtheme_title;
    }

    public void setSubtheme_title(String subtheme_title) 
    {
        this.subtheme_title = subtheme_title;
    }

    public List<Task> getTask() 
    {
        return listTask;
    }

    public void setTask(List<Task> listTask) 
    {
        this.listTask = listTask;
    }
    
    @Override
    public String toString() 
    {
        return subtheme_title;
    }
}
