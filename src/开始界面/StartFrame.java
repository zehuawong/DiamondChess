package ��ʼ����;

import ��ʾ�߷�.DC;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ��Ϸ.*;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Color;

public class StartFrame {
	/**
	 * Launch the application.
	 */
	static private JFrame frame=new JFrame();
	static JButton button1 = new JButton("��ʾ");
	static JButton button2 = new JButton("��ս");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame1 = new StartFrame();
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		//frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(400, 100, 978, 667);
		frame.setTitle("������ʯ����");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon image = new ImageIcon(this.getClass().getResource("��ʯ.png"));
		frame.setIconImage(image.getImage());// ����ʶ��ͼ��

		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);

		
		button1.setBackground(Color.PINK);
		button1.setFont(new Font("����", Font.BOLD, 35));

		button1.setBounds(frame.getWidth() / 2 - 180, 36, 178, 96);
		// button1.setOpaque(false);
		contentPane.add(button1);

		
		button2.setBackground(Color.PINK);
		button2.setFont(new Font("����", Font.BOLD, 35));
		button2.setBounds(frame.getWidth() / 2 + 50, 36, 178, 96);
		contentPane.add(button2);
		contentPane.setOpaque(false);// �����������͸��
		// ��Ϸ����ͼƬ
		Image bg = new ImageIcon("src\\��ʼ����\\��ʼ����.png").getImage();
		JLabel backLabel = new aLabel(bg);
		backLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());// ���ñ�����ǩ��λ��

		frame.getLayeredPane().add(backLabel, new Integer(Integer.MIN_VALUE));
		// ���Ӵ��ڴ�С�ļ�����
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				button1.setBounds(frame.getWidth() / 2 - 180, 36, 178, 96);// �������ð�ť��λ��
				button2.setBounds(frame.getWidth() / 2 + 50, 36, 178, 96);
				backLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());// ���ñ�����ǩ��λ��
			}
		});
		
		//��ʾ��ť
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//frame.dispose();
				��ʾ�߷�.DC dc = new ��ʾ�߷�.DC();
			//	dc.displayOneAnswer();//?Ϊʲô�޷�������ʾ�����ˣ���UI����				 
			}
		});
		//��ս
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//frame.dispose();
				��Ϸ.DC dc=new ��Ϸ.DC(); 
			}
		});
		frame.validate();
	}

	// �ڲ���
	private class aLabel extends JLabel {
		private Image image;

		public aLabel(Image bg) {
			this.image = bg;
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int x = this.getWidth();
			int y = this.getHeight();
			g.drawImage(image, 0, 0, x, y, null);
		}
	}

}
