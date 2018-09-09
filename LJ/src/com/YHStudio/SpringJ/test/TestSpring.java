package com.YHStudio.SpringJ.test;

import com.YHStudio.SpringJ.SpringIOC.ApplicationContext;
import com.YHStudio.SpringJ.SpringIOC.ClassPathXMLApplicationContext;
import com.YHStudio.SpringJ.SpringService.BuyBookService;
import com.YHStudio.SpringJ.SpringService.SeeBookService;
import com.YHStudio.SpringJ.SpringService.SellBookService;
import com.YHStudio.SpringJ.SpringService.TalkBookService;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年6月25日
 * 类的作用:测试创建SpringIOC容器
 */
public class TestSpring {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXMLApplicationContext("applicationContext.xml");
		SeeBookService seeBook = (SeeBookService) ctx.getBean("seeBook");
		BuyBookService buyBook = (BuyBookService) ctx.getBean("buyBook");
		SellBookService sellBook = (SellBookService) ctx.getBean("sellBook");
		TalkBookService talkBook = (TalkBookService) ctx.getBean("talkBook");
		
		buyBook.see();
		seeBook.seeBook();
		
		System.out.println(sellBook);
		sellBook.testInjection();
		System.out.println(talkBook);
		talkBook.testSeeBook();
	}
}
