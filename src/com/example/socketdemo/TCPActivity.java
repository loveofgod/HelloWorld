package com.example.socketdemo;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

/**
 * 基于TCP协议的Socket通讯
 * 
 * @author samsung
 * 
 */
public class TCPActivity extends Activity {
	private TextView tv_tcpText;
	// private String data;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String text = (String) msg.obj;
			tv_tcpText.setText(text);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tcp);
		tv_tcpText = (TextView) findViewById(R.id.tv_tcpText);

	}

	/**
	 * 客户端
	 * 
	 * @param view
	 */
	public void tcpSend(View view) {
		new Thread() {
			public void run() {

				try {
					// 1.创建客户端的socket服务
					Socket s = new Socket("127.0.0.1", 10001);
					// 2.获取socket流中的输出流
					OutputStream out = s.getOutputStream();
					// 3.写数据到服务端
					out.write("tcp connection is success!".getBytes());
					s.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 服务端
	 * 
	 * @param view
	 */
	public void tcpReceive(View view) {
		new Thread() {
			public void run() {
				try {
					// 1.创建服务端的socket服务,并监听一个端口
					ServerSocket ss = new ServerSocket(10001);
					// 2.获取客户端对象
					Socket s = ss.accept();
					// 获取客户端的IP
					String ip = s.getInetAddress().getHostAddress();
					// 3.从网络流中读取数据---获取客户端发送过来的数据，那么要使用客户端对象的读取流来读取数据
					InputStream in = s.getInputStream();

					byte[] buf = new byte[1024];
					int len = in.read(buf);
					String data = new String(buf, 0, len);

					Message msg = Message.obtain();
					msg.obj = data;
					handler.sendMessage(msg);

					// 4.关闭socket流
					s.close();
					ss.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
	}
}