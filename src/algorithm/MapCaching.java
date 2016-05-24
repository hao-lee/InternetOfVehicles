package algorithm;

import java.util.ArrayList;

public class MapCaching implements ProbabilityInterface{

	ArrayList<ArrayList<Double>> phiList = null;
	ArrayList<ArrayList<Integer>> resList = new ArrayList<>();
	
	public static void main(String[] args) {
		new MapCaching().map_caching();
	}
	
	/**
	 * �㷨����������
	 */
	public void map_caching() {
		double threshold = 0.5;
		int k = 5;//5������
		int i = 3;//3���ڵ�
		phiList = getPhiList(k, i);//��ȡ���ʶ�ά�б�
		//����ÿ�����ݿ�
		for(int count = 0; count < k; count++){
			
			double pk = 0;
			ArrayList<Double> fList = new ArrayList<>();
			//��PhiList�ĵ�count�и��Ƶ�fList�У�fList��ʾ��count�������ڸ����ڵ�ĸ��ʼ���
			for(int tmp = 0; tmp < phiList.get(count).size(); tmp++){
				fList.add(phiList.get(count).get(tmp));
			}
			ArrayList<Integer> sList = new ArrayList<>();
			while(!fListIsEmpty(fList)&& pk <threshold){
				ArrayList<Double> ret = getMaxValue(fList);
				double pTop = ret.get(0);
				int iTop = (int)(ret.get(1).doubleValue());
				fList.set(iTop, (double)-1);
				pk = pk + pTop;
				sList.add(iTop);
			}
			resList.add(sList);//ÿ����һ����k��������resList�м���һ��
			
		}//for
		//ѭ��������resList���ǽ���Ķ�ά�б�
		for(int local = 0; local < resList.size(); local++)
			System.out.println("��"+local+"����Ҫ���浽�Ľڵ�ţ�"+resList.get(local).toString());
		return;
	}
	
	/**
	 * ÿ����fListȡ��һ����ǰ�������ʣ�Ϊ�˲��ƻ�˳�򣬲�ֱ�ӽ��ø���ɾ�������Ǹ�ֵΪ-1
	 * ������Ԫ�ض�Ϊ-1ʱ�Ϳ�����ΪfList���и��ʶ���ȡ�����б���ˣ�����true
	 * @param fList		ĳ�����ڸ����ڵ�ĸ����б�fList
	 * @return			fList�Ƿ�Ϊ��
	 */
	private boolean fListIsEmpty(ArrayList<Double> fList){
		for(int local =0; local < fList.size(); local++){
			if(fList.get(local) != -1)
				return false;//û��
		}
		return true;
	}
	

	/**
	 * @param fList		ĳ�����ڸ����ڵ�ĸ����б�fList
	 * @return			����Ԫ�ص�list����һ��Ԫ����pTop���ڶ���Ԫ����iTop
	 */
	private ArrayList<Double> getMaxValue(ArrayList<Double> fList){
		double pTop = -1;
		int iTop = -1;
		ArrayList<Double> ret = new ArrayList<Double>();
		for(int local=0; local < fList.size(); local++){
			if(fList.get(local) > pTop)
				{pTop = fList.get(local);iTop = local;}
		}
		ret.add(pTop);
		ret.add((double)iTop);//�������Լ�����Ӧ�Ľڵ��
		return ret;
	}
	
	/**
	 * �ýӿ��ڵķ������뱻ʵ�֣����ǻ�ó�ʼ���ʶ�ά�б�ķ���
	 */
	@Override
	public ArrayList<ArrayList<Double>> getPhiList(int k,int i) {
		
		ArrayList<ArrayList<Double>> PhiList = new ArrayList<>();
		ArrayList<Double> tmp = null;
		//��i���� �����ڵ�j���ڵ�ĸ���
		double[][] phiTmp = {
				{0.1,0.3,0.6},
				{0.2,0.1,0.2},
				{0.1,0.5,0.3},
				{0.7,0.1,0.1},
				{0.3,0.5,0.2},
		};
		for(int k_tmp = 0; k_tmp < k; k_tmp++){
			//��k���ڸ���i�ڵ�ĸ���һά�б�
			tmp = new ArrayList<>();
			for(int i_tmp = 0;i_tmp < i; i_tmp++){
				tmp.add(phiTmp[k_tmp][i_tmp]);
			}
			PhiList.add(tmp);//��k��������������ϣ����뼯����
		}
		return PhiList;
	}//getPhiArray

}
