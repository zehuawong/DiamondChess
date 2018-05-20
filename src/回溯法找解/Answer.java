package ���ݷ��ҽ�;

import ���ݷ�������ʾ.*;

public class Answer {
	public static Step mystack[]=new Step[100];//���ջҪ�ģ�
	public static Step last_step=new Step();//��һ��
	public static char diamond[][]=new char [7][7];
	public static int Left_diamond=32;
	public static int x,y,nx,ny,ndir;// ndirֵ������, 0��������, 1��������, 2��������, 3��������
	public static int top;
	static int flag=1;// �Ƿ�ɹ��ҵ���ı�־
	public static DiamondChess frame;
	
	 
	public static void Init_diamond(){
		for(int i=0;i<7;i++){
			for(int j=0;j<7;j++){
				if((i<2||i>4)&&(j<2||j>4));
				else {
					diamond[i][j]='*';
				}
			}
		}
		diamond[3][3]='.';
	}
	static int Move_diamond(int y, int x, int dir)	
	{
		if(diamond[y][x] != '*')
		{
			return 0;
		}
		Step temp=new Step();
		switch(dir)  //�ĸ�������߷�
		{
		case 0:
			if(x+2>6 || diamond[y][x+1]!='*' || diamond[y][x+2]!='.')
			{
				return 0;
			}
			diamond[y][x] = diamond[y][x+1] = '.';//ԭ����λ�úͿ�������Ӽ�¼Ϊ���
			diamond[y][x+2] = '*';	 
			temp.sx = x;   //��¼�߷�λ��
			temp.sy = y;
			temp.tx = x+2;
			temp.ty = y;
			temp.dir = dir;
			frame.MoveOneStep(temp);//���ڵ����̸�����һ��
			mystack[top++] = temp; //�߷���ջ top++
			return 1;
			 
		case 1:
			if(y+2>6 || diamond[y+1][x]!='*' || diamond[y+2][x]!='.')
			{
				return 0;
			}
			diamond[y][x] = diamond[y+1][x] = '.';
			diamond[y+2][x] = '*';		
			temp.sx = x;  
			temp.sy = y;
			temp.tx = x;
			temp.ty = y+2;
			temp.dir = dir;
			frame.MoveOneStep(temp);//���ڵ����̸�����һ��
			mystack[top++] = temp;
			return 1;
			 
		case 2:
			if(x-2<0 || diamond[y][x-1]!='*' || diamond[y][x-2]!='.')
			{
				return 0;
			}
			diamond[y][x] = diamond[y][x-1] = '.';
			diamond[y][x-2] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x-2;
			temp.ty = y;
			temp.dir = dir;
			frame.MoveOneStep(temp);//���ڵ����̸�����һ��
			mystack[top++] = temp;
			return 1;
		case 3:
			if(y-2<0 || diamond[y-1][x]!='*' || diamond[y-2][x]!='.')
			{
				return 0;
			}
			diamond[y][x] = diamond[y-1][x] = '.';
			diamond[y-2][x] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x;
			temp.ty = y-2;
			temp.dir = dir;
			frame.MoveOneStep(temp);//���ڵ����̸�����һ��
			mystack[top++] = temp;
			return 1;
		default:
			break;
		}
		return 0;
	}
	
	 
	/*******************************������********************************/
	public void findOneAnswer(DiamondChess Frame)	 
	{	
		this.frame=Frame;
		//frame.chessArmy[0][0].setLife(true);
		Init_diamond(); //��ʼ��
		top = nx = ny = ndir = 0;
		System.out.println("���ݱ�����ֱ���ҵ�һ���� ");
		// ���ݱ�����ֱ���ҵ�һ����
		while(true){
			boolean backFlag=false;
		//	System.out.println("��һ��ѭ��");
			if(Left_diamond == 1 && diamond[3][3] == '*')
			{
				break;
			} 
			for(y=ny; y<7; y++,nx=0)  //nx,ny������һ��Ҫ�ƶ������ӵ���ʼλ�ã���
			{
				for(x=nx; x<7; x++,ndir=0)
				{
					for(int dir=ndir; dir<4; dir++)
					{
						if(Move_diamond(y, x, dir)==1)//����˲�����
						{	
							//�˴����´������̣�
							Left_diamond--;
							nx = ny = ndir = 0;
							backFlag=true;
							//goto nextstep;	//��ο�ʼ��һ�δ�ѭ��	 						
						}
						if(backFlag)
							break;
					}
					if(backFlag)
						break;
				}
				if(backFlag)
					break;
			} 
		//nextstep ��һ��
			 if(y == 7)
				{	
				 	System.out.println("top="+top);
					top--;
					// �ص���һ���� ���ı䷽��ע��Ҫ�ı䷽�򣬹ؼ���һ����
					if(top >= 0)
					{
						last_step = mystack[top];
						//��ԭ�������̵�һ��
						frame.BackOneStep(last_step);
						diamond[(last_step.sy + last_step.ty)/2][(last_step.sx + last_step.tx)/2] = '*';
						diamond[last_step.sy][last_step.sx] = '*';
						diamond[last_step.ty][last_step.tx] = '.';
						nx = last_step.sx;//��Ҫ����һ�ο�ʼ��λ��
						ny = last_step.sy;//��Ҫ
						ndir = last_step.dir + 1;//�ı䷽����Ҫ����
						Left_diamond++;
					}
					else  //���ջ��
					{
						System.out.println("�Ҳ����κ�һ����!");
						flag=0;
						break;
					}
				}
		
		}//��ѭ��while��1������
		System.out.println("�ɹ��ҵ���һ���⣡");
		//printAnswer();
	}
	public void printAnswer(){
		int i;
		for(i=0;i<top;i++){
			Step step=new Step();
			step=mystack[i];
			System.out.println("Move"+"("+(step.sy+1)+","+(step.sx+1)+")"+"to"
							+"("+(step.ty+1)+","+(step.tx+1)+")");
		}
	}
}
