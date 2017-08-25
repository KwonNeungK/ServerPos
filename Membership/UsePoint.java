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
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class UsePoint extends JDialog{
	PointDialog parent;
	public PointDBManager manager = new PointDBManager();
	
	private JLabel  contactLabel= new JLabel("����ó");
	private JTextField contactText = new JTextField();
	private JLabel  nameLabel= new JLabel("�̸�");
	private JLabel  pointLabel= new JLabel("����Ʈ");
	private JLabel willSpend = new JLabel("����Ͻ� ����Ʈ : ");
	private JTextField pointText = new JTextField();
	private JButton btnInquiry = new JButton("��ȸ");
	private JButton btnUse = new JButton("���");
	private JPanel panelUse = new JPanel(new GridLayout(4, 2));
	private JPanel usePoint = new JPanel(new GridLayout(2, 1));
	private int returnpoint;
	public int pointPrice;
	public void compInit(){
		this.panelUse.add(contactLabel);
		this.panelUse.add(contactText);
		this.panelUse.add(nameLabel);
		this.panelUse.add(pointLabel);
		this.usePoint.add(willSpend);
		this.usePoint.add(pointText);
		this.panelUse.add(usePoint);
		this.panelUse.add(btnInquiry);
		this.panelUse.add(btnUse);
		this.add(panelUse);
	}

	public void eventInit(){
		this.btnInquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int contact = Integer.parseInt(contactText.getText());
				try {
					ArrayList<MembershipPoint> result = manager.selectPoint(contact); 
					// MembershipPoint���� ���� Arraylist�� result�� dbmanager���� contact�� �Ѱ��־ 
					for( MembershipPoint tmp : result){
						nameLabel.setText(tmp.getName());
						pointLabel.setText(Integer.toString(tmp.getPoint()));

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
		});
		this.btnUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int point = Integer.parseInt(pointText.getText());
					int contact = Integer.parseInt(contactText.getText()); // �ؽ�Ʈ�ʵ忡 ���� ����Ʈ�� ����ó�� �����´�.

					ArrayList<MembershipPoint> result = manager.selectPoint(contact);
					for( MembershipPoint tmp : result){
						if( tmp.getPoint() > Integer.parseInt(pointText.getText())) {
							returnpoint = tmp.getPoint() - Integer.parseInt(pointText.getText()); // Integer.�κ��� ���߿� Server�� ���� �ݾ� �� �Ϻη� �޾ƿ��� ���� �����ߵ�.
							pointPrice = Integer.parseInt(parent.parent.self.tf1.getText()) // dialog�� parent�� server�� �ִ� tf ���� ���� �ҷ����� ��
									- Integer.parseInt(pointText.getText());
							parent.parent.self.tf1.setText(""+pointPrice); // ���� ����� ���� �ο��ϴ� ��.
							
							int insertResult = manager.updatePoint(returnpoint,contact);
							if(insertResult > 0) {
								JOptionPane.showMessageDialog(parent,"����Ʈ�� ���Ǿ����ϴ�.");
								dispose();					//���� ������ ���� 
							}else {
								JOptionPane.showMessageDialog(parent,"������ �߻��߽��ϴ�.");
							}
						} else {
							JOptionPane.showMessageDialog(parent, "������ ����Ʈ�� �����մϴ�.");
						}
					}// �ۿ��� �������� 
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(parent,"���� �߻�! �����ڿ��� �����ϼ���.");
				} 

			}
		});
	}
	public UsePoint(PointDialog parent) {
		this.parent = parent;
		this.setSize(500,500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(parent);
		this.compInit();
		this.eventInit();
		this.setVisible(true);
	}

}
