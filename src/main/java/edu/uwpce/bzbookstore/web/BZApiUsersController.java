package edu.uwpce.bzbookstore.web;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uwpce.bzbookstore.web.BZApiMessage.MsgType;

@RestController
@RequestMapping("/api")
public class BZApiUsersController {
   
	@Autowired
    private BZUsersManager usersManager;
          
    @RequestMapping(value="/users", method=RequestMethod.GET)
    public Map<Integer, BZUserInfo> getUsers(){
       	return usersManager.getUsers();
    }
    
    
    @RequestMapping(value="/users/{userid}", method=RequestMethod.GET)
    public Object getUser(@PathVariable("userid") int userid, HttpServletResponse response) {
    	BZUserInfo user = usersManager.getSingleUserById(userid);
    	if (user == null) {
            return new BZApiMessage(MsgType.ERROR, "User with id = " + userid + " does not exist.");
    	} else {
    		return user;
    	}
    }
    
    @RequestMapping(value="/users/{username}", method=RequestMethod.POST)
    public Object createUser(@RequestBody BZUserInfo newuser, @PathVariable("username") String username, HttpServletResponse response) {
		if (usersManager.getSingleUserByUsername(username) != null) {
            return new BZApiMessage(MsgType.ERROR, "User with username= " + username + " already exists.");
		} else {
			int newuserid = usersManager.setSingleUser(newuser);	
	        response.setStatus(HttpServletResponse.SC_CREATED);
			return usersManager.getSingleUserById(newuserid);
		}   	    	
    }
    
    
    @RequestMapping(value="/users/{userid}", method=RequestMethod.PUT)
    public Object updateUser(@RequestBody BZUserInfo userinfo, @PathVariable("userid") int userid){
    	usersManager.updateUser(userinfo);
       	BZUserInfo user = usersManager.getSingleUserById(userid);
    	if (user == null) {
            return new BZApiMessage(MsgType.ERROR, "User with id = " + userid + " not successfully updated.");
    	} else {
    		return user;
    	}
    }
    
    
    @RequestMapping(value="/users/{userid}", method=RequestMethod.DELETE)
    public Object deleteUser(@PathVariable("userid") int userid, HttpServletResponse response) {
    	if (usersManager.deleteUser(userid)){
    		return new BZApiMessage(MsgType.INFO, "User with id: " + userid + " has been deleted.");
    	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new BZApiMessage(MsgType.ERROR, "User with id: " + userid + " was not found.");
    	}
    }    
    
  
}
