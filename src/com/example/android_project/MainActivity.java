package com.example.android_project;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.devspark.sidenavigation.ISideNavigationCallback; 
import com.devspark.sidenavigation.SideNavigationView; 
import com.devspark.sidenavigation.SideNavigationView.Mode;


public class MainActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	// private Button mLoginButton;
	// private Button mUploadButton;
	// private Button mListButton;
	// private Button mLogoutButton;
	private SideNavigationView sideNavigationView;
	private ArrayList<Article> articleList;
	private ListView mainListView1;

	// 핸들러라는 것은 스레드가 메인 유아이에 바로 들어갈 수 없기 때문에 상요하는 메소드.
	// 안드로이드에서는 메인스레드와 서브스레드간의 통신을 위해 핸들러를 사용하는데 이는 메시지큐를 통한 메시지 전달방법을 사용합니다.
	// 핸들러에 메시지가 들어오면 순서대로 쌓여 fifo형식으로 갖다주게 됩니다. 가장 처음 들어온 메시지를 처음 처리하게 되는 거져.
	private final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.permitNetwork().build());

		Button BWrite = (Button) findViewById(R.id.button_write);
		Button BRefresh = (Button) findViewById(R.id.button_refresh);

		BWrite.setOnClickListener(this);
		BRefresh.setOnClickListener(this);

		// mLoginButton = (Button)findViewById(R.id.main_LoginButton);
		// mUploadButton = (Button)findViewById(R.id.main_UploadButton);
		// mListButton = (Button)findViewById(R.id.main_ListButton);
		// mLogoutButton = (Button)findViewById(R.id.main_LogoutButton);
		//
		// mLoginButton.setOnClickListener(this);
		// mUploadButton.setOnClickListener(this);
		// mListButton.setOnClickListener(this);
		// mLogoutButton.setOnClickListener(this);

		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		sideNavigationView.setMenuItems(R.menu.side_menu);
		sideNavigationView.setMenuClickCallback(sideNavigationCallback);
		sideNavigationView.setMode(Mode.LEFT);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mainListView1 = (ListView) findViewById(R.id.CustomList_ListView);
		refreshData();
		listView();
		
	}

	private void listView() {
		// db로부터 게시글 리스트를 받아옴.
		Dao dao = new Dao(getApplicationContext());
		articleList = dao.getArticleList();
		// customadapter를 적용함.
		CustomAdapter customAdapter = new CustomAdapter(this, R.layout.activity_list, articleList);
		mainListView1.setAdapter(customAdapter);
		mainListView1.setOnItemClickListener(this);
	}

	private void refreshData() {
		// 서버로부터 json 데이터를 가져옴.
		Proxy proxy = new Proxy();
		String jsonData = proxy.getJSON();
		Log.d("RESDATA", jsonData);

		// 디비에 json데이터를 저장함.
		Dao dao = new Dao(getApplicationContext());
		dao.insertJsonData(jsonData);

		// listview함수 적용시키기
		handler.post(new Runnable() {
			public void run() {
				listView();
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button_write:
			Intent intent = new Intent(this, ArticleWriter.class);
			startActivity(intent);
			break;
		case R.id.button_refresh:
			refreshData();
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Intent intent = new Intent(this, Details.class);

		intent.putExtra("ArticleNumber", articleList.get(position)
				.getArticleNumber() + "");
		startActivity(intent);
	}

	@Override
	public void onResume(){
		super.onResume();
		refreshData();
		listView();
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		String text = "";
		switch(item.getItemId()){
		case R.id.action_item1:
			text = "Action item, with text, displayed if room exists";
			break;
		case R.id.action_item2:
			text = "Action item, icon only, always displayed";
			break;
		case R.id.action_item3:
			text = "Normal menu item";
			break;
		case android.R.id.home:
			Log.i("test","home touched!!");
			sideNavigationView.toggleMenu();
			break;
		default:
			return super.onOptionsItemSelected(item);
			
		}
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		return true;
	}

	ISideNavigationCallback sideNavigationCallback = new ISideNavigationCallback() {
		
		@Override
		public void onSideNavigationItemClick(int itemId) {

			String text= "";
			switch(itemId){
			case R.id.side_navigation_menu_add:
				text = "add";
				break;
			case R.id.side_navigation_menu_call:
				text = "call";
				break;
			case R.id.side_navigation_menu_camera:
				text = "camera";
				break;
			case R.id.side_navigation_menu_delete:
				text = "delete";
				break;
			case R.id.side_navigation_menu_text:
				text = "text";
				break;
			default:
				text = "";
			}
			Toast.makeText(getApplicationContext(), "side menu:"+text, Toast.LENGTH_SHORT).show();
		}
		
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
