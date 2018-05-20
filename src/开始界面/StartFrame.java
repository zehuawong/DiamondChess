package 开始界面;

import 演示走法.DC;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import 游戏.*;

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
	static JButton button1 = new JButton("演示");
	static JButton button2 = new JButton("挑战");
	
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
		frame.setTitle("独立钻石跳棋");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon image = new ImageIcon(this.getClass().getResource("钻石.png"));
		frame.setIconImage(image.getImage());// 窗口识别图标

		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);

		
		button1.setBackground(Color.PINK);
		button1.setFont(new Font("楷体", Font.BOLD, 35));

		button1.setBounds(frame.getWidth() / 2 - 180, 36, 178, 96);
		// button1.setOpaque(false);
		contentPane.add(button1);

		
		button2.setBackground(Color.PINK);
		button2.setFont(new Font("楷体", Font.BOLD, 35));
		button2.setBounds(frame.getWidth() / 2 + 50, 36, 178, 96);
		contentPane.add(button2);
		contentPane.setOpaque(false);// 内容面板设置透明
		// 游戏背景图片
		Image bg = new ImageIcon("src\\开始界面\\开始界面.png").getImage();
		JLabel backLabel = new aLabel(bg);
		backLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());// 设置背景标签的位置

		frame.getLayeredPane().add(backLabel, new Integer(Integer.MIN_VALUE));
		// 增加窗口大小的监听器
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				button1.setBounds(frame.getWidth() / 2 - 180, 36, 178, 96);// 重新设置按钮的位置
				button2.setBounds(frame.getWidth() / 2 + 50, 36, 178, 96);
				backLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());// 设置背景标签的位置
			}
		});
		
		//演示按钮
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//frame.dispose();
				演示走法.DC dc = new 演示走法.DC();
			//	dc.displayOneAnswer();//?为什么无法正常显示界面了，：UI阻塞				 
			}
		});
		//挑战
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//frame.dispose();
				游戏.DC dc=new 游戏.DC(); 
			}
		});
		frame.validate();
	}

	// 内部类
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
