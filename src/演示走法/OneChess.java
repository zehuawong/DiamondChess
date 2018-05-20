package ��ʾ�߷�;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class OneChess extends JButton {
	private boolean life;
	public int xp;
	public int yp;

	public OneChess(boolean sl, int x, int y) {
		setLife(sl);
		xp = x;
		yp = y;
		// this.addMouseListener(this); // ��ǰ�����������¼�������
	}

	public void setLife(boolean l) { // ���������ǲ�������
		life = l;
		if (l) {
			setBackground(Color.black);
			ImageIcon image = new ImageIcon(this.getClass().getResource("��ɫ��ʯ.png"));
			this.setIcon(image);

		} else {
			setBackground(Color.black);
			this.setIcon(null);
		}
	}

	public void setMoving() { // �����ƶ������ӣ��������е���
		ImageIcon image = new ImageIcon(this.getClass().getResource("��ɫ��ʯ.png"));
		this.setIcon(image);
	}

	public void setBoard() { // ���ñ߽�
		setBackground(Color.WHITE);
	}

	public boolean getLife() {
		return life;
	}

}