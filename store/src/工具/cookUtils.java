package 工具;

import javax.servlet.http.Cookie;


public class cookUtils {
	public static Cookie getCookieByName(String id ,Cookie[] cookies){
		if(cookies!=null){
			for (Cookie c : cookies) {
				if(id.equals(c.getName())){
					return c;
				}
			}
		}
		return null;
	}
}
