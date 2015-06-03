package edu.uwpce.bzbookstore.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

public class BZUsersManagerImpl implements BZUsersManager {
	
    private static final Logger logger = LoggerFactory.getLogger(BZUsersManagerImpl.class);
	
	private static int USERID = 100;
	private Map<Integer, BZUserInfo> users;
		
	@Value("classpath:/defaultusers.json")
	private Resource defaultUsersResource;

/*	public BZUsersManagerImpl() {
		this.users = new ConcurrentHashMap<Integer, BZUserInfo>();
		logger.info("created new usersmanager, map size is: " + this.users.size());
	}
*/	
/*	@PostConstruct
	void init() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.users = mapper.readValue(defaultUsersResource.getInputStream(),
					new TypeReference<HashMap<String, BZUserInfo>>() {});
			logger.info("size of users in usersmanager after inputstream from json file: " + this.users.size());
		} catch (JsonParseException e) {
			logger.error("Got JSONParseException." + e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.error("Got JSONMappingException." + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Got IOException." + e);
			e.printStackTrace();
		}
	}
*/	

	
	@Override
	public Map<Integer, BZUserInfo> getUsers() {
		return users;
	}

	@Override
	public void setUsers(Map<Integer, BZUserInfo> users) {
		this.users = users;
		
	}

	@Override
	public BZUserInfo getSingleUserById(int userid) {
		BZUserInfo user = users.get(userid);
//		logger.info("user id in usersmanagerimpl is: " + user.getUserId());
		return user;
	}


	@Override
	public int setSingleUser(BZUserInfo user) {
		int userId = ++USERID;
		user.setUserId(userId);
		users.put(user.getUserId(), user);
		return userId;
		
	}

	@Override
	public BZUserInfo getSingleUserByUsername(String username) {
		BZUserInfo userresult = null;
		for (BZUserInfo user : users.values()) {
			logger.debug("in getsingleuserbyusername, username is: " + user.getUserName());
			if (user.getUserName().equals(username)){
				userresult = user;
			}
		}
		return userresult;
	}

	@Override
	public void updateUser(BZUserInfo userinfo) {
		BZUserInfo checkuser = this.getSingleUserById(userinfo.getUserId());
		if (checkuser != null){
				users.remove(checkuser.getUserId());
		}
		users.put(userinfo.getUserId(), userinfo);
	}

	@Override
	public boolean deleteUser(int userId) {
		boolean deleteSuccess = false;
		if (this.getSingleUserById(userId) != null) {
			users.remove(userId);
			--BZUsersManagerImpl.USERID;
			deleteSuccess = true;
		}
		return deleteSuccess;
	}
	

}
