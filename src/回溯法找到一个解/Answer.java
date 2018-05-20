package ���ݷ��ҵ�һ����;

import ���ݷ�������ʾ.*;

public class Answer {
	public static Step mystack[] = new Step[32];//��¼�߷���ջ
	public static int top;//ջ��ָ��
	public static Step last_step = new Step();// ��һ��
	public static char diamond[][] = new char[7][7];//���̣���ά�����ʾ
	public static int Left_diamond = 32;//ʣ������
	public static int x, y, nx, ny, ndir;// ndirֵ������, 0��������, 1��������, 2��������, 3��������
	static int flag = 1;// �Ƿ�ɹ��ҵ���ı�־
	public static DiamondChess frame;
	int count = 0;//�������ڲ�ѭ���Ĵ���
	int backCount = 0;//���ݵĴ���

	public static void Init_diamond() {//��ʼ������
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if ((i < 2 || i > 4) && (j < 2 || j > 4))
					;
				else {
					diamond[i][j] = '*';//������*���
				}
			}
		}
		diamond[3][3] = '.';
	}

	static int Move_diamond(int y, int x, int dir) {//�ж�������dir�������Ƿ���ߣ�����������߷���ջ
		if (diamond[y][x] != '*') {
			return 0;
		}
		Step temp = new Step();
		switch (dir) // �ĸ�������߷�
		{
		case 0:
			if (x + 2 > 6 || diamond[y][x + 1] != '*' || diamond[y][x + 2] != '.') {
				return 0;
			}
			diamond[y][x] = diamond[y][x + 1] = '.';//ԭ����λ�úͿ�������Ӽ�¼Ϊ���
			diamond[y][x + 2] = '*';
			temp.sx = x; //��¼�߷�λ��
			temp.sy = y;
			temp.tx = x + 2;
			temp.ty = y;
			temp.dir = dir;
			mystack[top++] = temp; // �߷���ջ top++
			return 1;
		case 1:
			if (y + 2 > 6 || diamond[y + 1][x] != '*' || diamond[y + 2][x] != '.') {
				return 0;
			}
			diamond[y][x] = diamond[y + 1][x] = '.';
			diamond[y + 2][x] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x;
			temp.ty = y + 2;
			temp.dir = dir;

			mystack[top++] = temp;
			return 1;

		case 2:
			if (x - 2 < 0 || diamond[y][x - 1] != '*' || diamond[y][x - 2] != '.') {
				return 0;
			}
			diamond[y][x] = diamond[y][x - 1] = '.';
			diamond[y][x - 2] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x - 2;
			temp.ty = y;
			temp.dir = dir;

			mystack[top++] = temp;
			return 1;
		case 3:
			if (y - 2 < 0 || diamond[y - 1][x] != '*' || diamond[y - 2][x] != '.') {
				return 0;
			}
			diamond[y][x] = diamond[y - 1][x] = '.';
			diamond[y - 2][x] = '*';
			temp.sx = x;
			temp.sy = y;
			temp.tx = x;
			temp.ty = y - 2;
			temp.dir = dir;

			mystack[top++] = temp;
			return 1;
		default:
			break;
		}
		return 0;
	}
	public void findOneAnswer() {//�ҵ�����һ���߷��ķ���
		Init_diamond(); // ��ʼ������
		top = nx = ny = ndir = 0;
		System.out.println("���ݱ�����ֱ���ҵ�һ���� ");
		while (true) {
			boolean backFlag = false;
			if (Left_diamond == 1 && diamond[3][3] == '*') {//�������ֻʣ��һ�������������룬��ɹ��ҵ�һ���⣬����ѭ��
				break;
			}
			for (y = ny; y < 7; y++, nx = 0) //nx,ny������һ��Ҫ�ƶ������ӵ���ʼλ��
			{
				for (x = nx; x < 7; x++, ndir = 0) {
					for (int dir = ndir; dir < 4; dir++) {
						count++;
						if (Move_diamond(y, x, dir) == 1)// ����˲�����
						{					 
							Left_diamond--;
							nx = ny = ndir = 0;
							backFlag = true;//����forѭ�����ж��Ƿ���Ҫ���ݣ����±�������					
						}
						if (backFlag)
							break;
					}
					if (backFlag)
						break;
				}
				if (backFlag)
					break;
			}
			if (y == 7) {//���ݵ�������y==7
				backCount++;
				top--;
				if (top >= 0) {	// �ص���һ���� ���ı䷽��
					last_step = mystack[top];
					//��ԭ����
					diamond[(last_step.sy + last_step.ty) / 2][(last_step.sx + last_step.tx) / 2] = '*';
					diamond[last_step.sy][last_step.sx] = '*';
					diamond[last_step.ty][last_step.tx] = '.';
					nx = last_step.sx;
					ny = last_step.sy;
					ndir = last_step.dir + 1;
					Left_diamond++;
				} else // ���ջ��
				{
					System.out.println("�Ҳ����κ�һ����!");
					flag = 0;
					break;
				}
			}

		} // ��ѭ��while��1������
		System.out.println("�ɹ��ҵ���һ���⣡");
	}

	public void printAnswer() {//��ӡ�߷�
		System.out.println("���ݵĴ���" + backCount);
		System.out.println("�������ڲ�ѭ���Ĵ���" + count);
		int i;
		for (i = 0; i < top; i++) {
			Step step = new Step();
			step = mystack[i];
			System.out.println("Move" + "(" + (step.sy + 1) + "," + (step.sx + 1) + ")" + "to" + "(" + (step.ty + 1)
					+ "," + (step.tx + 1) + ")");
		}
	}
}
