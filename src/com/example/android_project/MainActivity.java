package com.example.android_project;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener{

	
//	private Button mLoginButton;
//	private Button mUploadButton;
//	private Button mListButton;
//	private Button mLogoutButton;
	private ArrayList<Article> articleList;
	private ListView mainListView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button BWrite = (Button)findViewById(R.id.button_write);
		Button BRefresh = (Button)findViewById(R.id.button_refresh);
		
		BWrite.setOnClickListener(this);
		BRefresh.setOnClickListener(this);
		
//		mLoginButton = (Button)findViewById(R.id.main_LoginButton);
//		mUploadButton = (Button)findViewById(R.id.main_UploadButton);
//		mListButton = (Button)findViewById(R.id.main_ListButton);
//		mLogoutButton = (Button)findViewById(R.id.main_LogoutButton);
//		
//		mLoginButton.setOnClickListener(this);
//		mUploadButton.setOnClickListener(this);
//		mListButton.setOnClickListener(this);
//		mLogoutButton.setOnClickListener(this);
		
		mainListView1 = (ListView)findViewById(R.id.CustomList_ListView);
		
		Dao dao = new Dao(getApplicationContext());
		
		String testJsonData = dao.getJsonTestData();
		
		dao.insertJsonData(testJsonData);
		
		articleList = dao.getArticleList();
		CustomAdapter customAdapter = new CustomAdapter(this, R.layout.activity_list, articleList);
		mainListView1.setAdapter(customAdapter);
		mainListView1.setOnItemClickListener(this);
	}


	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.button_write:
			Intent intentLoginPage = new Intent(this, Login.class);
			startActivity(intentLoginPage);
			break;
		case R.id.main_UploadButton:
			
			break;
		case R.id.main_ListButton:
			break;
		case R.id.button_refresh:
			Intent intentLogoutPage = new Intent(this, Logout.class);
			startActivity(intentLogoutPage);
			break;
		}
		
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		Intent intent = new Intent(this,Details.class);
		
		intent.putExtra("ArticleNumber", articleList.get(position).getArticleNumber() + "");
		startActivity(intent);
	}

}
