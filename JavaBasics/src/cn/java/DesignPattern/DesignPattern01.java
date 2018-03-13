package cn.java.DesignPattern;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 这要从JDK上去看设计模式（一）:Creation Patterns
 * 通过阅读源码学习JAVA设计模式
 * @author Xu
 *
 */
class DesignPattern01 {
	
	/**
	 * 单例模式：保证类只有一个实例，提供一个全局访问点
	 */
	public void Singleton() {
		//是否Lazy初始化:类加载时就生成还是需要时生成
		//线程安全：volatile,synchronized，锁
		//
		java.lang.Runtime.getRuntime();
		java.awt.Desktop.getDesktop();
		java.lang.System.getSecurityManager();
	}
	
	/**
	 * 静态工厂模式：1.代替构造函数创建对象；2.方法名比构造函数清晰
	 */
	public void StaticFactory() throws Exception {
		java.lang.Class.forName("string");
		java.lang.reflect.Array.newInstance(String.class, 2);
	}
	
	/**
	 * 抽象工厂：创建一组相关对象实例
	 */
	public void abstractFactory() throws Exception{
		 Connection conn = null; 
		 conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/xjgs808", "root", "1qaz2wsx"); 
		 Statement st = (Statement) conn.createStatement(); 
	}
}


