package ���ݷ�������ʾ;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class OneChess extends JButton {
	private boolean life;//����ť���Ƿ�Ϊ���ӵı�ʶ
	public int xp;//xp��yp��ʶ���ӵ�λ����Ϣ
	public int yp;
	public OneChess(boolean sl, int x, int y) {
		setLife(sl);
		xp = x;
		yp = y;
	}
	public void setLife(boolean l) {// ���������ǲ�������
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
	public void setMoving() {// �����ƶ������ӣ��������е���
		ImageIcon image = new ImageIcon(this.getClass().getResource("��ɫ��ʯ.png"));
		this.setIcon(image);
	}
	public boolean getLife() {
		return life;
	}

}