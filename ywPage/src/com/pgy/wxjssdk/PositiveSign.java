package com.pgy.wxjssdk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.pgy.common.Util;

public class PositiveSign {
	
	
	
	 public static Map<String, String> sign(String jsapi_ticket, String url) throws FileNotFoundException, IOException {
	    Map<String, String> ret = new HashMap<String, String>();
	    String nonce_str = create_nonce_str();
	    String timestamp = create_timestamp();
	    String string1;
	    String signature = "";
	        
	 
        //注意这里参数名必须全部小写，且必须有序
	    string1 = "jsapi_ticket=" + jsapi_ticket +
	              "&noncestr=" + nonce_str +
	              "&timestamp=" + timestamp +
	              "&url=" + url;
        System.out.println(string1);
	 
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(string1.getBytes("UTF-8"));
	        signature = byteToHex(crypt.digest());
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch (UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    
	    //自动获取WxTokenUtil.properties中appid属性，避免appid改动时重复更改
	    String appId = Util.APPID;
	    
	    
	    ret.put("url", url);
	    //注意这里 要加上自己的appId
	    ret.put("appId", appId);
	    ret.put("jsapi_ticket", jsapi_ticket);
	    ret.put("nonceStr", nonce_str);
	    ret.put("timestamp", timestamp);
	    ret.put("signature", signature);
	 
        return ret;
    }
	 
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	 
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
	 
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

	
}
