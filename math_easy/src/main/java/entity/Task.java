package entity;

/**Класс для хранения данных задания.
@author Артемьев Р.А.
@version 24.04.2019 */
public class Task 
{
	/**Идентификатор задания*/
    private Long taskId;
    /**Идентификатор подтемы*/
    private Long subtheme_Id;
    /**Описание задания*/
    private String description;
    /**Ответ*/
    private String answer;
    
    /**Конструктор без параметров*/
    public Task() 
    { }
    
    /**Конструктор с параметрами
    @param taskId идентификатор задания 
    @param subtheme_Id идентификатор подтемы
    @param description описание задания
    @param answer ответ*/
    public Task(Long taskId, Long subtheme_Id, String description, String answer) 
    {
        this.taskId = taskId;
        this.subtheme_Id = subtheme_Id;
        this.description = description;
        this.answer = answer;
    }

    public Long getTaskId() 
    {
        return taskId;
    }

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getSubtheme_Id() 
    {
        return subtheme_Id;
    }

    public void setSubtheme_Id(Long subtheme_Id)
    {
        this.subtheme_Id = subtheme_Id;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getAnswer() 
    {
        return answer;
    }

    public void setAnswer(String answer) 
    {
        this.answer = answer;
    }

    @Override
    public String toString() 
    {
        return "Task{" + "task_id=" + taskId + 
                ", subtheme_Id=" + subtheme_Id +'}';
    }
}

