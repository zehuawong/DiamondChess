package ���ݷ��ҵ�һ����;
 
public class PrintAnswer {	
	public static void main(String []args){	
		long starttime=System.currentTimeMillis();		
		Answer answer=new Answer();
		answer.findOneAnswer();
		answer.printAnswer();	
		long endtime=System.currentTimeMillis();
		System.out.println("��������ʱ�䣺"+(endtime-starttime)+"ms");
	}	
}
