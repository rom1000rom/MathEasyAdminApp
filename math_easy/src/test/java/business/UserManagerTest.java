package business;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import dao.*;
import entity.User;
import exception.UserDaoException;

public class UserManagerTest
{
	private static final String TEST_DATE1
    = "User{user_id=5, first_name=Петров, last_name=Пётр, school_class=10, school_number=381, date_of_registration=2018-03-17}";
	private static final String ADD_USER1
    = "INSERT INTO me_user( first_name, last_name, password, school_class, school_number, date_of_registration)\n" + 
    		"VALUES ( 'Петров', 'Пётр', 'rgb2', 10, 381, '2018-03-17'::date );";
	private static final String DELETE_USER1
    = "DELETE FROM me_user WHERE password LIKE 'rgb2';";
	@Ignore
	@BeforeClass
    public static void startUp() throws Exception 
	{
        DBInit.startUp();
    }
	@Ignore
    @Test
    public void testUser() throws UserDaoException 
    {
    	UserDAO dao = new UserDbDAO();
        User us = dao.getUser((long)1);
        Assert.assertTrue(us.toString() == TEST_DATE1);
    }

    
}