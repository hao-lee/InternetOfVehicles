package algorithm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapCaching {

	ArrayList<ArrayList<Double>> phiList = null;
	ArrayList<ArrayList<Integer>> resList = new ArrayList<>();

	int k = 15;// 15块数据(gz_05/14)
	int i = 3;// 3个节点

	public static void main(String[] args) {
		new MapCaching().map_caching();
	}

	/**
	 * 构造函数，从文件中读取数据计算
	 */
	public MapCaching() {
		int[] probility_Data_I = {}; // 存放路过i节点的次数
		int[] lodeData_X = {}; // 存放数据。每个节点下载chunk的个数Xi
		int[] lodeData_Y = {}; // 存放数据。每个节点下载的最后一个chunk的编号Yi
		/* 调用接口函数，生成新的概率 */
		FileInputStream fileInputStream = null;
		BufferedReader bf = null;
		String line;
		try {
			
			//读取DATA_I.txt
			fileInputStream = new FileInputStream("./IOFile/DATA_I.txt");
			bf = new BufferedReader(new InputStreamReader(fileInputStream));
			System.out.println("读取路过i节点次数！");
			while ((line = bf.readLine()) != null) {
				probility_Data_I = String2Int(line);
			}
			
			if (probility_Data_I.length == 0 || probility_Data_I == null) {
				System.out.println("经过i点的次数为空！");
				return;
			}
			
			//读取lodeData_X.txt
			fileInputStream = new FileInputStream("./IOFile/lodeData_X.txt");
			bf = new BufferedReader(new InputStreamReader(fileInputStream));
			int len = probility_Data_I.length;
			System.out.println("读取第i节点下载chunk的个数Xi");
			while ((line = bf.readLine()) != null) {
				lodeData_X = String2Int(line);
				if (lodeData_X.length != len) {
					System.out.println("Waring:长度不符！");
					break;
				}
			}
			
			//读取lodeData_Y.txt
			fileInputStream = new FileInputStream("./IOFile/lodeData_Y.txt");
			bf = new BufferedReader(new InputStreamReader(fileInputStream));
			len = probility_Data_I.length;
			System.out.println("第i个节点下载的最后一个chunk的编号Yi");
			while ((line = bf.readLine()) != null) {
				lodeData_Y = String2Int(line);
				if (lodeData_Y.length != len) {
					System.out.println("Waring:输入的每个节点下载最后一个节点的数据和输入节点数不符！");
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bf != null) bf.close();
				if(fileInputStream != null) fileInputStream.close();
			} catch (Exception e2) {e2.printStackTrace();}
		}

		/*
		 * 概率生成
		 * */
		ProbabilityGenerator probabilityGenerator = new 
				ProbabilityGenerator(probility_Data_I,lodeData_X, lodeData_Y);
		phiList = probabilityGenerator.getPhiList(k, i);
	}
	/**
	 * 把String解析成int类型的数组
	 * @param line
	 * @return
	 */
	private int[] String2Int(String line) {
		String tmp[] = line.trim().split(",");
		int len = tmp.length;
		int reslut[] = new int[len];
		for (int i = 0; i < tmp.length; i++) {
			try {
				reslut[i] = Integer.parseInt(tmp[i].trim());
			} catch (Exception e) {
				System.out.println("格式转换错误！");
			}
			System.out.print(reslut[i] + " ");
		}
		System.out.println();
		return reslut;
	}

	/**
	 * 算法计算主函数
	 */
	public void map_caching() {
		double threshold = 0.5;

		// 遍历每个数据块
		for (int count = 0; count < k; count++) {

			double pk = 0;
			ArrayList<Double> fList = new ArrayList<>();
			// 将PhiList的第count行复制到fList中，fList表示第count块数据在各个节点的概率集合
			for (int tmp = 0; tmp < phiList.get(count).size(); tmp++) {
				fList.add(phiList.get(count).get(tmp));
			}
			ArrayList<Integer> sList = new ArrayList<>();
			while (!fListIsEmpty(fList) && pk < threshold) {
				ArrayList<Double> ret = getMaxValue(fList);
				double pTop = ret.get(0);
				int iTop = (int) (ret.get(1).doubleValue());
				fList.set(iTop, (double) -1);
				pk = pk + pTop;
				sList.add(iTop);
			}
			resList.add(sList);// 每测试一个块k，都会在resList中加入一行

		}// for
			// 循环结束后，resList就是结果的二维列表
		for (int local = 0; local < resList.size(); local++)
			System.out.println(resList.get(local).toString());
		return;
	}

	/**
	 * 每当从fList取出一个当前的最大概率，为了不破坏顺序，不直接将该概率删除，而是赋值为-1
	 * 当所有元素都为-1时就可以认为fList所有概率都被取出，列表空了，返回true
	 * 
	 * @param fList
	 *            某个块在各个节点的概率列表fList
	 * @return fList是否为空
	 */
	private boolean fListIsEmpty(ArrayList<Double> fList) {
		for (int local = 0; local < fList.size(); local++) {
			if (fList.get(local) != -1)
				return false;// 没空
		}
		return true;
	}

	/**
	 * @param fList
	 *            某个块在各个节点的概率列表fList
	 * @return 两个元素的list，第一个元素是pTop，第二个元素是iTop
	 */
	private ArrayList<Double> getMaxValue(ArrayList<Double> fList) {
		double pTop = -1;
		int iTop = -1;
		ArrayList<Double> ret = new ArrayList<Double>();
		for (int local = 0; local < fList.size(); local++) {
			if (fList.get(local) > pTop) {
				pTop = fList.get(local);
				iTop = local;
			}
		}
		ret.add(pTop);
		ret.add((double) iTop);// 最大概率以及它对应的节点号
		return ret;
	}

}
