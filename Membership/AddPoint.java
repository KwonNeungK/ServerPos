package Membership;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class AddPoint extends JDialog{
	PointDialog parent;
	ServerPos.Server server;
	private PointDBManager manager = new PointDBManager();


	private JLabel nameLabel = new JLabel("�̸�");
	private JTextField nameText = new JTextField();
	private JLabel  contactLabel= new JLabel("����ó");
	private JTextField contactText = new JTextField();
	private JLabel  pointLabel= new JLabel("����Ʈ");
	private JTextField pointText = new JTextField();
	private JButton btnAdd = new JButton("����");
	private JPanel panelAdd = new JPanel(new GridLayout(3, 2));
	private int returnpoint;
	private final JLabel empty = new JLabel();

	public void compInit(){
		//		this.panelAdd.add(nameLabel);
		//		this.panelAdd.add(nameText);
		this.panelAdd.add(contactLabel);
		this.panelAdd.add(contactText);
		this.panelAdd.add(pointLabel);
		this.panelAdd.add(pointText);
		this.pointText.setText("" +Integer.parseInt(parent.parent.self.tf1.getText())/ 100 *2);
		
		panelAdd.add(empty);
		this.panelAdd.add(btnAdd);
		getContentPane().add(panelAdd);
	}

	public void eventInit(){
		this.btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int point = Integer.parseInt(pointText.getText());
					int contact = Integer.parseInt(contactText.getText()); // �ؽ�Ʈ�ʵ忡 ���� ����Ʈ�� ����ó�� �����´�.

					ArrayList<MembershipPoint> result = manager.selectPoint(contact);
					for( MembershipPoint tmp : result){
						int estimatedPoint = Integer.parseInt(parent.parent.self.tf1.getText())/ 100 *2;
						returnpoint = tmp.getPoint() + estimatedPoint; // Integer.�κ��� ���߿� Server�� ���� �ݾ� �� �Ϻη� �޾ƿ��� ���� �����ߵ�. 
					}// �ۿ��� �������� 
					
					
					int insertResult = manager.updatePoint(returnpoint,contact);
					if(insertResult > 0) {
						JOptionPane.showMessageDialog(parent,"����Ʈ�� �����Ǿ����ϴ�.");
						dispose();					//���� ������ ���� 
					}else {
						JOptionPane.showMessageDialog(parent,"���� ��ȣ�Դϴ�.");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(parent,"���� �߻�! �����ڿ��� �����ϼ���.");
				} 
			}
		});
	}
	public AddPoint(PointDialog parent) {
		this.parent = parent;
		this.setSize(300,150);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(parent);
		this.compInit();
		this.eventInit();
		this.setVisible(true);
	}

}
