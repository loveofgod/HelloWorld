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
 * UDPЭ��ͨѶ��ʽ��ʾ
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
	 * ���Ͷ�
	 * 
	 * @param view
	 */
	public void send(View view) {
		new Thread() {
			public void run() {
				try {
					// 1.����udp����,��ָ���������ݵĶ˿�
					DatagramSocket ds = new DatagramSocket(8888);
					// 2.��Ҫ���͵����ݷ��
					byte[] data = "i love you baby!".getBytes();
					DatagramPacket dp = new DatagramPacket(data, data.length,
							InetAddress.getByName("127.0.0.1"), 10000);
					// 3.��������
					ds.send(dp);
					// 4.�ر���Դ
					ds.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * ���ն�
	 * 
	 * @param view
	 */
	public void receive(View view) {
		new Thread() {

		

			public void run() {
				try {
					// 1.����UDPЭ��,��ָ�����ն˿�
					DatagramSocket ds = new DatagramSocket(10000);
					// 2.�������ݰ�,���ڽ�������
					byte[] buf = new byte[1024];
					DatagramPacket dp = new DatagramPacket(buf, buf.length);
					//3.��������
					ds.receive(dp);//����ʽ����
					//4.ȡ������
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
