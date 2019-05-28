import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import com.demo.model.FoodGroup;
import com.demo.model.FoodGroupDAO;

public class App {
	
	public static void main(String[] args) {
		ApplicationContext appContext = new FileSystemXmlApplicationContext("appContext.xml");
		
		try {
			FoodGroupDAO myFoodGroupDAO = appContext.getBean("foodGroupDAO",FoodGroupDAO.class);
			
			FoodGroup myFoodGroup1 = new FoodGroup("Test", "test");
			FoodGroup myFoodGroup2 = new FoodGroup("1111", "1111");
			FoodGroup myFoodGroup3 = new FoodGroup("2222", "2222");
			FoodGroup myFoodGroup1a = new FoodGroup("Test", "test");
			
			List<FoodGroup> myList = new ArrayList<FoodGroup>();
			myList.add(myFoodGroup1);
			myList.add(myFoodGroup2);
			myList.add(myFoodGroup3);
			myList.add(myFoodGroup1a);
			
			int[] numAffectedRowsArray = myFoodGroupDAO.createFoodGroups(myList);
			
			for (int i : numAffectedRowsArray) {
				System.out.println("updated rows: " + i);
			}
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getClass());
		}
		
//		try {
//			FoodGroupDAO myFoodGroupDAO = appContext.getBean("foodGroupDAO",FoodGroupDAO.class);
//			
//			List<FoodGroup> myFoodGroupList = myFoodGroupDAO.getFoodGroups();
//			
//			for (FoodGroup fg: myFoodGroupList) {
//				System.out.println(fg.talkAboutYourself());	
//			}
//		} catch (DataAccessException e) {
//			System.out.println(e.getMessage());
//			System.out.println(e.getClass());
//		}
		
		
		
//		FoodGroup myFoodGroup = myFoodGroupDAO.getFoodGroup(12);
//		System.out.println(myFoodGroup.talkAboutYourself());
		
		//myFoodGroupDAO.addFoodGroup("jebnenie", "elo delo 320");
		
//		FoodGroup myFoodGroup = new FoodGroup("1231", "123123123");
//		myFoodGroupDAO.addFoodGroup(myFoodGroup);
		
//		FoodGroup myFoodGroup = new FoodGroup("Updated name", "Updated description");
//		myFoodGroupDAO.updFoodGroup(myFoodGroup);
		
//		myFoodGroupDAO.delFoodGroup(6);
		
		((FileSystemXmlApplicationContext) appContext).close();
	}

}
