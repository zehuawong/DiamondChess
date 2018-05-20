package 回溯法找到一个解;
 
public class PrintAnswer {	
	public static void main(String []args){	
		long starttime=System.currentTimeMillis();		
		Answer answer=new Answer();
		answer.findOneAnswer();
		answer.printAnswer();	
		long endtime=System.currentTimeMillis();
		System.out.println("程序运行时间："+(endtime-starttime)+"ms");
	}	
}
