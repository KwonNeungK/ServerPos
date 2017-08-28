package Discount;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DiscountDialog extends JDialog{

	private ServerPos.Server parent = null;
	private int price;
	private ImageIcon basicMem = new ImageIcon("직원할인.jpg");
	private ImageIcon basicEvn = new ImageIcon("행사할인.jpg");
	private ImageIcon darkMem = new ImageIcon("직원할인흑.jpg");
	private ImageIcon darkEvn = new ImageIcon("행사할인흑.jpg");

	private JButton memDiscount = new JButton(basicMem);
	private JButton eventDiscount = new JButton(basicEvn);

	private JLabel priceLabel = new JLabel("");
	private JPanel panelInput = new JPanel(new GridBagLayout());

	private void eventInit(){
		this.memDiscount.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				memDiscount.setIcon(darkMem);
				memDiscount.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				memDiscount.setIcon(basicMem);
				memDiscount.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		this.memDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberDis md = new MemberDis(price); // MemberDis 인스턴스 생성자에 price를 넘긴다.
				parent.tf1.setText(""+md.returnMember());
				dispose();
			}
		});

		this.eventDiscount.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				eventDiscount.setIcon(darkEvn);
				eventDiscount.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				eventDiscount.setIcon(basicEvn);
				eventDiscount.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		this.eventDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventDiscount ed = new EventDiscount(price);
				parent.tf1.setText(""+ed.returnEvent());
				dispose();
			}
		});

	}

	private void compInit(){
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(10,5,10,5);
		c.gridy = 2; c.gridx = 2;
		this.memDiscount.setBorderPainted(false);
		this.memDiscount.setContentAreaFilled(false);
		this.panelInput.add(memDiscount,c);

		this.memDiscount.setPreferredSize(new Dimension(130,130));
		c.gridy = 2; c.gridx = 1;

		this.eventDiscount.setBorderPainted(false);
		this.eventDiscount.setContentAreaFilled(false);
		this.panelInput.add(eventDiscount,c);
		this.eventDiscount.setPreferredSize(new Dimension(130,130));

		this.add(panelInput);
	}

	public DiscountDialog(ServerPos.Server parent, int price){
		this.parent = parent;
		this.price = price;
		this.setSize(300,200);
		this.setLocation(1075,225);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.compInit();
		this.eventInit();

	}
}
