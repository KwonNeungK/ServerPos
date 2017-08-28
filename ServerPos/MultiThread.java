package ServerPos;
import java.awt.Color;
import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class MultiThread extends Thread{
	
	private Server ser;
	private String table;
	private String msg;
	
	int num;
	
	private Socket sock;

	ObjectInputStream input;

	public MultiThread(Server ser,Socket sock){
		this.ser = ser;
		this.sock = sock;
	}


	public void run(){
		try {
			OutputStream os = sock.getOutputStream(); // ������ ������(��Ʈ�� ����) -> import java.io.OutputStream;
			DataOutputStream dos = new DataOutputStream(os); // ������ ������ ��Ʈ�� ���׷��̵� -> import java.io.OutputStream;

			InputStream is = sock.getInputStream(); // ������ �ޱ� (��Ʈ������)
			DataInputStream dis = new DataInputStream(is); // ������ �ޱ� ��Ʈ�� ���׷��̵�
			
			FileInputStream fis = new FileInputStream("doorbell.mp3");
			Player player = new Player(fis);
		
			while(true){
				msg = dis.readUTF();
				if(msg.equals("orders")) {
					table = dis.readUTF();
					
					num = Integer.parseInt(table);
					ser.btn[num-1].setBackground(Color.red);
					
					input = new ObjectInputStream(dis); 
					
					HashMap<String, ArrayList<Order>> tmp = ser.getFooldList();
					if(tmp.containsKey(table)){
						ArrayList<Order> t = tmp.get(table);
						t.addAll((ArrayList<Order>)input.readObject());
						ser.arrlist(table, t);
					}else{
						ser.arrlist(table, (ArrayList<Order>)input.readObject());
						new Thread() {
					        public void run() {
					            try {
					                while(true) {
					                    player.play();
					                    System.out.println("���� ����");
					                }
					            }
					            catch (Exception e) {System.out.println(e); }
					        }
					    }.start();						
						
					}
				}
				else if(msg.equals("alert")) {
					table = dis.readUTF();
					JOptionPane.showMessageDialog(ser, table+"�� ���̺� ȣ��");
					player.play();
				}
				else 
					System.out.println(msg); // (�б�) ���
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
