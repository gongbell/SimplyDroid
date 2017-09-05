package cn.edu.buaa.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.edu.buaa.state.EventState;

public class MD5 {
	
	//The method to calculate MD5 value of a given event sequence
	public static String getMD5(List<EventState> queue){
		String plainText = getIndexString(queue);
		return getMD5(plainText);
	}
	
	//The method to calculate MD5 value of a text stream
	public static String getMD5(String text){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] b = md.digest();
			
			int i;
			
			StringBuffer buf = new StringBuffer("");
			for(int offset = 0; offset < b.length; offset++){
				i = b[offset];
				if(i < 0) {
					i += 256;
				}
				if(i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//The method to convert a event sequence to plain text
	private static String getIndexString(List<EventState> queue){
		StringBuffer res = new StringBuffer("1");
		for(EventState es: queue){
			res.append("," + es.getIndex());
		}
		return res.toString();
	}
	
}
