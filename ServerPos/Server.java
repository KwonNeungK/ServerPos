package ServerPos;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Membership.PointDialog;

public class Server extends JFrame{
	
	public int money = 100000;
	
	//���� ��Ȳ
	String num = null;
	String tabelend;
	String listdel;
	int tableColor;

	final PopupMenu pMenu = new PopupMenu("Edit"); //final�� �ؾ� �̺�Ʈ �ڵ鷯 ���డ��
	MenuItem menu = new MenuItem("�޴�����");

	int total = 0;
	private MultiThread mt;

	public Server self = this;
	HashMap<String, ArrayList<Order>> foodlist = new HashMap<String, ArrayList<Order>>();

	DefaultTableModel model;
	JTable list;
	JScrollPane js;

	private String[] menulist = new String[] {"����","����","����"};

	private JLabel log = new JLabel();
	
	JButton[] btn = new JButton[15];
	JPanel pan = new JPanel(new GridLayout(3, 5));

	private JLabel la1 = new JLabel("��   �� : ");
	private JLabel la2 = new JLabel("������ : ");
	private JLabel la3 = new JLabel("��   �� : ");

	public JTextField tf1 = new JTextField();   //���ձ�
	JTextField tf2 = new JTextField();	 //������	
	JTextField tf3 = new JTextField();   //�ܵ�

	private JButton complete = new JButton("�Ϸ�");
	private JButton cancel = new JButton("���");
	private JPanel pan2 = new JPanel(new GridLayout(1, 2));
	
	public JButton btn1 = new JButton(new ImageIcon("1.jpg"));    //����
	private JButton btn2 = new JButton(new ImageIcon("2.jpg"));  //���� ��Ȳ
	public JButton btn3 = new JButton(new ImageIcon("33.jpg"));  //����
	private JButton btn4 = new JButton(new ImageIcon("4.jpg"));  //����Ʈ
	private JButton btn5 = new JButton("������");  //������
	private JButton btn6 = new JButton("����");   //����
	private JPanel pan3 = new JPanel(new GridLayout(1, 6));
	private JPanel pan4 = new JPanel(new GridLayout(3, 1));
	private JPanel pan5 = new JPanel(new GridLayout(3, 1));



	private void table(){  //���̺��ġ
		new Table(self);
	}

	public void arrlist(String a, ArrayList<Order> b){
		foodlist.put(a, b);
	}
	public ArrayList<Order> menulist(String num){
		return foodlist.get(num);  // �ֹ��� ���� ����Ʈ
	}

	public HashMap<String, ArrayList<Order>> getFooldList(){
		return foodlist;  //�߰� �ֹ� Ȯ�� �޼���
	}


	private void allEvent(){
		new BtnEvent(self);  //���̺� ��ȣ
		new PopupEvent(self);  // �Ǿ�â 
		new Discount.DiscountEvent(self); //����
		new Calculate.CalculateEvent(self);
		
		
		btn6.addActionListener(new ActionListener() {//����
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	private void time(){
		new Time.TimeBar(self);
	}


	private void textEvent(){   //��� �� ���̺� �ʱ�ȭ
		tf2.addActionListener(new ActionListener() {  //���
			public void actionPerformed(ActionEvent e) {
				int num1 = Integer.parseInt(tf1.getText()); //���ձ�
				int num2 = Integer.parseInt(tf2.getText()); //������
				int num3 = num2 - num1;  //�ܵ�
				tf3.setText(""+num3);
			}
		});
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PointDialog(self).setVisible(true);
			}
		});
		complete.addActionListener(new ActionListener() { //�Ϸ� ��ư
			public void actionPerformed(ActionEvent e) {
				int del = model.getRowCount();
				for(int i = 0;i<del;i++){
					model.removeRow(0);
				}
				total = Integer.parseInt(tf1.getText());
				money += total;
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");

				btn[self.tableColor].setBackground(null);
				foodlist.remove(listdel);
			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int del = model.getRowCount();
				for(int i = 0;i<del;i++){
					model.removeRow(0);
				}
				tf1.setText("");
			}
		});

	}

	private void comInit(){
		this.setLayout(null);
		model = new DefaultTableModel(menulist,0);
		list = new JTable(model);
		js = new JScrollPane(list);

		this.add(js);
		this.js.setBounds(851, 101, 339, 300);
		//��� ���̺� ���� ����
		this.list.getColumnModel().getColumn(0).setPreferredWidth(250);
		this.list.getColumnModel().getColumn(1).setPreferredWidth(40);

		//��� ���̺� ���� ����
		DefaultTableCellRenderer tableRender = new DefaultTableCellRenderer();
		tableRender.setHorizontalAlignment(JLabel.CENTER);
		list.getColumnModel().getColumn(0).setCellRenderer(tableRender);
		list.getColumnModel().getColumn(1).setCellRenderer(tableRender);
		list.getColumnModel().getColumn(2).setCellRenderer(tableRender);


		//---------���� ������ �ܵ�
		la1.setHorizontalAlignment(JLabel.LEFT);
		la2.setHorizontalAlignment(JLabel.LEFT);
		la3.setHorizontalAlignment(JLabel.LEFT);

		this.pan4.add(la1);
		this.la1.setFont(new Font("���� ���", Font.BOLD, 20));
		this.pan4.add(la2);
		this.la2.setFont(new Font("���� ���", Font.BOLD, 20));
		this.pan4.add(la3);
		this.la3.setFont(new Font("���� ���", Font.BOLD, 20));

		this.pan5.add(tf1);
		this.pan5.add(tf2);
		this.pan5.add(tf3);

		this.add(pan4);
		this.add(pan5);
		this.pan4.setBounds(855, 401, 80, 150);  //���� ������ �ܵ� ���� 
		this.pan5.setBounds(940, 403, 250, 150);	 //���� ������ �ܵ� �ʵ� ����
		//------------�Ϸ� ���
		this.pan2.add(complete);
		this.pan2.add(cancel);
		this.add(pan2);  
		this.pan2.setBounds(853, 555, 337, 60);  //�Ϸ� ���
		//-----------���� ������ ���� ����
		this.pan3.add(btn1); //����
		this.pan3.add(btn2); //������Ȳ
		this.pan3.add(btn3); //����
		this.pan3.add(btn4); //����Ʈ
		this.pan3.add(btn5); //������
		this.pan3.add(btn6); //����
		this.add(pan3);
		this.pan3.setBounds(0, 616, 1191, 40); //���� ������ ��� ���� ����
		//---�ΰ�
		this.add(log);
		this.log.setBounds(0, 0, 1190, 100);
		this.log.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.log.setOpaque(true); //Opaque���� true�� �̸� ������ �־�� ������ ����ȴ�
		this.log.setBackground(Color.white);


		

	}



	public Server() throws Exception{
		this.setTitle("BLACKSTONE");
		this.setSize(1201,715);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.time();
		this.table();
		this.allEvent();
		this.textEvent();
		this.comInit();
		this.setVisible(true);

		ServerSocket server = new ServerSocket(20000);

		while (true) {
			System.out.println("������ ������ ����ϴ� �� �Դϴ�.");
			Socket sock = server.accept(); // ��Ʈ��ũ���� 20000��Ʈ�� ������ ������ ���
			System.out.println(sock.getInetAddress() + "���� ���� �߽��ϴ�.");
			mt = new MultiThread(self,sock);
			mt.start();
		}
	}
	public static void main(String[] args) throws Exception{
//		try {
//			// select Look and Feel
//			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//			// start application
			new Server();
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
	} // end main

}