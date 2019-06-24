package entity;

import java.util.List;

/**Класс для хранения данных темы.
@author Артемьев Р.А.
@version 24.04.2019 */
public class Theme 
{
	/**Идентификатор темы*/
    private Long theme_id;
    /**Название темы*/
    private String theme_title;
    /**Краткая теоретическая справка*/
    private String brief_theoretical_information;
    /**Список подтем*/
    private List<Subtheme> listSubtheme;
    
    
    /**Конструктор без параметров*/
    public Theme() 
    { }
    
    /**Конструктор с параметрами
    @param theme_id идентификатор темы 
    @param theme_title название темы
    @param brief_theoretical_information краткая теоретическая справка*/
    public Theme(Long theme_id, String theme_title, String brief_theoretical_information) 
    {
        this.theme_id = theme_id;
        this.theme_title = theme_title;
        this.brief_theoretical_information = brief_theoretical_information;      
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

    public String getBrief_theoretical_information() 
    {
        return brief_theoretical_information;
    }

    public void setBrief_theoretical_information(String brief_theoretical_information) 
    {
        this.brief_theoretical_information = brief_theoretical_information;
    }
    
    public List<Subtheme> getSubtheme() 
    {
        return listSubtheme;
    }

    public void setSubtheme(List<Subtheme> listSubtheme) 
    {
        this.listSubtheme = listSubtheme;
    }
    
    @Override
    public String toString() 
    {
        return "Theme{" + "theme_id=" + theme_id + 
                ", theme_title=" + theme_title +'}';
    }
}
