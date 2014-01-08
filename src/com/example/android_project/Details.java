package com.example.android_project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		//make 3 textviews (writer, title, content), 1 imageView(Image) parsing from ids in R codes, 
		TextView tvWriter = (TextView)findViewById(R.id.textView_writer);
		TextView tvTitle = (TextView)findViewById(R.id.textView_title);
		TextView tvContent = (TextView)findViewById(R.id.textView_content);
		ImageView ivImage = (ImageView)findViewById(R.id.imageView_photo);
		//Make fit in articleNumbers, and converting String from Intent code.
		String articleNumber = getIntent().getExtras().getString("ArticleNumber");
		
		Dao dao = new Dao(getApplicationContext());
		Article article = dao.getArticleByArticleNumber(Integer.parseInt(articleNumber));
	
		tvTitle.setText(article.getTitle());
		tvWriter.setText(article.getWriter());
		tvContent.setText(article.getContent());
		
		String img_path = getApplicationContext().getFilesDir().getPath()+"/"+article.getImgName();
		File img_load_path = new File(img_path);
		
		if(img_load_path.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			ivImage.setImageBitmap(bitmap);
		}	
	}
}
