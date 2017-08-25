package Membership;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class PointDialog extends JDialog{
	public ServerPos.Server parent = null;
	PointDialog pDialog = this;
	private JButton bt1 = new JButton("회원 등록");
	private JButton bt2 = new JButton("적립");
	private JButton bt3 = new JButton("사용");
	private JPanel panel = new JPanel(new GridLayout(3, 1));
	
	private void compInit(){
	
		this.panel.add(bt1);
		this.panel.add(bt2); 
		this.panel.add(bt3);
		this.add(panel);
		
		
	}
	private void eventInit(){
		this.bt1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JoinMember(pDialog).setVisible(true);
			}
		});
		this.bt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddPoint(pDialog).setVisible(true);
			}
		});
		this.bt3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UsePoint(pDialog).setVisible(true);
			}
		});
	}
	
	
	public PointDialog(ServerPos.Server parent) {
		this.parent = parent;
		this.setSize(500,300);
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.compInit();
		this.eventInit();
		this.setVisible(true);
	}
	
}
