package com.example.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.*;


//DeepLearning Server와 통신을 위한 class
public class CppServer {
	
	public DataInputStream dis;
	public DataOutputStream dos;
	private ServerSocket server;
	private TcpServer tcpServer;
	private Connection conn;
	private PreparedStatement pstmt;

	//Android와 통신하는 클래스를 제어하기 위해 매개변수로 받음
	public CppServer(TcpServer tcpServer) {
		this.tcpServer = tcpServer;
	
	try {
		//port 8086으로 서버소켓을 연다
		server = new ServerSocket(8086);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Initialize complate");

	//DeepLearning Server와 소켓 연결을 위한 스레드
	Thread t = new Thread() {
		public void run() {
			while (true) {
				try {
					Socket client = server.accept();
					System.out.println("cppServer client come ok");
					dos = new DataOutputStream(client.getOutputStream());
					dis = new DataInputStream(client.getInputStream());
					new pushThread(dis, tcpServer);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("cppServer Connection");
			}
		}
	};

	t.start();
}
	
	//DeepLearning Server에서 감지된 알림을 받아 Android에게 Push알림 보냄
	class pushThread extends Thread{
		
		DataInputStream dis = null;
		TcpServer tcpServer = null;
		byte [] b1 = new byte[4];
		byte [] b2 = new byte[20];
		//생성자에서 Thread 실행
		public pushThread(DataInputStream dis, TcpServer tcpServer) {
			this.dis = dis;
			this.tcpServer = tcpServer;					
			this.start();
			System.out.println("pushThread create ok!");
		}
		
		public void run() {
			while(true) {
				try {
					//감지된 알림을 받음
					dis.read(b1, 0, 4);
					String data = new String(b1);
					System.out.println("Read Line Data = " + data);
					String filename;
					//DeepLearning Server에서 push라는 알림을 받으면
					if(data.equals("push")) {
						System.out.println("call push at cpp file");
					dis.read(b2);
					filename = new String(b2); 
						//DataBase에 삽입
						conn = null;
						pstmt = null;
						String jdbc_driver = "com.mysql.jdbc.Driver";
						String jdbc_url = "jdbc:mysql://localhost:3306/video"; 
						try {
							Class.forName(jdbc_driver);
							conn = DriverManager.getConnection(jdbc_url,"root","hansung");
						} catch (Exception e) {
							e.printStackTrace();
						}
						String sql ="insert into list(date) values(?)";
						try {
							pstmt =  conn.prepareStatement(sql);
							pstmt.setString(1,filename);
							pstmt.executeUpdate();
							System.out.println("Mysql insert OK");
						} catch (SQLException e) {
							e.printStackTrace();
						}
						finally {
							disconnect();
						}
						//tcpServer class에서 Android에게 Push알림 보냄
						tcpServer.push();
						System.out.println("cppServer -> tcpServer.push() OK");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

