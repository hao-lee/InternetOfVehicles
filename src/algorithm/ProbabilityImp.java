package algorithm;

import java.util.ArrayList;

public class ProbabilityImp implements ProbabilityInterface {

	public static final int MAXSIZE_X = 8;//�ڵ㻺���chunk����
	public static final int CHUNK_NUMBER = 15;//�ı����ֵ�chunk����
	public static final int NODE_NUMBER = 3;//�ڵ����
	
	public double[][] probility_Data = new double[CHUNK_NUMBER][NODE_NUMBER];
	//phi(i,k)
	public double[][] DownloadTimes_X = new double[NODE_NUMBER][MAXSIZE_X + 1];
	//·���ڵ�ʱ�����ص�chunk����ΪXi�Ĵ���
    public double[][] DownloadTimes_Y = new double[NODE_NUMBER][CHUNK_NUMBER + 1];
    //·���ڵ�ʱ�����ص����һ��chunk�ı��ΪYi�Ĵ���
	public double[] PassTimes={0,0,0};// ���·��i�ڵ�Ĵ���
	//public int[] UpdatePassTimes;
	public int[] updataNodeSequence;//��ʻ�У�һ���ı�������ɾ����Ľڵ�˳���ⲿ���Σ�
	
	int[] lodeData_X;  //ÿ���ڵ�����chunk�ĸ���Xi���ⲿ���Σ�
	int[] lodeData_Y; //ÿ���ڵ����ص����һ��chunk�ı��Yi���ⲿ���Σ�
	
	/**
	 * ���ڴ����ʼ������
	 * @param int[][] lodeData_X ��i�ڵ�����chunk�ĸ���Xi
	 * @param probility_Data_Y ��i�ڵ����ص����һ��chunk�ı��Yi
	 * @param probility_Data_I ���·��i�ڵ�Ĵ���
	 */
	public ProbabilityImp(int[] updataNodeSequence, int[] lodeData_X,
			int[] lodeData_Y) {
		super();
		this.updataNodeSequence = updataNodeSequence;//���ﴫ�ݵ��ǽڵ�ı�ţ�1,2,3...����
		this.lodeData_X = lodeData_X;
		this.lodeData_Y = lodeData_Y;
		//�������ߵĳ���Ӧ����һ����
	}

