package gui;

import java.util.List;

import javax.swing.AbstractListModel;
import business.UserManager;
import dao.UserDAO;
import entity.User;

/**Класс представляет модель списка пользователей.
@author Артемьев Р.А.
@version 29.04.2019 */
public class UserListModel extends AbstractListModel<String>
{
	/**Cписок пользователей*/
	private  List<User> userList;
	
	public UserListModel(List<User> userList)
    {
    	this.userList = userList;
    }
	
	@Override
	public String getElementAt(int id) 
	{
		User us = userList.get(id);
		String str = us.getLastName() + " " + us.getFirstName();
		return str;
	}

	@Override
	public int getSize() 
	{
		return userList.size();
	}

}
