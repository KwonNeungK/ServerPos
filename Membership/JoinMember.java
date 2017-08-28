package Membership;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JoinMember extends JDialog{
	PointDialog parent;
	private PointDBManager manager = new PointDBManager();

	
	private JLabel nameLabel = new JLabel("�̸�");
	private JTextField nameText = new JTextField();
	private JLabel  contactLabel= new JLabel("����ó");
	private JTextField contactText = new JTextField();
	private JLabel  blank = new JLabel("");
	private JButton btnJoin = new JButton("����");
	private JPanel panelJoin = new JPanel(new GridLayout(3, 2));
	
	public void compInit(){
		this.panelJoin.add(nameLabel);
		this.panelJoin.add(nameText);
		this.panelJoin.add(contactLabel);
		this.panelJoin.add(contactText);
		this.panelJoin.add(blank);
		this.panelJoin.add(btnJoin);
		this.add(panelJoin);
	}
	
	public void eventInit(){
		this.btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String name = nameText.getText();
					int contact = Integer.parseInt(contactText.getText()); // �̸�, ����ó�� String�� int������ ����
					int zero = 0;
					
					int insertResult = manager.insertMember(name, contact, zero); // DBmanager���� ���� result�� �������� ��. 
					
					if(insertResult > 0) {
						JOptionPane.showMessageDialog(parent,"ȸ�����Կ� �����߽��ϴ�.");
						dispose();					//���� ������ ���� 
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(parent,"���� �߻�");
				} 
			}
		});
	}
	public JoinMember(PointDialog parent) {
		this.parent = parent;
		this.setSize(300,150);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(parent);
		this.compInit();
		this.eventInit();
		this.setVisible(true);
	}

}
