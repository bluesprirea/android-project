package com.example.android_project;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		//make textview works, by connecting with 'findviewbyid'
		TextView tvWriter = (TextView)findViewById(R.id.textView_writer);
		TextView tvTitle = (TextView)findViewById(R.id.textView_title);
		TextView tvContent = (TextView)findViewById(R.id.textView_content);
		ImageView ivImage = (ImageView)findViewById(R.id.imageView_photo);
		
		String articleNumber = getIntent().getExtras().getString("ArticleNumber");
	
		Dao dao = new Dao(getApplicationContext());
		Article article = dao.getArticleByArticleNumber(Integer.parseInt(articleNumber));
	
		tvTitle.setText(article.getTitle());
		tvWriter.setText(article.getWriter());
		tvContent.setText(article.getContent());
		
		try{
			//이미지 파일 이름 가지고 오기
			InputStream ims = getApplicationContext().getAssets().open(article.getImgName());
			//이미지로 불러와 drawable로 컨버팅
			Drawable d = Drawable.createFromStream(ims, null);
			//이미지 보여주기
			ivImage.setImageDrawable(d);
		}catch (IOException e){
			Log.e("Error", e.getMessage());
		}
		
	}

	

}
