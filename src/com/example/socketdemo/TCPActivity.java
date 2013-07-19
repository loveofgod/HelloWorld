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
 * ����TCPЭ���SocketͨѶ
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
	 * �ͻ���
	 * 
	 * @param view
	 */
	public void tcpSend(View view) {
		new Thread() {
			public void run() {

				try {
					// 1.�����ͻ��˵�socket����
					Socket s = new Socket("127.0.0.1", 10001);
					// 2.��ȡsocket���е������
					OutputStream out = s.getOutputStream();
					// 3.д���ݵ������
					out.write("tcp connection is success!".getBytes());
					s.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * �����
	 * 
	 * @param view
	 */
	public void tcpReceive(View view) {
		new Thread() {
			public void run() {
				try {
					// 1.��������˵�socket����,������һ���˿�
					ServerSocket ss = new ServerSocket(10001);
					// 2.��ȡ�ͻ��˶���
					Socket s = ss.accept();
					// ��ȡ�ͻ��˵�IP
					String ip = s.getInetAddress().getHostAddress();
					// 3.���������ж�ȡ����---��ȡ�ͻ��˷��͹��������ݣ���ôҪʹ�ÿͻ��˶���Ķ�ȡ������ȡ����
					InputStream in = s.getInputStream();

					byte[] buf = new byte[1024];
					int len = in.read(buf);
					String data = new String(buf, 0, len);

					Message msg = Message.obtain();
					msg.obj = data;
					handler.sendMessage(msg);

					// 4.�ر�socket��
					s.close();
					ss.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
	}
}