package com.YHStudio.SpringJ.SpringService;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年6月25日
 * 类的作用:创建说书的service
 */
public class TalkBookService {

	private int age;
	private SeeBookService seeBook;
	
	// 创建无参构造器
	public TalkBookService() {
		super();
	}
	// 创建有参构造器
	public TalkBookService(int age, SeeBookService seeBook) {
		this.age = age;
		this.seeBook = seeBook;
	}
	
	// 创建对应的setter、getter方法
	public void setAge(int age) {
		this.age = age;
	}
	public int getAge() {
		return age;
	}
	public void setSeeBook(SeeBookService seeBook) {
		this.seeBook = seeBook;
	}
	public SeeBookService getSeeBook() {
		return seeBook;
	}
	@Override
	public String toString() {
		return "TalkBook [age=" + age + ", seeBook=" + seeBook + "]";
	}
	
	public void testSeeBook() {
		seeBook.seeBook();
	}
}
