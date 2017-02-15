package springbook.user.service;

import java.util.List;


import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserServiceImpl implements UserService {
	UserDao userDao;
	
	private MailSender mailSender; 

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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
		user.upgradLevel();
		userDao.update(user);
		sendUpgradeEMail(user);
	}
	
	private void sendUpgradeEMail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("useradmin@ksug.org");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + " 로 업그레이드 되었습니다.");
		mailSender.send(mailMessage);
	}
	
	public void upgradeLevels() {
		
		List<User> users = userDao.getAll();
		
		for(User user : users) {
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
		
//		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//		
//		try {
//			upgradeLevelsInternal();
//			transactionManager.commit(status);
//		} catch(Exception e) {
//			transactionManager.rollback(status);
//			throw e;
//		}
	}
	
	private void upgradeLevelsInternal() {
		List<User> users = userDao.getAll();
		for(User user : users) {
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}

	public void add(User user) {
		
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
		
	}

	@Override
	public User get(String id) {
		return userDao.get(id);
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public void deleteAll() {
		userDao.deleteAll();
	}

	@Override
	public void update(User user) {
		userDao.update(user);
		
	}
}
