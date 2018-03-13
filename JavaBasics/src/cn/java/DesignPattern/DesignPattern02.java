package cn.java.DesignPattern;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 这要从JDK上去看设计模式（二）:Structural Patterns
 * 通过阅读源码学习JAVA设计模式
 * @author Xu
 *
 */
public class DesignPattern02 {
	
	/**
	 * 适配器模式：使不兼容的接口相容，把一个接口或是类变成另外一种
	 * (recognizeable by creational methods 
	 * taking an instance of different abstract/interface type 
	 * and returning an implementation of own/another abstract/interface type 
	 * which decorates/overrides the given instance
	 */
	public void Adapter() throws Exception {
		//javax.swing.JTable(TableModel.class);
		//javax.swing.JList(ListModel);
		//java.io.InputStreamReader(InputStream);
		//java.io.OutputStreamWriter(OutputStream);
		
		//读取字节流  
        //InputStream in = System.in;//读取键盘的输入。  
        InputStream in = new FileInputStream("D:\\demo.txt");//读取文件的数据。  
        //将字节流向字符流的转换。要启用从字节到字符的有效转换，  
        //可以提前从底层流读取更多的字节.  
        InputStreamReader isr = new InputStreamReader(in);//读取  
	}
	
	/**
	 * 组合：一致对待组合对象和独立对象
	 * recognizeable by behavioral methods 
	 * taking an instance of same abstract/interface type into a tree structure)
	 */
	public void Composite() throws Exception {

		//javax.swing.JComponent.add(Component);
	}
	
	/**
	 * 装饰器模式：装饰后添加新功能，防止类继承带来的爆炸式增长
	 * 为一个对象动态加上一系列动作，而不需要因为这些动作不同而产生大量继承类
	 * taking an instance of same abstract/interface type which adds additional behaviour)
	 */
	public void Decorator() throws Exception {
		InputStream in = new FileInputStream("D:\\demo.txt");
		
		BufferedInputStream bf = new java.io.BufferedInputStream(in);
		DataInputStream df = new java.io.DataInputStream(in);
		//BufferedOutputStream bof = new java.io.BufferedOutputStream(out);
	}

}
