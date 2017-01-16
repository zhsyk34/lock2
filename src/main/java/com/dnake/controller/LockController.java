package com.dnake.controller;

import com.dnake.common.Page;
import com.dnake.common.PageData;
import com.dnake.entity.Gateway;
import com.dnake.entity.Lock;
import com.dnake.exception.ControlException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/lock")
public class LockController extends CommonController {

	@RequestMapping("/find")
	@ResponseBody
	public PageData<Lock> find(String name, @RequestParam(value = "page", required = false, defaultValue = "1") int pageNo, @RequestParam(value = "rows", required = false, defaultValue = "10") int pageSize) {
		Page page = Page.of(pageNo, pageSize);
		List<Lock> list = lockService.findUsedList(name, SORT, page);
		int count = lockService.countUsed(name);
		return PageData.of(list, page, count);
	}

	//绑定1.进入对码状态
	@RequestMapping(value = "/enter")
	@ResponseBody
	public String enter(long gatewayId, String name) {
		Gateway gateway = gatewayService.find(gatewayId);
		if (gateway == null) {
			return null;
		}
		return commandService.enter(gateway.getUdid(), name);
	}

	//绑定2.启动入网状态,在此后进行绑定操作
	@RequestMapping(value = "/begin")
	@ResponseBody
	public boolean begin(String uuid) {
		return commandService.begin(uuid);
	}

	//绑定3.操作完后保存:退出对码状态后进行开锁测试,如果成功则启用并返回uuid,否则删除预存数据
	@RequestMapping(value = "/bind")
	@ResponseBody
	public boolean bind(String uuid) {
		try {
			return commandService.bind(uuid);
		} catch (ControlException e) {
			e.printStackTrace();
		}
		return false;
	}

	@RequestMapping("/update")
	@ResponseBody
	public boolean update(long id, String name) {
		Lock lock = lockService.findById(id);
		if (lock == null) {
			return false;
		}
		lock.setName(name);
		return lockService.update(lock) > 0;
	}

	//修改密码
	@RequestMapping("/word")
	@ResponseBody
	public boolean word(long id, int index, String value) {
		try {
			Lock lock = lockService.findById(id);
			return lock != null && commandService.word(lock.getUuid(), index, value);
		} catch (ControlException e) {
			e.printStackTrace();
		}
		return false;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(Long[] ids) {
		lockService.deleteByIds(ids);
		return true;
	}

	//控制
	@RequestMapping("/ctrl")
	@ResponseBody
	public boolean ctrl(String uuid, int type) {
		return type == 1 ? commandService.open(uuid) : commandService.close(uuid);
	}
}
