package springbook.user.service;

import springbook.user.domain.User;

public interface UserLevelUpgradePolicy {
	public boolean canUpgradeLevel(User user);
	public void upgradeLevel(User user);
}
