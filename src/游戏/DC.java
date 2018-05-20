package ��Ϸ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.SwingConstants;

class Step // ��¼�ƶ����ӵ���Ϣ
{
	public int sx, sy; // ��¼�ƶ�����ǰ���ӵ�λ��
	public int tx, ty; // ��¼�ƶ����Ӻ����ӵ�λ��
	public int dir; // dirֵ�����ƶ����ӵķ���
}

public class DC extends JFrame {
	// -----moveʱ���õı�����
	public static int oldx = 0;
	public static int oldy = 0;
	public static int newx = 0;
	public static int newy = 0;
	public static boolean movingflag = false; // �����ƶ������ӱ�ʶ

	private GridLayout optionlayer = new GridLayout(5, 1);
	private GridLayout chessboardlayer = new GridLayout(9, 9);
	// -----���Ҳ��ֵ�jpanel
	private JPanel leftpanel = new JPanel();
	private JPanel rightpanel = new JPanel();
	// -----
	public static int restcount = 32;
	public static int steps = 0;
	public static JLabel jl1 = new JLabel("ʣ������:" + restcount);
	public static JLabel jl2 = new JLabel("��ǰ����:0" + steps);

	public static boolean[][] chessdot;// -----chess����
	public static boolean[][] chessboard;// -----board����//���ֱ߽�
	// ������������ά������Ҫ��Ϊ�˳�ʼ������

