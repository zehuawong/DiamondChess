package ��ʾ�߷�;

import ���ݷ��ҵ�һ����.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class DC extends JFrame implements ActionListener {
	// -----moveʱ���õı�����
	public static int oldx = 0;
	public static int oldy = 0;
	public static int newx = 0;
	public static int newy = 0;
	public static boolean movingflag = false; // �����ƶ������ӱ�ʶ

	private GridLayout optionlayer = new GridLayout(8, 1);
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
	JButton startButton = new JButton("��ʼ");
	JButton stopButton = new JButton("��ͣ");
	JButton continueButton = new JButton("����");
	JButton backButton = new JButton("�˳�");
	displayOneAnswer display;// ��ʾ���̵߳�Ŀ�����
	Thread displayThread;
	Answer answer = new Answer();
	int sleepTime = 1000;
	private final JButton upButton = new JButton("����+");
	private final JButton downButton = new JButton("����-");

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
		ImageIcon image = new ImageIcon(this.getClass().getResource("��ʯ.png"));
		this.setIconImage(image.getImage());// ����ʶ��ͼ��
		this.setVisible(true);
		// ��Ϸ����ͼƬ
		
		ImageIcon bg = new ImageIcon(this.getClass().getResource("����1.jpg"));
		JLabel backLabel = new JLabel(bg);
		backLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
		this.getLayeredPane().add(backLabel, new Integer(Integer.MIN_VALUE));

		
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setOpaque(false);// �����������͸��
		this.setBounds(400, 100, 1080, 800);
		this.setTitle("������ʯ�����߷���ʾ");
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
		jl1.setBackground(Color.blue);
		this.leftpanel.add(jl1);
		jl2.setForeground(Color.YELLOW);
		jl2.setBounds(189, 5, 179, 35);
		jl2.setVerticalAlignment(SwingConstants.TOP);
		jl2.setFont(new Font("����", Font.BOLD, 30));
		jl2.setBackground(Color.BLUE);
		this.leftpanel.add(jl2);

		contentPane.add(this.rightpanel, BorderLayout.CENTER);// �ұ����
		rightpanel.setOpaque(false);// �ұ��������͸��
		this.rightpanel.setLayout(chessboardlayer);// �ұ߲���9*9
		initChessBoard();
		initChessDot();
		initChessArmy();
		startButton.setForeground(Color.YELLOW);
		startButton.setBackground(Color.BLACK);
		startButton.setFont(new Font("����", Font.BOLD, 30));
		// ��ʼ��ť
		startButton.addActionListener(this);
		this.leftpanel.add(startButton);

		stopButton.setForeground(Color.YELLOW);
		stopButton.setBackground(Color.BLACK);
		stopButton.setFont(new Font("����", Font.BOLD, 30));
		stopButton.setOpaque(false);// ��ť����͸��
		stopButton.addActionListener(this);
		this.leftpanel.add(stopButton);

		continueButton.setForeground(Color.YELLOW);
		continueButton.setBackground(Color.BLACK);
		continueButton.setFont(new Font("����", Font.BOLD, 30));
		continueButton.addActionListener(this);
		this.leftpanel.add(continueButton);

		backButton.setBackground(Color.BLACK);
		backButton.setForeground(Color.YELLOW);
		backButton.addActionListener(this);
		backButton.setFont(new Font("����", Font.BOLD, 30));

		// ���ٰ�ť
		upButton.setForeground(Color.YELLOW);
		upButton.setBackground(Color.BLACK);
		upButton.setFont(new Font("����", Font.BOLD, 30));
		upButton.setOpaque(false);// ��ť����͸��
		upButton.addActionListener(this);
		leftpanel.add(upButton);
		// ���ٰ�ť
		downButton.setForeground(Color.YELLOW);
		downButton.setBackground(Color.BLACK);
		downButton.setFont(new Font("����", Font.BOLD, 30));
		downButton.setOpaque(false);// ��ť����͸��
		downButton.addActionListener(this);
		leftpanel.add(downButton);
		this.leftpanel.add(backButton);

		answer.findOneAnswer();// ���ݷ��ҵ�һ����
		display = new displayOneAnswer();// ��ʾ���߳� ��Ŀ�����
		displayThread = new Thread(display);// ��ʾ���߳�

		this.validate(); // ע��Ҫ���ô˷�������������޷���ʾ
	}

	// �ڲ���ķ�ʽִ����ʾ�߳�
	public class displayOneAnswer implements Runnable {
		boolean stopFlag = false;// �����߳̽�����־
		int i = 0;

		public void setStopFlag(boolean flag) {
			stopFlag = flag;
		}

		public void run() {
			for (; i < Answer.top && stopFlag == false; i++) {
				// System.out.println("i=" + i);
				Step step = new Step();
				step = answer.mystack[i];
				chessArmy[step.sx + 1][step.sy + 1].setMoving();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e1) {
				}
				chessArmy[step.sx + 1][step.sy + 1].setLife(false);
				chessArmy[(step.sx + step.tx) / 2 + 1][(step.sy + step.ty) / 2 + 1].setLife(false);
				jl1.setText("ʣ������:" + (31 - i));
				jl2.setText("���߲���:" + (i + 1));
				chessArmy[step.tx + 1][step.ty + 1].setMoving();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e2) {
				}
				chessArmy[step.tx + 1][step.ty + 1].setLife(true);
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			startButton.setEnabled(false);// ���ð�ťʧЧ����ֹ�ٴε����ʼ��ť�������쳣
			displayThread.start();

		} else if (e.getSource() == stopButton) {
			displayThread.suspend(); // ����������������������õĻ����ᷢ���̰߳�ȫ����
		} else if (e.getSource() == continueButton) {
			displayThread.resume();//
		} else if (e.getSource() == upButton) {
			sleepTime *= 0.5;
		} else if (e.getSource() == downButton) {
			sleepTime *= 2;
		} else if (e.getSource() == backButton) {// ���ذ�Ť
			display.setStopFlag(true);
			// displayThread.stop();
			this.dispose();
			// StartFrame frame=new StartFrame();
		}
	}

	public static void main(String[] _s) {
		DC dc = new DC();
	}

}
