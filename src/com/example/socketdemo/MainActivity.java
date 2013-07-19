package com.example.socketdemo;

import com.example.chatDemo.ChatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Socket通信之IP在java中的对象封装
 * 
 * @author samsung
 * 
 */
public class MainActivity extends Activity implements OnClickListener{
	
	private Button bt_udp,bt_tcp,bt_chat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bt_udp = (Button) findViewById(R.id.bt_udp);
		bt_tcp = (Button) findViewById(R.id.bt_tcp);
		bt_chat = (Button) findViewById(R.id.bt_chat);
		
		bt_udp.setOnClickListener(this);
		bt_tcp.setOnClickListener(this);
		bt_chat.setOnClickListener(this);

	}

	/**
	 * 添加点击事件
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.bt_udp:
			Intent udpIntent = new Intent(this,UDPActivity.class);
			startActivity(udpIntent);
			break;
		case R.id.bt_tcp:
			Intent tcpIntent = new Intent(this,TCPActivity.class);
			startActivity(tcpIntent);
			break;
		case R.id.bt_chat:
			Intent chatIntent = new Intent(this,ChatActivity.class);
			startActivity(chatIntent);
			break;
		}
	}
	
	
	
	
/*	public void ipDemo()
	{
		*//**
		 * 获取IP地址属于网络操作,要放在子线程中进行
		 *//*
		new Thread() {
			public void run() {
				try {
					// 获取本地主机
					i = InetAddress.getLocalHost();
					System.out.println(i.toString());
					System.out.println("address:" + i.getHostAddress());
					System.out.println("name:" + i.getHostName());

					// 发送消息到主线程进行UI更新
					Message msg = Message.obtain();
					msg.obj = i.toString();
					handler.sendMessage(msg);

					InetAddress i2 = InetAddress.getByName("www.baidu.com");
					System.out.println("address: " + i2.getHostAddress());
					System.out.println("name : " + i2.getHostName());

					InetAddress i3 = InetAddress.getByAddress("10.0.2.2"
							.getBytes());
					System.out.println("address: " + i3.getHostAddress());
					System.out.println("name : " + i3.getHostName());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}*/
}
