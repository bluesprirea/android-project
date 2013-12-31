package com.example.android_project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class Proxy {
	public String getJSON(){
		//try&catch 사용생활
		try{
			URL url = new URL("http://10.73.44.93/~stu07/loadData.php");
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
			conn.setRequestProperty("Accept", "application/json");
			//inputStream으로 서버응답을 받겠다는 것. 
			conn.setDoInput(true);
			
			conn.connect();
			
			int status = conn.getResponseCode();
			Log.i("test", "ProxyResponseCode:" +status);
			
			switch(status) {
			case 200:
			case 201:
				//status that connected well
				BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while( (line = br.readLine()) != null){
					sb.append(line + "\n");
				}
				br.close();
				
				return sb.toString();
			}
		}catch (Exception e){
			e.printStackTrace();
			Log.i("test", "Network error"+e);
		}
		return null;
	}
	
}
