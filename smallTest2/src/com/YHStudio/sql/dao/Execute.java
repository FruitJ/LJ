package com.YHStudio.sql.dao;

import java.sql.ResultSet;

import com.YHStudio.sql.entity.StaffBean;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年9月7日
 * 类的作用:定义相关功能
 */
public interface Execute {
	
	/**
	 * 定义添加员工信息的功能方法
	 * @param staff - 接收一个StaffBean类型的对象
	 * @return - 返回一个受影响行数
	 */
	public abstract int addStaffInfo(StaffBean staff);
	
	/**
	 * 定义更新员工信息的功能方法
	 * @param staff - 接收一个StaffBean类型的对象
	 * @return - 返回一个受影响行数
	 */
	public abstract int updateStaffInfo(StaffBean staff);
	
	/**
	 * 定义删除员工信息的功能方法
	 * @param staff - 接收一个StaffBean类型的对象
	 * @return - 返回一个受影响行数
	 */
	public abstract int deleteStaffInfo(StaffBean staff);

	/**
	 * 定义查询员工信息的功能方法
	 * @param staff - 接收一个StaffBean类型对象
	 * @return - 返回一个ResultSet集合/你指定的其它类型参数
	 */
	public abstract ResultSet queryStaffInfo(StaffBean staff);
	
}
