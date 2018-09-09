package com.YHStudio.SpringJ.SpringIOC;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年6月25日
 * 类的作用:创建IOC容器的接口
 */
public interface ApplicationContext {
	
	public abstract Object getBean(String beanName);
}
