package com.YHStudio.sql.entity;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年9月7日
 * 类的作用:创建Staff表的实体类
 */
public class StaffBean {

	private String staff_name;
	private int staff_age;
	private int department_id;
	private String sex;
	private String phone;
	private String hobby;
	private int salary;
	
	// 创建无参构造器
	public StaffBean() {}
	
	// 创建有参构造器
	public StaffBean(String staff_name, int staff_age, int department_id, String sex, String phone, String hobby,
			int salary) {
		this.staff_name = staff_name;
		this.staff_age = staff_age; 
		this.department_id = department_id;
		this.sex = sex;
		this.phone = phone;
		this.hobby = hobby;
		this.salary = salary;
	}

	// 创建对应的setter与getter
	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public int getStaff_age() {
		return staff_age;
	}

	public void setStaff_age(int staff_age) {
		this.staff_age = staff_age;
	}

	public int getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	// toString方法
	@Override
	public String toString() {
		return "StafInfoBean [staff_name=" + staff_name + ", staff_age=" + staff_age + ", department_id="
				+ department_id + ", sex=" + sex + ", phone=" + phone + ", hobby=" + hobby + ", salary=" + salary + "]";
	}
}
