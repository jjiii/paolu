package jj.tech.paolu.utils;

import java.io.UnsupportedEncodingException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
	private static final String DEFAULT_PATH =  "/";
	private static final int DEFAULT_AGE = -1;
	
	private static String path = DEFAULT_PATH;
	private static int age = DEFAULT_AGE;
	
	/**
	 * 添加cookie
	 * @param name
	 * @param value
	 * @param response
	 * @param maxAge
	 * @throws UnsupportedEncodingException
	 */
	public static void add(String name, String value,HttpServletResponse response){
		try {
//			Cookie cookie = new Cookie(name,URLEncoder.encode(value,"utf-8"));
//			byte[] compressed =Snappy.compress(value.getBytes("UTF-8"));//压缩
			Cookie cookie = new Cookie(name, value);
			cookie.setMaxAge(age);
			cookie.setPath(path);
//			cookie.setDomain("localhost");
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 删除cookie
	 * @param name
	 * @param response
	 */
	public static void delete(HttpServletResponse response,String name){
		Cookie cookie = new Cookie(name,"");
		cookie.setMaxAge(0);
		cookie.setPath(path);
		response.addCookie(cookie);
	}
	
	public static void deleteAll(HttpServletRequest request, HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				cookie.setMaxAge(0);
				cookie.setPath(path);
				response.addCookie(cookie);
			}
		}
	}
	
	/**
	 * 改变cookie的值
	 * @param cookie
	 * @param value
	 */
	public static void edit(Cookie cookie,String value){
		if(cookie != null) cookie.setValue(value);
	}
	
	/**
	 * 更加cookie名称查找，返回其值
	 * 找不到返回null
	 * @param request
	 * @param name
	 * @return
	 */
	public static String fine(HttpServletRequest request,String name){
		try {
			Cookie[] cookies = request.getCookies();
			if(cookies != null){
				for(Cookie cookie : cookies){
					if(name.equals(cookie.getName()))
					{
						
//						byte[] cookitValue = Base64.decodeBase64(cookie.getValue());
//						byte[] uncompressed = Snappy.uncompress(cookitValue);
//						return URLDecoder.decode(cookie.getValue(), "utf-8");
//						return  new String (uncompressed,"UTF-8");
						return cookie.getValue();
					}
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public static Cookie get(HttpServletRequest request,String name){
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(name.equals(cookie.getName()))
				return cookie;
			}
		}
		return null;
	}
}