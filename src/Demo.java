import java.sql.SQLException;
import java.util.List;

import ua.nure.zhmaka.Practice8.dto.Group;
import ua.nure.zhmaka.Practice8.dto.User;
import ua.nure.zhmaka.Practice8.util.DBManager;

public class Demo {
     
    private static <T> void printList(List<T> list) {
        for (T element : list) {
            System.out.println(element);
        }
    }
     
    public static void main(String[] args) throws SQLException {
        // users  ==> [ivanov]
		// groups ==> [teamA]
         
    	DBManager dbManager = DBManager.getInstance();      
         
        // Part 1
        dbManager.insertUser(User.createUser("petrov"));
        dbManager.insertUser(User.createUser("obama"));       
        printList(dbManager.findAllUsers());
        // users  ==> [ivanov, petrov, obama]
         
        System.out.println("===========================");
        
    	// Part 2
        dbManager.insertGroup(Group.createGroup("teamB"));
        dbManager.insertGroup(Group.createGroup("teamC"));        
        printList(dbManager.findAllGroups());
        // groups ==> [teamA, teamB, teamC]
         
        System.out.println("===========================");
        

		// Part 3
        User userPetrov = dbManager.getUser("petrov");
        User userIvanov = dbManager.getUser("ivanov");
        User userObama = dbManager.getUser("obama");
        
        System.out.println(userPetrov);
        System.out.println(userIvanov);
        System.out.println(userObama);
        
    	Group teamA = dbManager.getGroup("teamA");
        Group teamB = dbManager.getGroup("teamB");
        Group teamC = dbManager.getGroup("teamC");
        
        System.out.println(teamA);
        System.out.println(teamB);
        System.out.println(teamC);
        
        // method setGroupsForUser must implement transaction!
        dbManager.setGroupsForUser(userIvanov, teamA);
        dbManager.setGroupsForUser(userPetrov, teamA, teamB);
        dbManager.setGroupsForUser(userObama, teamA, teamB, teamC);
         
        for (User user : dbManager.findAllUsers()) {
            printList(dbManager.getUserGroups(user));
            System.out.println("~~~~~");
        }
        
        
    	// Part 4
        
        // on delete cascade!
        dbManager.deleteGroup(teamA);
         
        // Part 5
        teamC.setName("teamX");
        dbManager.updateGroup(teamC);
         
        // Part 6
        printList(dbManager.findAllGroups());
        // groups ==> [teamB, teamX]
       
         
        System.out.println("===========================");
         
    }
}