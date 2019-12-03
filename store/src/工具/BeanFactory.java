package 工具;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.faces.flow.builder.ReturnBuilder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sun.mail.handlers.image_gif;

public class BeanFactory {
	public static Object getBean(String id){
		//通过id获取一个指定的实现类
		
		try {
			//1.获取document对象
			Document doc=new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
			//2.获取指定的bean对象
			Element el=(Element) doc.selectSingleNode("//bean[@id='"+id+"']");
			//3.获取bean对象的class属性
			String value= el.attributeValue("class");
			//4.反射
			//return Class.forName(value).newInstance();
			Object	obj = Class.forName(value).newInstance();
			if(id.endsWith("Service")){
				Object proxyobj = Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces() ,new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if("add".equals(method.getName())||"regist".equals(method.getName())){
							System.out.println("添加数据");
							return method.invoke(obj, args);
						}
						return method.invoke(obj, args);
					}
				} );
				return proxyobj;
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(BeanFactory.getBean("CategoryService"));
	}

}
