package com.swingwsocket;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.text.*;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.validation.Validator;

public class Client implements ActionListener{

	static JTextField msg;
	static JPanel a1;
	static Box verticle= Box.createVerticalBox();
	static JFrame f = new JFrame();
	static DataOutputStream dos;
	
	Client(){
		f.setUndecorated(true);
		f.getContentPane().setLayout(null);
		
		
		
		JPanel p1 = new JPanel();
		p1.setForeground(Color.WHITE);
		p1.setBorder(null);
		p1.setBackground(new Color(7, 94, 70));
		p1.setBounds(0, 0, 350, 48);
		p1.setLayout(null);
		f.getContentPane().add(p1);
		f.setVisible(true);
		
		ImageIcon i1=new ImageIcon(this.getClass().getResource("/3.png"));
		Image i2=i1.getImage().getScaledInstance(23, 22, Image.SCALE_DEFAULT);
		ImageIcon i3=new ImageIcon(i2);
		JLabel back=new JLabel(i3);
		back.setBounds(4, 0, 22, 45);
		p1.add(back);
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				f.setVisible(false);
			}
		});
		
		ImageIcon i4=new ImageIcon(this.getClass().getResource("/mirzapur.png"));
		Image i5=i4.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i6=new ImageIcon(i5);
		JLabel profile=new JLabel(i6);
		profile.setBounds(30, 0, 50, 45);
		p1.add(profile);
		
		ImageIcon i7=new ImageIcon(this.getClass().getResource("/video.png"));
		Image i8=i7.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i9=new ImageIcon(i8);
		JLabel video=new JLabel(i9);
		video.setBounds(240, 0, 20, 45);
		p1.add(video);
		
		ImageIcon i10=new ImageIcon(this.getClass().getResource("/phone.png"));
		Image i11=i10.getImage().getScaledInstance(27, 25, Image.SCALE_DEFAULT);
		ImageIcon i12=new ImageIcon(i11);
		JLabel phone=new JLabel(i12);
		phone.setBounds(280, 0, 30, 45);
		p1.add(phone);
		
		ImageIcon i13=new ImageIcon(this.getClass().getResource("/3icon (1).png"));
		Image i14=i13.getImage().getScaledInstance(11, 18, Image.SCALE_DEFAULT);
		ImageIcon i15=new ImageIcon(i14);
		JLabel moreover=new JLabel(i15);
		moreover.setBounds(320, 0, 30, 45);
		p1.add(moreover);
		
		JLabel name = new JLabel("Ravi");
		name.setFont(new Font("Sens-Serif", Font.BOLD | Font.ITALIC, 19));
		name.setForeground(new Color(255, 255, 255));
		name.setBounds(83, 0, 64, 34);
		p1.add(name);
		
		JLabel status = new JLabel("Active Now");
		status.setFont(new Font("Sens-Serif", Font.ITALIC, 11));
		status.setForeground(new Color(255, 255, 255));
		status.setBounds(83, 25, 64, 21);
		p1.add(status);
		
		a1 = new JPanel();
		a1.setBounds(5, 48, 340, 465);
		f.getContentPane().add(a1);

		msg=new JTextField();
		msg.setBounds(2, 520, 245, 30);
		msg.setFont(new Font("Sens-Serif", Font.PLAIN, 14));
		f.getContentPane().add(msg);
		
		JButton send=new JButton("Send");
		send.setBackground(new Color(7, 94, 64));
		send.setFont(new Font("SansSerif", Font.PLAIN, 16));
		send.setForeground(new Color(255, 255, 255));
		send.setBounds(257, 520, 93, 30);
		send.addActionListener(this);
		f.getContentPane().add(send);
		
		f.setSize(330, 550);
		f.setLocation(600, 100);
		f.getContentPane().setBackground(Color.WHITE);


		
		f.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		String textout=msg.getText();
		
		JPanel a2=formatLabel(textout);
		
		
		a1.setLayout(new BorderLayout());
		
		JPanel right=new JPanel(new BorderLayout());
		right.add(a2, BorderLayout.LINE_END);                      //msg align to the right side;
		verticle.add(right);										//msg settle into next line automatically
		verticle.add(Box.createVerticalStrut(15));
		
		a1.add(verticle, BorderLayout.PAGE_START);
		
		dos.writeUTF(textout);
		
		msg.setText("");
	
		f.repaint();
		f.invalidate();
		f.validate();
	}
	
	catch(Exception ex) {
		System.out.println(ex);
	
	}
	}
	public static JPanel formatLabel(String textout) {
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output=new JLabel("<html><p style=\"width: 150px\">" + textout +" </p></html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 18));
		output.setForeground(Color.white);
		output.setBackground(new Color(37, 211, 102));
		output.setBorder(new EmptyBorder(10, 10, 10, 40));  //for padding;
		output.setOpaque(true);
		
		panel.add(output);
		
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel time=new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		
		
		return panel;
	}






	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Client();
		
		try {
			
			Socket s =new Socket("127.0.0.1", 6001);      //1. LOCALHOST, 2. PORT NO OF SERVER SOCKET
			
			DataInputStream din = new  DataInputStream(s.getInputStream());       //for msg receive
			dos = new DataOutputStream(s.getOutputStream());     //for  msg send
			
			while(true) {     
				//used protocol for msg receiving and for read
				
				a1.setLayout(new BorderLayout());
				String out = din.readUTF();
				JPanel panel = formatLabel(out);
				
				JPanel left = new JPanel(new BorderLayout());    //received msg showed on the left side
				left.add(panel, BorderLayout.LINE_START);
				verticle.add(left);
				
				verticle.add(Box.createVerticalStrut(15));  //store the msgs which is received 
				a1.add(verticle, BorderLayout.PAGE_START);
				
				
				f.validate();
				
			}
			
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}

}
