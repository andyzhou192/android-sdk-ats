package net.andy.ats.android.sdk;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.Robolectric;
import org.robolectric.ParameterizedRobolectricTestRunner.Parameters;
import org.robolectric.annotation.Config;

import android.content.Context;

import com.cmcc.ats.android.sdk.util.Excel2003Handler;
import com.cmcc.ats.android.sdk.util.LogUtils;
import com.cmcc.ats.android.sdk.util.PropertiesUtils;
import com.cmcc.ats.android.sdk.util.impl.Excel2003HandlerImpl;

@Config(manifest=Config.NONE)
@RunWith(ParameterizedRobolectricTestRunner.class)
public abstract class BaseTestRunner {
	public Context context;
	private Class<? extends BaseTestRunner> testClass;

	public BaseTestRunner(Class<? extends BaseTestRunner> testClass) {
		//设置Robolectric对http请求不进行拦截
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		//设置Robolectric对http响应不进行拦截
		Robolectric.getFakeHttpLayer().interceptResponseContent(false);
		//获取Android应用上下文
		this.context = Robolectric.application.getApplicationContext();
		//获取正在执行的测试类名称
		this.testClass = testClass;
	}

	@Parameters(name = "caseID={0}")
	public static Collection<?> getDatas(){
		Class<?> testClass = ParameterizedRobolectricTestRunner.currentTestClass.getJavaClass();
		String file = testClass.getName().toLowerCase().replace(".", File.separator) + ".xls";
		LogUtils.debug(BaseTestRunner.class, ParameterizedRobolectricTestRunner.currentTestClass.getJavaClass() + "----------" + file);
		return Arrays.asList(getParams(file));
	}

	/**
	 * 获取默认路径下的指定文件,默认路径在测试资源路径下雨测试类所在包名同路径下的上一级路径的files路径下，
	 * 如测试类在src/test/java/com/cmcc/android/script路径下，那么默认路径是src/test/resources/com/cmcc/android/files
	 * @param fileName  文件名
	 * @return
	 */
	public String getFile(String fileName){
		String proPath = System.getProperty("user.dir") +  File.separator + "target" +  File.separator + "test-classes" +  File.separator;
		String file = testClass.getPackage().getName().toLowerCase().replace(".", File.separator) +  File.separator + "files" + File.separator + fileName;
		LogUtils.debug(getClass(), proPath + file);
		return file;
	}
	
	/**
	 * 获取setting.properties文件中配置的路径下的指定文件
	 * @param key   setting.properties文件中配置的key
	 * @param fileName   文件名
	 * @return
	 */
	public String getFile(String key, String fileName){
		return PropertiesUtils.getProperty(key) + fileName;
	}
	
	//测试是否执行完标示
	private  boolean done = false;

	protected  boolean isDone() {
		return done;
	}
	
	protected void setDone(boolean done) {
		this.done = done;
	}

	public void keepAlive(long keepTime) {
		long startTime = System.currentTimeMillis()/1000;
		long endTime = startTime;
		while (!isDone() & (endTime - startTime < keepTime)) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			endTime = System.currentTimeMillis()/1000;
		}
		LogUtils.debug(getClass(), "主线程结束");
	}
	
	/**
	 * 获取excel文件中参数
	 * @param file
	 * @return
	 */
	private static Object[][] getParams(String file) {
		Excel2003Handler excHandler = new Excel2003HandlerImpl();
		List<String> titles = new ArrayList<String>();
		
		List<String[]> paramList = excHandler.readExcel(file, 0);
		
		for(String str : paramList.get(0)){
			titles.add(str);
		}
		String[][] params = new String[paramList.size()-1][];
		for(int i = 0; i < paramList.size()-1; i++){
			params[i] = paramList.get(i+1);
		}
		return params;
	}
}
