package entity;

/**Класс для хранения данных о входе пользователя в программу.
@author Артемьев Р.А.
@version 03.05.2019 */
public class UserInput 
{	
    /**Дата входа*/
    private String inputDate;
    /**Заданий решено правильно*/
    private int[] tasksSolvedCorrectly;
    /**Заданий решено неправильно*/
    private int[] tasksSolvedInCorrectly;
   
    
    /**Конструктор без параметров*/
    public UserInput() 
    { }
    
    /**Конструктор с параметрами
    @param inputDate дата входа 
    @param tasksSolvedCorrectly заданий решено правильно
    @param tasksSolvedInCorrectly заданий решено неправильно
    */
    public UserInput(String inputDate, int[] tasksSolvedCorrectly, int[] tasksSolvedInCorrectly) 
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

    public int[] getTasksSolvedCorrectly() 
    {
        return tasksSolvedCorrectly;
    }

    public void setTasksSolvedCorrectly(int[] tasksSolvedCorrectly) 
    {
        this.tasksSolvedCorrectly = tasksSolvedCorrectly;
    }
    
    public int[] getTasksSolvedInCorrectly() 
    {
        return tasksSolvedInCorrectly;
    }

    public void setTasksSolvedInCorrectly(int[] tasksSolvedInCorrectly) 
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

