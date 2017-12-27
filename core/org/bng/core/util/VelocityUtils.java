package org.bng.core.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.texen.util.PropertiesUtil;

public class VelocityUtils {
	
	public static String processTemplateFile(Map<String, Object> root, String filePath) {
		StringWriter writer = new StringWriter();
		String result = "";
		
		try {
			Properties properties = new PropertiesUtil().load("velocity.properties");
			Velocity.init(properties);
			
			VelocityContext context = new VelocityContext();
			putValueToContext(context,root);
			
			Template t = Velocity.getTemplate("template/" + filePath);
			t.merge(context, writer);
			result = writer.toString();
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private static VelocityContext putValueToContext(VelocityContext context, Map<String,Object> root){
		for(Iterator<Map.Entry<String, Object>> iter = root.entrySet().iterator(); iter.hasNext();){
			Map.Entry<String, Object> entry = iter.next();
			context.put(entry.getKey(), entry.getValue());
		}
		return context;
	}

}
