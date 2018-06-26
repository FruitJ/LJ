package com.YHStudio.dataChange;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年6月25日
 * 类的作用:测试数据类型转换
 */
public class Change {

	public static void main(String[] args) {
		// 数据类型 byte short int long float double String Integer Short Long Float Double
		
		/**
		 * 将String -> int
		 */
		String str = "100";
		int num = Integer.parseInt(str);
		System.out.println(num);
		String str1 = "101";
		int num1 = Integer.valueOf(str1).intValue();
		System.out.println(num1);
		
		/**
		 * 将int -> String
		 */
		// int num2 = 256;
		// String str2 = num2;// 报错:int不是类
		
		Integer num2 = 23;
		String str2 = num2.toString();// 只针对于被转换类型是int的包装类
		System.out.println(str2);
		
		int num3 = 28;
		String str3 = String.valueOf(num3);// 不管被转换类型是否是int型都可以
		System.out.println(str3);
		
		
		/**
		 * Java - >IEEE 754标准《二进制浮点数算法》导致的弊端
		 * 有些浮点数不能完全精确的表示出来
		 */
	
		System.out.println(0.1 + 0.2);
		
		/**
		 * a = a - 1;与a += 1;的区别
		 */
		short num4 = 4;
		// num4 = num4 - 1;
		num4 += 1;
		System.out.println(num4);
		
	}
}