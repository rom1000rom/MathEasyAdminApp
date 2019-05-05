package entity;

import java.util.List;

/**Класс для хранения данных о входе пользователя в программу.
@author Артемьев Р.А.
@version 03.05.2019 */
public class UserInput 
{	
    /**Дата входа*/
    private String inputDate;
    /**Заданий решено правильно*/
    private Integer[] tasksSolvedCorrectly;
    /**Заданий решено неправильно*/
    private Integer[] tasksSolvedInCorrectly;
   
    
    /**Конструктор без параметров*/
    public UserInput() 
    { }
    
    /**Конструктор с параметрами
    @param inputDate дата входа 
    @param tasksSolvedCorrectly заданий решено правильно
    @param tasksSolvedInCorrectly заданий решено неправильно
    */
    public UserInput(String inputDate, Integer[] tasksSolvedCorrectly, Integer[] tasksSolvedInCorrectly) 
    {
        this.inputDate = inputDate;
        this.tasksSolvedCorrectly = tasksSolvedCorrectly;
        this.tasksSolvedInCorrectly = tasksSolvedInCorrectly;  
    }
     
    public String getInputDate() 
    {
        return inputDate;
    }

    public void setInputDate(String inputDate) 
    {
        this.inputDate = inputDate;
    }

    public Integer[] getTasksSolvedCorrectly() 
    {
        return tasksSolvedCorrectly;
    }

    public void setTasksSolvedCorrectly(Integer[] tasksSolvedCorrectly) 
    {
        this.tasksSolvedCorrectly = tasksSolvedCorrectly;
    }
    
    public Integer[] getTasksSolvedInCorrectly() 
    {
        return tasksSolvedInCorrectly;
    }

    public void setTasksSolvedInCorrectly(Integer[] tasksSolvedInCorrectly) 
    {
        this.tasksSolvedInCorrectly = tasksSolvedInCorrectly;
    }
    
    @Override
    public String toString() 
    {
        return "UserInput{" + "inputDate=" + inputDate + 
                ", tasksSolvedCorrectly=" + tasksSolvedCorrectly + 
                ", tasksSolvedInCorrectly=" + tasksSolvedInCorrectly + '}';
    }
}

