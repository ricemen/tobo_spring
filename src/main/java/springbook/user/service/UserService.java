package springbook.user.service;

import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService {
	UserDao userDao;
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMENT_FOR_GOLD = 30;
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	protected boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		
		switch(currentLevel) {
		case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER : return (user.getRecommend() >= MIN_RECCOMENT_FOR_GOLD);
		case GOLD : return false;
		default: throw new IllegalArgumentException("Unknow Level : " + currentLevel);
		}
	}
	
	protected void upgradeLevel(User user) {
//		if(user.getLevel() == Level.BASIC) user.setLevel(Level.SILVER);
//		else if(user.getLevel() == Level.SILVER) user.setLevel(Level.GOLD);
//		userDao.update(user);
		user.upgradLevel();
		userDao.update(user);
	}
	
	public void upgradeLevels() {
		
		List<User> users = userDao.getAll();
		
		for(User user : users) {
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
		
//		List<User> users = userDao.getAll();
//		
//		for(User user : users) {
//			
//			Boolean changed = false;
//			
//			if(user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
//				user.setLevel(Level.SILVER);
//				changed = true;
//			}
//			else if(user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
//				user.setLevel(Level.GOLD);
//				changed = true;
//			} 
//			
//			if(changed) userDao.update(user);
//		}
	}

	public void add(User user) {
		
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
		
	}
}
