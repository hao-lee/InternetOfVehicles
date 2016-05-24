package algorithm;

import java.util.ArrayList;

public interface ProbabilityInterface {
	/**
	 * 
	 * @param k		数据块数目
	 * @param i		节点数目
	 * @return		返回概率二维列表φ
	 */
	public ArrayList<ArrayList<Double>> getPhiList(int k,int i);//获取动态二维数组φ(k,i),表示第k块缓存到第i节点的概率
	
}