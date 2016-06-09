package algorithm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import util.FileIO;

public class MapCaching {

	ArrayList<ArrayList<Double>> phiList = null;
	ArrayList<ArrayList<Integer>> resList = new ArrayList<>();

	int k = 15;// 15������(gz_05/14)
	int i = 3;// 3���ڵ�

	public static void main(String[] args) {
		new MapCaching().map_caching();
	}

	/**
	 * ���캯�������ļ��ж�ȡ���ݼ���
	 */
	public MapCaching() {
		int[] probility_Data_I = {}; // ���·��i�ڵ�Ĵ���
		int[] lodeData_X = {}; // ������ݡ�ÿ���ڵ�����chunk�ĸ���Xi
		int[] lodeData_Y = {}; // ������ݡ�ÿ���ڵ����ص����һ��chunk�ı��Yi
		
		FileIO fileIO = new FileIO();
		/* ���ýӿں����������µĸ��� */
		String line;
		try {
			//��ȡDATA_I.txt
			probility_Data_I = fileIO.readFile("./IOFile/Data_I.txt");
			//��ȡlodeData_X.txt
			lodeData_X = fileIO.readFile("./IOFile/lodeData_X.txt");
			//��ȡlodeData_Y.txt
			lodeData_Y = fileIO.readFile("./IOFile/lodeData_Y.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * ��������
		 * */
		ProbabilityGenerator probabilityGenerator = new 
				ProbabilityGenerator(probility_Data_I,lodeData_X, lodeData_Y);
		phiList = probabilityGenerator.getPhiList(k, i);
	}
	/**
	 * ��String������int���͵�����
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
				System.out.println("��ʽת������");
			}
			System.out.print(reslut[i] + " ");
		}
		System.out.println();
		return reslut;
	}

	/**
	 * �㷨����������
	 */
	public void map_caching() {
		double threshold = 0.5;

		// ����ÿ�����ݿ�
		for (int count = 0; count < k; count++) {

			double pk = 0;
			ArrayList<Double> fList = new ArrayList<>();
			// ��PhiList�ĵ�count�и��Ƶ�fList�У�fList��ʾ��count�������ڸ����ڵ�ĸ��ʼ���
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
			resList.add(sList);// ÿ����һ����k��������resList�м���һ��

		}// for
			// ѭ��������resList���ǽ���Ķ�ά�б�
		for (int local = 0; local < resList.size(); local++)
			System.out.println(resList.get(local).toString());
		return;
	}

	/**
	 * ÿ����fListȡ��һ����ǰ�������ʣ�Ϊ�˲��ƻ�˳�򣬲�ֱ�ӽ��ø���ɾ�������Ǹ�ֵΪ-1
	 * ������Ԫ�ض�Ϊ-1ʱ�Ϳ�����ΪfList���и��ʶ���ȡ�����б���ˣ�����true
	 * 
	 * @param fList
	 *            ĳ�����ڸ����ڵ�ĸ����б�fList
	 * @return fList�Ƿ�Ϊ��
	 */
	private boolean fListIsEmpty(ArrayList<Double> fList) {
		for (int local = 0; local < fList.size(); local++) {
			if (fList.get(local) != -1)
				return false;// û��
		}
		return true;
	}

	/**
	 * @param fList
	 *            ĳ�����ڸ����ڵ�ĸ����б�fList
	 * @return ����Ԫ�ص�list����һ��Ԫ����pTop���ڶ���Ԫ����iTop
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
		ret.add((double) iTop);// �������Լ�����Ӧ�Ľڵ��
		return ret;
	}

}
