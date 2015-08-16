package com.example.friendster;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Images extends Activity implements OnItemClickListener{
	
	ListView imageList;
	Thread th;
	String response,list;
	TextView tv;
	String BaseURL = "http://webserver123.esy.es/Images/";
	int numberOfImages;
	String[] retrived_array_list;
	ArrayList<String> imageURLs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_layout);
		imageList = (ListView) findViewById(R.id.listView1);
		imageURLs = new ArrayList<String>();
		th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					
					String userlist = "http://webserver123.esy.es/Images/listOfImages.php";
					
					HttpClient client = new DefaultHttpClient();
					HttpPost post = new HttpPost(userlist);
					
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					response = client.execute(post,responseHandler);
					
					list = response;
					retrived_array_list = list.split("//");
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(),"List of images : "+list , Toast.LENGTH_LONG).show();
							for (int i = 1; i < retrived_array_list.length; i++) {
								String tmp = ""+BaseURL+retrived_array_list[i]+".png";
								imageURLs.add(tmp);
								Toast.makeText(getApplicationContext(),"Downloading "+retrived_array_list[i]+" from "+tmp , Toast.LENGTH_SHORT).show();

							}
							Toast.makeText(getApplicationContext(),"All Images Downloaded" , Toast.LENGTH_LONG).show();
							imageList.setAdapter(new myAdapter(Images.this));
							imageList.setOnItemClickListener(Images.this);
						}
					});
					
					
				}catch(Exception ee){
					ee.printStackTrace();
				}								
			}
		});
		th.start();						
		
	}
	private class myAdapter extends BaseAdapter{

		ImageView iv;
		View row;
		LayoutInflater inflater;
		public myAdapter(Context context) {
			// TODO Auto-generated constructor stub
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageURLs.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return imageURLs.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
		
		class myHolder{
			ImageView imageview;
			public myHolder(View v) {
				// TODO Auto-generated constructor stub
				imageview = (ImageView) v.findViewById(R.id.imageView1);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row = convertView;
			myHolder holder=null;
			if (row==null) {
				inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.individual_image,parent,false);
				holder = new myHolder(row);
				
				row.setTag(holder);
			}else{
				holder = (myHolder) row.getTag();
			}
			if (holder.imageview!=null) {
				new ImageDownloaderTask(holder.imageview).execute(imageURLs.get(position));
			}	
					
			return row;	
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent comment_intent = new Intent(Images.this,Images_Comment.class);
		comment_intent.putExtra("selected_image", imageURLs.get(position));
		comment_intent.putExtra("image", retrived_array_list[position+1]);

		startActivity(comment_intent);
	}
}

