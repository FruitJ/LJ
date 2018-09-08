package com.YHStudio.sql.controller;

import org.junit.Test;

import com.YHStudio.sql.entity.StaffBean;
import com.YHStudio.sql.service.DealDataService;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年9月7日
 * 类的作用:接收前台请求
 */
public class DealDataController {

	// 获取DealDataService类的实例
	private DealDataService dealData = new DealDataService();
	
	private String staff_name = "XXY";
	private int staff_age = 21;
	private int department_id = 3;
	private String sex = "女";
	private String phone = "17803218828";
	private String hobby = "看书";
	private int salary = 15000;
	
	@Test
	public void addStaffInfo() throws Exception {
		
		// 调用Service层的方法
		// 测试添加数据
		boolean bol = dealData.addInfo(new StaffBean(staff_name, staff_age, department_id, sex, phone, hobby, salary));
		// 测试更新数据
//		boolean bol = dealData.updateInfo((new StaffBean("AOP", staff_age, department_id, sex, phone, hobby, salary)));
		// 测试删除数据
//		boolean bol = dealData.deleteInfo((new StaffBean("AOP", 21, department_id, sex, phone, hobby, salary)));
		// 测试查询数据
//		boolean bol = dealData.queryInfo((new StaffBean("XXY", 21, department_id, sex, phone, hobby, salary)));
		if(bol) {
			System.out.println("请求转发页面 ...");
		}else {
			System.out.println("重定向页面 ...");
		}
		
	}
}
