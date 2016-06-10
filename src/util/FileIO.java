package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Vector;

import org.junit.Test;

public class FileIO {
	
	public FileIO() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @author chenzewei
	 * @param filePath
	 * @return 
	 * @return
	 */
	public  int[] readFile(String filePath) {
		Vector<Integer> res = new Vector<Integer>();
		
		FileInputStream fileInputStream = null;
		BufferedReader bf = null;
		String line;
		String fileName = filePath.substring(filePath.lastIndexOf("/"));
		
		try {
			//读取文件
			fileInputStream = new FileInputStream(filePath);
			bf = new BufferedReader(new InputStreamReader(fileInputStream));
			System.out.println("读"+fileName+"文件开始！");
			while ((line = bf.readLine()) != null) {
				 res.addAll(String2Int(line));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(fileName+"读取失败！");
		} finally{
			try {
				if(bf != null) bf.close();
				if(fileInputStream != null) fileInputStream.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return vector2Array(res);
	}
	
	public int[] vector2Array(Vector<Integer> res) {
		// TODO Auto-generated method stub
		int array[] = new int[res.size()];
		int index = 0;
		for (Iterator iterator = res.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			array[index++] = integer;
		}
		return array;
	}
	/**
	 * 数据写入文件
	 * 返回值：1 成功，0失败
	 *@author chenzewei
	 *@param 
	 */
	public int savaFile(int[] data,String filePath){
		
		FileOutputStream fileOutputStream = null;
		BufferedWriter bw = null;
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			bf.append(data[i]);
			if( i!= data.length-1)
				bf.append(",");
		}
		String fileName = filePath.substring(filePath.lastIndexOf("/"));
		try {
			//读取文件
			fileOutputStream = new FileOutputStream(filePath);
			bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			System.out.println(fileName+"文件开始写入");
			bw.write(bf.toString());
			bw.flush();
			System.out.println(fileName+"文件开始完成");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(fileName+"文件写入失败");
			return 0;
		} finally{
			try {
				if(bw != null) bw.close();
				if(fileOutputStream != null) fileOutputStream.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return 1;
	}

	/**
	 * 把String解析成Vector
	 * @param line
	 * @return
	 */
	private Vector<Integer> String2Int(String line) {
		String tmp[] = line.trim().split(",");
		int len = tmp.length;
		Vector<Integer> res = new Vector<Integer>();
		for (int i = 0; i < len; i++) {
			try {
				res.add(Integer.parseInt(tmp[i].trim()));
			} catch (Exception e) {
				System.out.println("格式转换错误！");
			}
			System.out.print(res.get(i)+ " ");
		}
		System.out.println();
		return res;
	}
	
	/**
	 *测试readfile()函数
	 * @param void
	 * @return
	 * 用法：如下
	 */
	@Test
	public void testreadfile(){
		int[] a = new FileIO().readFile("./IOFile/test.txt");
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
		System.out.println();
	}
	/**
	 *测试savefile()函数
	 * @param void
	 * @return
	 * 用法：如下
	 */
	@Test
	public void testsaveFile(){
		int a[] = {1,3,5,7,8};
		new FileIO().savaFile(a,"./IOFile/test.txt");
	}
}