	// -----chess���õ��� //��button����ɫ��Ϣ��
	public static OneChess[][] chessArmy = new OneChess[9][9];
	private static Stack<Step> stack=new Stack<Step>();
	private JButton lastStep=new JButton("����");
	private JButton backButton=new JButton("�˳�");
	// -----��ʼ��
	public static void initChessDot() {
		chessdot = new boolean[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (((i < 3 || i > 5) && (j < 3 || j > 5)) || i == 0 || i == 8 || j == 0 || j == 8)
					chessdot[i][j] = false;
				else
					chessdot[i][j] = true;
			}
		}
		chessdot[4][4] = false;
	}

	public static void initChessBoard() {
		chessboard = new boolean[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (((i < 3 || i > 5) && (j < 3 || j > 5)) || i == 0 || i == 8 || j == 0 || j == 8)
					chessboard[i][j] = false;
				else
					chessboard[i][j] = true;
			}
		}

	}
	void initChessArmy() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				boolean flag = chessdot[col][row];
				OneChess oc = new OneChess(flag, col, row);// һ������
				if (!chessboard[col][row]) { // ����������ӣ����Ǳ߽�Ļ�
					oc.setBoard(); // ���ñ߽�Ϊ��ɫ
					JLabel borderLabel = new JLabel(); // ��ǩ���뵽�ұߵ������
					borderLabel.setBackground(Color.DARK_GRAY);
					borderLabel.setOpaque(false);
					this.rightpanel.add(borderLabel);
				} else {
					this.rightpanel.add(oc); // ����button���뵽�ұ������
				}
				this.chessArmy[col][row] = oc; // ���ú���button����ɫ��Ϣ��������

			}
		}
	}
	public DC() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon image = new ImageIcon(this.getClass().getResource("��ʯͼ��.png"));
		this.setIconImage(image.getImage());// ����ʶ��ͼ��

		// ��Ϸ����ͼƬ
		ImageIcon bg = new ImageIcon(this.getClass().getResource("����1.jpg"));
		JLabel backLabel = new JLabel(bg);
		backLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
		this.getLayeredPane().add(backLabel, new Integer(Integer.MIN_VALUE));

		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setOpaque(false);// �����������͸��
		this.setBounds(400, 100, 1080, 800);
		this.setTitle("������ʯ����");
		// this.setResizable(false);//���ô��ڲ��ɵ�����С
		// contentPane.setLayout(gamepanellayer);
		contentPane.setLayout(new BorderLayout());
		contentPane.add(this.leftpanel, BorderLayout.WEST); // ������

		leftpanel.setBackground(Color.gray);// new Color(100,100,100)
		leftpanel.setOpaque(false);
		optionlayer.setVgap(20);
		leftpanel.setLayout(this.optionlayer);
		jl1.setForeground(Color.YELLOW);
		jl1.setBounds(5, 5, 179, 35);
		jl1.setVerticalAlignment(SwingConstants.BOTTOM);
		jl1.setFont(new Font("����", Font.BOLD, 30)); // ʣ�ಽ�������߲���
		jl1.setBackground(Color.BLACK);
		this.leftpanel.add(jl1);
		jl2.setForeground(Color.YELLOW);
		jl2.setBounds(189, 5, 179, 35);
		jl2.setVerticalAlignment(SwingConstants.TOP);
		jl2.setFont(new Font("����", Font.BOLD, 30));
		jl2.setBackground(Color.BLACK);
		this.leftpanel.add(jl2);
		//���尴ť
		lastStep.setForeground(Color.YELLOW);
		lastStep.setForeground(Color.YELLOW);
		lastStep.setBackground(Color.BLACK);
		lastStep.setFont(new Font("����", Font.BOLD, 30));
		lastStep.setOpaque(false);// ��ť����͸��
		lastStep.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				do_lastStepButton_click( e);
			}	
		});
		this.leftpanel.add(lastStep);
		
		 
		backButton.setBackground(Color.BLACK);
		backButton.setForeground(Color.YELLOW);
		backButton.setOpaque(false);// ��ť����͸��
		backButton.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				do_backButton_click( e);
			}	
		});
		backButton.setFont(new Font("����", Font.BOLD, 30));
		this.leftpanel.add(backButton);
		
		contentPane.add(this.rightpanel, BorderLayout.CENTER);// �ұ����
		rightpanel.setOpaque(false);// �ұ��������͸��
		this.rightpanel.setLayout(chessboardlayer);// �ұ߲���9*9
		initChessBoard();
		initChessDot();
		initChessArmy();
		this.setVisible(true);

	}

	

	public static boolean canMove() { // ��̬����
		boolean f = false;
		if (oldx == newx && (oldy == newy + 2 || newy == oldy + 2)) { // xˮƽ�����ƶ�����
			if (chessArmy[oldx][(newy + oldy) / 2].getLife()) { // ����м��������
				f = true;
			} else {
				f = false;
			}
		}
		if (oldy == newy && (oldx == newx + 2 || newx == oldx + 2)) { // y�����ƶ�����
			if (chessArmy[(newx + oldx) / 2][oldy].getLife()) { // ����м��������
				f = true;
			} else {
				f = false;
			}
		}
		return f;
	}

	public static void doMove() { // ��̬����
		if (oldx == newx) {
			chessArmy[oldx][(newy + oldy) / 2].setLife(false);
		}
		if (oldy == newy) {
			chessArmy[(newx + oldx) / 2][oldy].setLife(false);
		}
		chessArmy[oldx][oldy].setLife(false);
		chessArmy[newx][newy].setLife(true);
		Step step=new Step();
		step.sx=oldx;step.sy=oldy;
		step.tx=newx;step.ty=newy;
		stack.push(step);//�߷���¼��ջ		
	}
	//�������
	private void do_lastStepButton_click(MouseEvent e){
		if(!stack.isEmpty()){
			Step step=new Step();			
			step=stack.peek();
			stack.pop();
			//chessArmy[step.tx][step.ty].setMoving();			
			chessArmy[step.tx][step.ty].setLife(false);
			chessArmy[(step.sx+step.tx)/2][(step.sy+step.ty)/2].setLife(true);
			chessArmy[step.sx][step.sy].setLife(true);	
			//�޸ĵ�ǰ������ʣ������
			steps--;
			restcount++;
			jl1.setText("ʣ������:" + restcount);
			jl2.setText("��ǰ����:" + steps);
		}
	}
	//�˳�����
	private void  do_backButton_click(MouseEvent e){
		this.dispose();
	}
	public static void main(String[] args) {
		DC dc = new DC();
	}

}
