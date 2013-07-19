package com.example.chatDemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.socketdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
/**
 * 聊天室主界面
 * @author samsung
 *
 */
public class ChatActivity extends Activity
{
	private ListView talkView;
	private Button messageButton;
	private EditText messageText;
	private ArrayList<ChatMessage> list = new ArrayList<ChatMessage>();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		init();
	}

	private void init()
	{
		talkView = (ListView) findViewById(R.id.list);
		messageButton = (Button) findViewById(R.id.MessageButton);
		messageText = (EditText) findViewById(R.id.MessageText);
		messageButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				String date = getDate();
				String msgText = getText();
				int RIdA = R.layout.activity_chatfrom;
				ChatMessage newMsg = new ChatMessage( date, msgText, RIdA);
				
				list.add(newMsg);
				int RIdB = R.layout.activity_chatto;

				ChatMessage backMsg = new ChatMessage(date,
						"自动回复(for test!)", RIdB);
				list.add(backMsg);
				talkView
						.setAdapter(new ChattingAdapter(ChatActivity.this, list));
				messageText.setText("");
			}
		});
	}

	private String getDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		Date d = new Date();
		return sdf.format(d);
	}

	private String getText()
	{
		return messageText.getText().toString();
	}

}
