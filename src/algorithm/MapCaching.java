package algorithm;

import java.util.ArrayList;

public class MapCaching implements ProbabilityInterface{

	ArrayList<ArrayList<Double>> phiList = null;
	ArrayList<ArrayList<Integer>> resList = new ArrayList<>();
	
	public static void main(String[] args) {
		new MapCaching().map_caching();
	}
	
	/**
	 * 算法计算主函数
	 */
	public void map_caching() {
		double threshold = 0.5;
		int k = 5;//5块数据
		int i = 3;//3个节点
		phiList = getPhiList(k, i);//获取概率二维列表
		//遍历每个数据块
		for(int count = 0; count < k; count++){
			
			double pk = 0;
			ArrayList<Double> fList = new ArrayList<>();
			//将PhiList的第count行复制到fList中，fList表示第count块数据在各个节点的概率集合
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
			resList.add(sList);//每测试一个块k，都会在resList中加入一行
			
		}//for
		//循环结束后，resList就是结果的二维列表
		for(int local = 0; local < resList.size(); local++)
			System.out.println("第"+local+"块所要缓存到的节点号："+resList.get(local).toString());
		return;
	}
	
	/**
	 * 每当从fList取出一个当前的最大概率，为了不破坏顺序，不直接将该概率删除，而是赋值为-1
	 * 当所有元素都为-1时就可以认为fList所有概率都被取出，列表空了，返回true
	 * @param fList		某个块在各个节点的概率列表fList
	 * @return			fList是否为空
	 */
	private boolean fListIsEmpty(ArrayList<Double> fList){
		for(int local =0; local < fList.size(); local++){
			if(fList.get(local) != -1)
				return false;//没空
		}
		return true;
	}
	

	/**
	 * @param fList		某个块在各个节点的概率列表fList
	 * @return			两个元素的list，第一个元素是pTop，第二个元素是iTop
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
		ret.add((double)iTop);//最大概率以及它对应的节点号
		return ret;
	}
	
	/**
	 * 该接口内的方法必须被实现，这是获得初始概率二维列表的方法
	 */
	@Override
	public ArrayList<ArrayList<Double>> getPhiList(int k,int i) {
		
		ArrayList<ArrayList<Double>> PhiList = new ArrayList<>();
		ArrayList<Double> tmp = null;
		//第i个块 缓存在第j个节点的概率
		double[][] phiTmp = {
				{0.1,0.3,0.6},
				{0.2,0.1,0.2},
				{0.1,0.5,0.3},
				{0.7,0.1,0.1},
				{0.3,0.5,0.2},
		};
		for(int k_tmp = 0; k_tmp < k; k_tmp++){
			//第k块在各个i节点的概率一维列表
			tmp = new ArrayList<>();
			for(int i_tmp = 0;i_tmp < i; i_tmp++){
				tmp.add(phiTmp[k_tmp][i_tmp]);
			}
			PhiList.add(tmp);//第k个块的数据填充完毕，加入集合中
		}
		return PhiList;
	}//getPhiArray

}
