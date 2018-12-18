package net.andy.ats.android.sdk.tools;


import java.io.BufferedReader;
import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;


/**
 * 
 * @author sifuma@163.com
 *
 */

public class FileTools {


	/**

	 * 判断指定文件是否存在

	 * @param path：文件路径，如：/storage/sdcard0/Manual/test.pdf

	 * @return

	 */

	public static boolean fileIsExists(String path) {

		try {

			File f = new File(path);

			if (!f.exists()) {

				return false;

			}


		} catch (Exception e) {

			// TODO: handle exception

			return false;

		}
		return true;

	}


	/**

	 * 判断指定文件中是否存在指定的内容

	 * @param filePath

	 * @param contents

	 * @return

	 */

	@SuppressWarnings("resource")

	public static boolean isContainSpecialText(String url, String...contents) {

		boolean isContain = false;

		FileReader fr;

		try {

			fr = new FileReader(url);

			BufferedReader bf = new BufferedReader(fr);

			String temp = "";

			while(temp != null){

				temp = bf.readLine();

				if(temp != null){

					for(String content:contents){

						isContain = temp.contains(content);

						if(!isContain) break;

					}

				}

				if(isContain) break;

			}

		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return isContain;

	}

}
