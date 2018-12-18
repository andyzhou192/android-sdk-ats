package net.andy.ats.android.sdk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.cmcc.ats.android.sdk.tools.StringTools;

/**
 * 
 * @author sifuma@163.com
 *
 */
public class PropertiesUtils {
	
	public static String getProperty(String proName){
		String proValue = "";
		Properties prop =  new  Properties();
//		String proFileTest = (Object.class.getClass().getResource("/").getPath() + "../test-classes/property.properties").replace("/", File.separator);
//		String proFileMain = (Object.class.getClass().getResource("/").getPath() + "property.properties").replace("/", File.separator);
//		String proFile = FileTools.fileIsExists(proFileTest) ? proFileTest : proFileMain;
//		LogUtils.debug("proFile:" + proFile);
		InputStream in = Object.class.getResourceAsStream("/setting.properties");
		try {
//			in = new FileInputStream(proFile);
			prop.load(in);    
			proValue = prop.getProperty(proName.replace("_", ".")); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(null != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}   
        return StringTools.isNull(proValue) ? proValue : proValue.trim();
	}

//	public static void main(String[] args) {
//		System.out.println(getProperty("url"));
//	}
}
