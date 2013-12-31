package com.example.android_project;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.util.Log;

public class FileDownloader {
	private final Context context;
	
	public FileDownloader(Context context){
		this.context = context;
	}
	
	public void downFile(String fileUrl, String fileName){
		File filePath = new File(context.getFilesDir().getPath() + "/" + fileName);
		
		if(!filePath.exists()){
			try{
				URL url = new URL(fileUrl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				//서버 접속에 대한 시간제
				conn.setConnectTimeout(10 * 1000);
				//파일을 읽어오는 것에 대한 시간제한
				conn.setReadTimeout(10*1000);
				//요청 방식은 post, get중에 get을 선
				conn.setRequestMethod("GET");
				//연결을 지속하도록 함. 
				conn.setRequestProperty("Conncetion","Keep-Alive");
				//utf-8로 charset을 설
				conn.setRequestProperty("Accept-Charset", "UTF-8");
				//캐시된 데이터를 사용하지 않고 매번 서버로부터 다시 받
				conn.setRequestProperty("Cache-Control", "no-cache");
				//서버로부터 json형식의 타입으로 데이터 요청
				conn.setRequestProperty("Accept", "*/*");
				//inputStream으로 서버응답을 받겠다는 것. 
				conn.setDoInput(true);
				
				conn.connect();
				
				int status = conn.getResponseCode();
				
				
				switch(status) {
				case 200:
				case 201:
					InputStream is = conn.getInputStream();
					
					BufferedInputStream bis = new BufferedInputStream(is);
					ByteArrayBuffer baf = new ByteArrayBuffer(50);
					
					int current = 0;
					
					while ((current = bis.read()) != -1){
						baf.append((byte) current);
					}
					
					FileOutputStream fos = context.openFileOutput(fileName, 0);
					fos.write(baf.toByteArray());
					fos.close();
					
					bis.close();
					is.close();
			}
		}catch (IOException e){
			Log.i("test", "download error :"+e);
		}
}
	}
}
