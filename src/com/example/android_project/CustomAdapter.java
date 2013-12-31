package com.example.android_project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Article>{
	//Make fit file based on article, 
	private Context context;
	private int layoutResourceId;
	private ArrayList<Article> ListData;
	
	public CustomAdapter(Context context, int layoutResourceId, ArrayList<Article> ListData){
		super(context, layoutResourceId, ListData);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.ListData = ListData;
	}
	
	@Override
	public View getView(int position, View ConvertView, ViewGroup parent){
		View row = ConvertView;
		
		if(row == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
		}
		//row.findViewById로 row안의 레이아웃을 설정하는
		ImageView imageView = (ImageView) row.findViewById(R.id.customRow_image1);
		TextView tvTitle = (TextView) row.findViewById(R.id.customRow_text1);
		TextView tvContent = (TextView) row.findViewById(R.id.customRow_text2);
		//volley라는 라이브러리를 사용하면 화면을 전환해도 이미지나 버튼을 다시 가져오는 것이 아니라 알아서 캐시에 적용됨.
		
		tvTitle.setText(ListData.get(position).getTitle());
		tvContent.setText(ListData.get(position).getContent());
		
		String img_path = context.getFilesDir().getPath()+"/"+ListData.get(position).getImgName();
		File img_load_path = new File(img_path);
		
		if(img_load_path.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			imageView.setImageBitmap(bitmap);
		}
		
		return row;
	}

}
