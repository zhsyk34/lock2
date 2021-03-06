package com.dnake.controller;

import com.dnake.common.Page;
import com.dnake.common.PageData;
import com.dnake.common.ResultMsg;
import com.dnake.entity.Gateway;
import com.dnake.kit.ValidateKit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/gateway")
public class GatewayController extends CommonController {

	@RequestMapping("/list")
	@ResponseBody
	public List<Gateway> list() {
		return gatewayService.findList();
	}

	@RequestMapping("/find")
	@ResponseBody
	public PageData<Gateway> find(String sn, String name, @RequestParam(value = "page", required = false, defaultValue = "1") int pageNo, @RequestParam(value = "rows", required = false, defaultValue = "10") int pageSize) {
		Page page = Page.of(pageNo, pageSize);
		List<Gateway> list = gatewayService.findList(sn, name, SORT, page);
		int count = gatewayService.count(sn, name);
		return PageData.of(list, page, count);
	}

	@RequestMapping("/save")
	@ResponseBody
	public ResultMsg save(Gateway gateway) {
		if (ValidateKit.valid(gateway.getId())) {
			Gateway original = gatewayService.find(gateway.getId());
			if (original == null) {
				return ResultMsg.from(false, "网关不存在");
			}
			boolean r = gatewayService.update(original.setName(gateway.getName()).setVersion(gateway.getVersion())) > 0;
			return ResultMsg.from(r, r ? "修改成功" : "修改失败");
		}
		int r = gatewayService.save(gateway);
		return ResultMsg.from(r > 0, r > 0 ? "添加成功" : r == 0 ? "网关已存在" : "设备号不存在");
	}

	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(Long[] ids) {
		return gatewayService.deleteByIds(ids) > 0;
	}
}
