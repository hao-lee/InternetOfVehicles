package algorithm;

import java.util.ArrayList;

public class ProbabilityGenerator implements ProbabilityInterface {

	public static final int MAXSIZE_X = 8;//节点缓存的chunk数量
	public static final int CHUNK_NUMBER = 15;//文本被分的chunk数量
	public static final int NODE_NUMBER = 3;//节点个数
	
	public double[][] probility_Data = new double[CHUNK_NUMBER][NODE_NUMBER];
	//phi(i,k)
	public double[][] downloadTimes_X = new double[NODE_NUMBER][MAXSIZE_X + 1];
	//路过节点时，下载的chunk数量为Xi的次数
    public double[][] downloadTimes_Y = new double[NODE_NUMBER][CHUNK_NUMBER + 1];
    //路过节点时，下载的最后一个chunk的编号为Yi的次数
	public double[] passTimes={0,0,0};// 存放路过i节点的次数
	//public int[] UpdatePassTimes;
	public int[] updataNodeSequence;//行驶中，一个文本下载完成经过的节点顺序（外部传参）
	
	int[] lodeData_X;  //每个节点下载chunk的个数Xi（外部传参）
	int[] lodeData_Y; //每个节点下载的最后一个chunk的编号Yi（外部传参）
	
	/**
	 * 用于传入初始化数据
	 * @param int[][] lodeData_X 第i节点下载chunk的个数Xi
	 * @param probility_Data_Y 第i节点下载的最后一个chunk的编号Yi
	 * @param probility_Data_I 存放路过i节点的次数
	 */
	public ProbabilityGenerator(int[] updataNodeSequence, int[] lodeData_X,
			int[] lodeData_Y) {
		super();
		this.updataNodeSequence = updataNodeSequence;//这里传递的是节点的编号（1,2,3...）。
		this.lodeData_X = lodeData_X;
		this.lodeData_Y = lodeData_Y;
		//以上三者的长度应当是一样的
	}

	@Override
	public ArrayList<ArrayList<Double>> getPhiList(int k, int i) {
	
		for (int local = 0; local<updataNodeSequence.length; local++) { //更新DownloadTimes_X
			
				if(lodeData_X[local]>=MAXSIZE_X){
					downloadTimes_X[updataNodeSequence[local]-1][MAXSIZE_X]++;
					//减1是因为节点从1开始，下标从0开始
				}else{
					downloadTimes_X[updataNodeSequence[local]-1][lodeData_X[local]]+=1;	
					//减1是因为节点从1开始，下标从0开始
				}				
		} // for(int n = 0;	
		
		for (int local = 0; local<updataNodeSequence.length; local++) {//更新DownloadTimes_Y		
				downloadTimes_Y[updataNodeSequence[local]-1][lodeData_Y[local]]+=1;	
				//减1是因为节点从1开始，下标从0开始
		} // for(int n = 0;
			
		for (int local = 0; local<updataNodeSequence.length; local++) {//更新路过次数
			 passTimes[updataNodeSequence[local]-1]+=1;
			//减1是因为节点从1开始，下标从0开始
		} 
		for (int kChunk = 0; kChunk < CHUNK_NUMBER; kChunk++) {//利用公式计算Phi（i，k）
			for (int local = 0; local<updataNodeSequence.length; local++) {
				probility_Data[kChunk][updataNodeSequence[local]-1] = 
						     calPhi_i_k(kChunk+1, updataNodeSequence[local],local);//只改变最新路过的节点的概率
				              //传送的是chunk编号（注意不是下标，下标+1=编号）
			}
		}		

		/*
		 * 输出DownloadTimes_X
		 * */
		System.out.println("最终的DownloadTimes_X如下：");
		for ( i = 0; i < NODE_NUMBER; i++) // 打印概率
		{
			for (int j = 0; j <= MAXSIZE_X; j++) {
				System.out.print(downloadTimes_X[i][j] + "    ");
			}
			System.out.print("\n");
		}
		
		/*
		 * 输出DownloadTimes_Y
		 * */
		System.out.println("最终的DownloadTimes_Y如下：");
		for ( i = 0; i < NODE_NUMBER; i++) // 打印概率
		{
			for (int j = 0; j <= CHUNK_NUMBER; j++) {
				System.out.print(downloadTimes_Y[i][j] + "    ");
			}
			System.out.print("\n");
		}
		
		/*
		 * 输出passTimes
		 * */
		System.out.println("最终的PassTimes如下：");
		for ( i = 0; i < NODE_NUMBER; i++) // 打印概率
		{
			System.out.print(passTimes[i]+"    "); 
		}	
		
		/*
		 * 输出最终概率数组
		 * */
		System.out.println("\n最终的概率如下：");
		for ( i = 0; i < CHUNK_NUMBER; i++) // 打印概率
		{
			for (int j = 0; j < NODE_NUMBER; j++) {
				String double_str = String.format("%.4f", probility_Data[i][j]);
				System.out.print(double_str + "    ");
			}
			System.out.print("\n");
		}
		///////////////////////////////
		// 对数组形式的=》转成ArrayList
		ArrayList<ArrayList<Double>> phiList = new ArrayList<ArrayList<Double>>();

		for (i = 0; i < probility_Data.length; i++) {
			ArrayList<Double> rowList = new ArrayList<Double>();
			for (int j = 0; j < probility_Data[0].length; j++) {
				rowList.add(probility_Data[i][j]);
			}
			phiList.add(rowList);
		}
		return phiList;
	}
	
/*
 * 函数功能：计算节点下载chunk的概率
 * 入口参数：
 * （1）iNode：节点编号（注意不是下标，下标从0开始）
 * （2）kChunk：chunk编号（注意不是下标，下标从0开始）
 * 返回值：double类型，iNode节点下载kChunk的概率值
 */
	public double calPhi_i_k(int kChunk, int iNode,int local) {
		double phi = 0;
		double pdx = 0;
		int priorNode ;
		if(passTimes[iNode-1]<=0.1)//防止分母PassTimes[m]为0
		{
			return 0;
		}else if ((iNode == updataNodeSequence[0])|(local==0)) {//当节点为第一个路过的节点时
			
			int v = 0;
			for (v = MAXSIZE_X; v >= kChunk; v--) {
					           // 下载chunk个数为Xi的次数/路过的总次数
					phi+= downloadTimes_X[iNode-1][v]/passTimes[iNode-1];//公式的特殊情况处理		
				}

		} else {
				int j = 0;
				int v = 0;
				priorNode = updataNodeSequence[local-1];
				for (j = 0; j < kChunk; j++) {
					for (v = MAXSIZE_X; v >= kChunk - j; v--) {
						           // 下载chunk个数为Xi的次数/路过的总次数
						pdx += downloadTimes_X[iNode-1][v]/passTimes[iNode-1];		
					}
					                // 下载chunk最后一个编号为Yi的次数/路过的总次数
					phi += pdx*(downloadTimes_Y[priorNode-1][j]/passTimes[priorNode-1]);
					//updataNodeSequence[local-1]是iNode的上一节点，local是iNode节点的下标
				}
			}
		return phi;
	}//p
	
}
