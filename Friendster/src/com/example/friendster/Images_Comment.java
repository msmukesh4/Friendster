package com.example.friendster;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Images_Comment extends Activity {

	ImageView iv;
	ListView comments;
	EditText et_comment;
	Thread thread,thread_list;
	ArrayList<NameValuePair> namevaluepair, query;
	Button send;
	String response,response_comment;
	String[] comment_array;
	int sent=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comments_layout);
		iv = (ImageView) findViewById(R.id.image);
		comments = (ListView) findViewById(R.id.comment_list);
		et_comment = (EditText) findViewById(R.id.comment);
		namevaluepair = new ArrayList<NameValuePair>();
		query = new ArrayList<NameValuePair>();
		send = (Button) findViewById(R.id.send);
		
		new ImageDownloaderTask(iv).execute(getIntent().getExtras().getString("selected_image"));
		
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						sent=2;
						namevaluepair.add(new BasicNameValuePair("comment",et_comment.getText().toString()));
						namevaluepair.add(new BasicNameValuePair("image",getIntent().getExtras().getString("image")));

						try{
				                HttpClient client = new DefaultHttpClient();
				                HttpPost post = new HttpPost("http://webserver123.esy.es/Images/insertComment.php");
				                post.setEntity(new UrlEncodedFormEntity(namevaluepair));

				                ResponseHandler<String> responseHandler = new BasicResponseHandler();
								response = client.execute(post,responseHandler);
								
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_LONG).show();
										et_comment.setText("");
									}
								});
				              
			                
			            }catch(final Exception e){
			                 e.printStackTrace();
			            }  
					
					}
				});
				
				thread.start();
			}
		});
				
		thread_list = new Thread(new Runnable() {
					
			@Override
			public void run() {
				while(true){
					// TODO Auto-generated method stub
					if(sent>0){
						try{				
								query.add(new BasicNameValuePair("img_name", getIntent().getExtras().getString("image")));
								String userlist = "http://webserver123.esy.es/Images/retriveComments.php";							
								HttpClient client = new DefaultHttpClient();
								HttpPost post = new HttpPost(userlist);
								post.setEntity(new UrlEncodedFormEntity(query));
										
								ResponseHandler<String> responseHandler = new BasicResponseHandler();
								response_comment = client.execute(post,responseHandler);
										
								comment_array = response_comment.split("//");							
								runOnUiThread(new Runnable() {
											
									@Override
									public void run() {
										// TODO Auto-generated method stub
										comments.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
												android.R.layout.simple_list_item_1, comment_array));
										
									}
								});
								Thread.sleep(10000);		
										
										
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						sent--;;
					}
				}
			}
		});
		thread_list.start();
	}
}
