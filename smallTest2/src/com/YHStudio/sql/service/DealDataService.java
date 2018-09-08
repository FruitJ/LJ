package com.YHStudio.sql.service;

import java.sql.ResultSet;

import com.YHStudio.sql.dao.Execute;
import com.YHStudio.sql.entity.StaffBean;
import com.YHStudio.sql.utils.SqlSessionFactory;

/**
 * @author 刘杰
 * @version 1.0 2018年9月7日 
 * 类的作用:处理数据
 */
public class DealDataService {

	// 添加员工信息
	public boolean addInfo(StaffBean staff) {
		// Execute实现类对象
		Execute execute = (Execute) SqlSessionFactory.getSession(Execute.class);
		// 执行添加员工信息操作
		int num = execute.addStaffInfo(staff);
		System.out.println("受影响行数:" + num);
		if (num > 0) {// 如果添加成功
			return true;
		}
		return false;// 未添加成功
	}

	// 更新员工信息
	public boolean updateInfo(StaffBean staff) {
		// Execute实现类对象
		Execute execute = (Execute) SqlSessionFactory.getSession(Execute.class);
		// 执行更新员工信息操作
		int num = execute.updateStaffInfo(staff);
		System.out.println("受影响行数:" + num);
		if (num > 0) {// 如果添加成功
			return true;
		}
		return false;// 未添加成功
	}

	// 删除员工信息
	public boolean deleteInfo(StaffBean staff) {
		// Execute实现类对象
		Execute execute = (Execute) SqlSessionFactory.getSession(Execute.class);
		// 执行删除员工信息操作
		int num = execute.deleteStaffInfo(staff);
		System.out.println("受影响行数:" + num);
		if (num > 0) {// 如果添加成功
			return true;
		}
		return false;// 未添加成功
	}

	// 查询员工信息
	public boolean queryInfo(StaffBean staff) throws Exception {
		// Execute实现类对象
		Execute execute = (Execute) SqlSessionFactory.getSession(Execute.class);
		// 执行查询员工信息操作
		ResultSet res = execute.queryStaffInfo(staff);
		System.out.println("res:");
		System.out.println("res:");
		while (res.next()) {
			System.out.println(res.getString("staff_name"));
			System.out.println(res.getInt("staff_age"));
			System.out.println(res.getString("hobby"));
		}
		if (res.next()) {// 如果添加成功
			return true;
		}
		return false;// 未添加成功
	}
}
