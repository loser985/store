package 工具;

import java.util.UUID;

public class UUIDUtils {
	public static String getId(){
		String id=UUID.randomUUID().toString().replace("-", "").toUpperCase();
		return id;
		
	}
	public static String getCode(){
		return getId();
	}
	public static void main(String[] args) {
		System.out.println(getId());
	}
}