	@Override
	public ArrayList<ArrayList<Double>> getPhiList(int k, int i) {
	
		for (int local = 0; local<updataNodeSequence.length; local++) { //����DownloadTimes_X
			
				if(lodeData_X[local]>=MAXSIZE_X){
					DownloadTimes_X[updataNodeSequence[local]-1][MAXSIZE_X]++;
					//��1����Ϊ�ڵ��1��ʼ���±��0��ʼ
				}else{
					DownloadTimes_X[updataNodeSequence[local]-1][lodeData_X[local]]+=1;	
					//��1����Ϊ�ڵ��1��ʼ���±��0��ʼ
				}				
		} // for(int n = 0;	
		
		for (int local = 0; local<updataNodeSequence.length; local++) {//����DownloadTimes_Y		
				DownloadTimes_Y[updataNodeSequence[local]-1][lodeData_Y[local]]+=1;	
				//��1����Ϊ�ڵ��1��ʼ���±��0��ʼ
		} // for(int n = 0;
			
		for (int local = 0; local<updataNodeSequence.length; local++) {//����·������
			 PassTimes[updataNodeSequence[local]-1]+=1;
			//��1����Ϊ�ڵ��1��ʼ���±��0��ʼ
		} 
		for (int kChunk = 0; kChunk < CHUNK_NUMBER; kChunk++) {//���ù�ʽ����Phi��i��k��
			for (int local = 0; local<updataNodeSequence.length; local++) {
				probility_Data[kChunk][updataNodeSequence[local]-1] = 
						     p(kChunk+1, updataNodeSequence[local],local);//ֻ�ı�����·���Ľڵ�ĸ���
				              //���͵���chunk��ţ�ע�ⲻ���±꣬�±�+1=��ţ�
			}
		}		

		//////////////////////////////////	
		System.out.println("���յ�DownloadTimes_X���£�");
		for ( i = 0; i < NODE_NUMBER; i++) // ��ӡ����
		{
			for (int j = 0; j <= MAXSIZE_X; j++) {
				System.out.print(DownloadTimes_X[i][j] + "    ");
			}
			System.out.print("\n");
		}
		//////////////////////////////////
		System.out.println("���յ�DownloadTimes_Y���£�");
		for ( i = 0; i < NODE_NUMBER; i++) // ��ӡ����
		{
			for (int j = 0; j <= CHUNK_NUMBER; j++) {
				System.out.print(DownloadTimes_Y[i][j] + "    ");
			}
			System.out.print("\n");
		}
		/////////////////////////////
		System.out.println("���յ�PassTimes���£�");
		for ( i = 0; i < NODE_NUMBER; i++) // ��ӡ����
		{
			System.out.print(PassTimes[i]+"    "); 
		}	
		///////////////////////////
		System.out.println("\n���յĸ������£�");
		for ( i = 0; i < CHUNK_NUMBER; i++) // ��ӡ����
		{
			for (int j = 0; j < NODE_NUMBER; j++) {
				String double_str = String.format("%.4f", probility_Data[i][j]);
				System.out.print(double_str + "    ");
			}
			System.out.print("\n");
		}
		///////////////////////////////
		// ��������ʽ��=��ת��ArrayList
		ArrayList<ArrayList<Double>> prob = new ArrayList<ArrayList<Double>>();

		for (int j = 0; j < probility_Data.length; j++) {
			ArrayList<Double> pro = new ArrayList<Double>();
			for (int j2 = 0; j2 < probility_Data[0].length; j2++) {
				pro.add(probility_Data[j][j2]);
			}
			prob.add(pro);
		}
		return prob;
	}
	
/*
 * �������ܣ�����ڵ�����chunk�ĸ���
 * ��ڲ�����
 * ��1��iNode���ڵ��ţ�ע�ⲻ���±꣬�±��0��ʼ��
 * ��2��kChunk��chunk��ţ�ע�ⲻ���±꣬�±��0��ʼ��
 * ����ֵ��double���ͣ�iNode�ڵ�����kChunk�ĸ���ֵ
 */
	public double p(int kChunk, int iNode,int local) {
		double phi = 0;
		double pdx = 0;
		int priorNode ;
		if(PassTimes[iNode-1]<=0.1)//��ֹ��ĸPassTimes[m]Ϊ0
		{
			return 0;
		}else if ((iNode == updataNodeSequence[0])|(local==0)) {//���ڵ�Ϊ��һ��·���Ľڵ�ʱ
			
			int v = 0;
			for (v = MAXSIZE_X; v >= kChunk; v--) {
					           // ����chunk����ΪXi�Ĵ���/·�����ܴ���
					phi+= DownloadTimes_X[iNode-1][v]/PassTimes[iNode-1];//��ʽ�������������		
				}

		} else {
				int j = 0;
				int v = 0;
				priorNode = updataNodeSequence[local-1];
				for (j = 0; j < kChunk; j++) {
					for (v = MAXSIZE_X; v >= kChunk - j; v--) {
						           // ����chunk����ΪXi�Ĵ���/·�����ܴ���
						pdx += DownloadTimes_X[iNode-1][v]/PassTimes[iNode-1];		
					}
					                // ����chunk���һ�����ΪYi�Ĵ���/·�����ܴ���
					phi += pdx*(DownloadTimes_Y[priorNode-1][j]/PassTimes[priorNode-1]);
					//updataNodeSequence[local-1]��iNode����һ�ڵ㣬local��iNode�ڵ���±�
				}
			}
		return phi;
	}//p
	
}
