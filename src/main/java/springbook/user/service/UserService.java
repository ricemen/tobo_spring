package springbook.user.service;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService {
	UserDao userDao;
	
	PlatformTransactionManager transactionManager;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMENT_FOR_GOLD = 30;
	
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
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			List<User> users = userDao.getAll();
			
			for(User user : users) {
				if(canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			transactionManager.commit(status);
		} catch(Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
	}

	public void add(User user) {
		
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
		
	}
}
