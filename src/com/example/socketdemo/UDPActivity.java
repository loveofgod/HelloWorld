package com.example.socketdemo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
/**
 * UDP协议通讯方式演示
 * @author samsung
 *
 */
public class UDPActivity extends Activity {
	protected static final int RECEIVE_DATA = 1;
	TextView tv_text;
	private String text;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch(msg.what)
			{
			case RECEIVE_DATA:
				tv_text.setText(text);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upd);
		tv_text = (TextView) findViewById(R.id.tv_text);
	}
	/**
	 * 发送端
	 * 
	 * @param view
	 */
	public void send(View view) {
		new Thread() {
			public void run() {
				try {
					// 1.创建udp服务,并指定发送数据的端口
					DatagramSocket ds = new DatagramSocket(8888);
					// 2.将要发送的数据封包
					byte[] data = "i love you baby!".getBytes();
					DatagramPacket dp = new DatagramPacket(data, data.length,
							InetAddress.getByName("127.0.0.1"), 10000);
					// 3.发送数据
					ds.send(dp);
					// 4.关闭资源
					ds.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 接收端
	 * 
	 * @param view
	 */
	public void receive(View view) {
		new Thread() {

		

			public void run() {
				try {
					// 1.创建UDP协议,并指定接收端口
					DatagramSocket ds = new DatagramSocket(10000);
					// 2.定义数据包,用于接收数据
					byte[] buf = new byte[1024];
					DatagramPacket dp = new DatagramPacket(buf, buf.length);
					//3.接收数据
					ds.receive(dp);//阻塞式方法
					//4.取出数据
					String ip = dp.getAddress().getHostAddress();
					int port = dp.getPort();
					String data = new String(dp.getData(),0,dp.getLength());
					text = ip+" :: "+port+" :: "+ data;
					Message msg = Message.obtain();
					msg.what = RECEIVE_DATA;
					handler.sendMessage(msg);
					
					ds.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();
	}
	
}
