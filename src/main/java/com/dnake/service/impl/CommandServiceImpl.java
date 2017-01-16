package com.dnake.service.impl;

import com.dnake.dao.CommandDao;
import com.dnake.entity.Gateway;
import com.dnake.entity.Lock;
import com.dnake.entity.Word;
import com.dnake.exception.ControlException;
import com.dnake.service.CommandService;
import com.dnake.service.GatewayService;
import com.dnake.service.LockService;
import com.dnake.service.WordService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * number:设备号
 * index:密码编号
 */
@Service
public class CommandServiceImpl implements CommandService {

	private static final Logger logger = LoggerFactory.getLogger(CommandServiceImpl.class);

	@Resource
	private LockService lockService;
	@Resource
	private GatewayService gatewayService;
	@Resource
	private WordService wordService;
	@Resource
	private CommandDao commandDao;

	@Override
	public String enter(String udid, String name) {
		Gateway gateway = gatewayService.find(udid);
		if (gateway == null) {
			return null;
		}

		if (!commandDao.enter(udid)) {
			logger.error("进入对码失败");
			return null;
		}

		Long gatewayId = gateway.getId();
		int number = lockService.allocate(gatewayId);
		if (number == -1) {
			return null;
		}

		//预存
		Lock lock = lockService.findUnused(gatewayId, number);
		if (lock == null) {
			lock = new Lock().setGatewayId(gatewayId).setName(name).setNumber(number).setPermission(false);
			lockService.save(lock);
		} else {
			lock.setName(name);
			lockService.update(lock);
		}
		lock = lockService.findUnused(gatewayId, number);
		return lock.getUuid();
	}

	@Override
	public boolean begin(String udid, int number) {
		Gateway gateway = gatewayService.find(udid);
		if (gateway == null) {
			return false;
		}

		boolean result = commandDao.close(gateway.getUdid(), number);
		if (!result) {
			logger.error("尝试入网失败");
		}
		return result;
	}

	@Override
	public boolean begin(String uuid) {
		Target target = from(uuid);
		return target != null && this.begin(target.gateway.getUdid(), target.lock.getNumber());
	}

	@Override
	public boolean bind(String udid, int number) throws ControlException {
		Gateway gateway = gatewayService.find(udid);
		if (gateway == null) {
			return false;
		}

		boolean exit = commandDao.exit(udid);
		if (!exit) {
			logger.error("退出对码失败");
			return false;
		}

		Lock lock = lockService.findUnused(gateway.getId(), number);
		if (lock == null) {
			logger.error("找不到锁");
			return false;
		}

		Long lockId = lock.getId();

		//save lock info
		lockService.update(lock.setPermission(true));

		//save word info
		String password = "576838";//TODO:default word
		int index = 1;
		Word word = wordService.find(lockId, index);
		if (word == null) {
			word = new Word().setLockId(lockId).setNumber(index).setValue(password);
			wordService.save(word);
		} else {
			wordService.update(word.setValue(password));//reset manager word
		}

		// test to open
		boolean open = commandDao.open(udid, number);
		if (!open) {
			logger.debug("测试开锁失败");
			lockService.deleteById(lockId);
			throw new ControlException("开锁失败,取消绑定");
		}

		return true;
	}

	@Override
	public boolean bind(String uuid) throws ControlException {
		Target target = from(uuid);
		return target != null && this.bind(target.gateway.getUdid(), target.lock.getNumber());
	}

	@Override
	public boolean word(String udid, int number, int index, String value) throws ControlException {
		Gateway gateway = gatewayService.find(udid);
		if (gateway == null) {
			return false;
		}

		Lock lock = lockService.findUsed(gateway.getId(), number);//only used
		if (lock == null) {
			logger.error("找不到锁");
			return false;
		}

		Long lockId = lock.getId();
		Word word = wordService.find(lockId, index);
		if (word == null) {
			word = new Word().setLockId(lockId).setNumber(index).setValue(value);
			wordService.save(word);
		} else {
			wordService.update(word.setValue(value));
		}

		// ctrl
		boolean open = commandDao.word(udid, number, index, value);
		if (!open) {
			logger.debug("修改密码失败");
			throw new ControlException("修改密码失败");
		}

		return true;
	}

	@Override
	public boolean word(String uuid, int index, String value) throws ControlException {
		Target target = from(uuid);
		return target != null && this.word(target.gateway.getUdid(), target.lock.getNumber(), index, value);
	}

	@Override
	public boolean open(String udid, int number) {
		return commandDao.open(udid, number);
	}

	@Override
	public boolean open(String uuid) {
		Target target = from(uuid);
		return target != null && this.open(target.gateway.getUdid(), target.lock.getNumber());
	}

	@Override
	public boolean close(String udid, int number) {
		return commandDao.close(udid, number);
	}

	@Override
	public boolean close(String uuid) {
		Target target = from(uuid);
		return target != null && this.close(target.gateway.getUdid(), target.lock.getNumber());
	}

	@Override
	public boolean delete(String udid, int number, int index) throws ControlException {
		Gateway gateway = gatewayService.find(udid);
		if (gateway == null) {
			return false;
		}

		Lock lock = lockService.findUsed(gateway.getId(), number);
		if (lock == null) {
			logger.error("找不到锁");
			return false;
		}

		Long lockId = lock.getId();
		Word word = wordService.find(lockId, index);
		if (word != null) {
			wordService.deleteByEntity(word);
		}
		if (!commandDao.delete(udid, number, index)) {
			logger.error("删除密码失败");
			throw new ControlException("删除密码失败");
		}

		return true;
	}

	@Override
	public boolean delete(String uuid, int index) throws ControlException {
		Target target = from(uuid);
		return target != null && this.delete(target.gateway.getUdid(), target.lock.getNumber(), index);
	}

	private Target from(String uuid) {
		Lock lock = lockService.find(uuid);
		if (lock == null) {
			return null;
		}
		Gateway gateway = gatewayService.find(lock.getGatewayId());
		if (gateway == null) {
			return null;
		}

		return Target.of(gateway, lock);
	}

	@Getter
	@RequiredArgsConstructor(staticName = "of")
	private static class Target {
		private final Gateway gateway;
		private final Lock lock;

	}

}
