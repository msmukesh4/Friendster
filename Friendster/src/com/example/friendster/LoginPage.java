package com.example.friendster;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends Activity {
	
	
	EditText et_username,et_password;
	String txt_username,txt_password;
	Thread auth;
	Button login,register;
	ArrayList<NameValuePair> cridentials;
	String response;
	TextView fail_text_1,fail_text_2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		et_username = (EditText) findViewById(R.id.username);
		et_password =(EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.button1);
		register = (Button) findViewById(R.id.register);
		fail_text_1 = (TextView) findViewById(R.id.fail1);
		fail_text_2 = (TextView) findViewById(R.id.fail2);
		fail_text_1.setVisibility(View.INVISIBLE);		
		fail_text_2.setVisibility(View.INVISIBLE);				

		cridentials = new ArrayList<NameValuePair>();
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				txt_username = et_username.getText().toString();
				txt_password = et_password.getText().toString();
				
				cridentials.add(new BasicNameValuePair("username", txt_username));
				cridentials.add(new BasicNameValuePair("password",txt_password));
				
				auth = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
							HttpClient client = new DefaultHttpClient();
							HttpPost post = new HttpPost("http://webserver123.esy.es/Images/login.php");
							post.setEntity(new UrlEncodedFormEntity(cridentials));
							ResponseHandler<String> Handler = new BasicResponseHandler();
							
							response =client.execute(post,Handler);
							if(response.equalsIgnoreCase("1")){
								Intent intent = new Intent(LoginPage.this, MainActivity.class);
								startActivity(intent);
							}else{
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										fail_text_1.setVisibility(View.VISIBLE);
										fail_text_2.setVisibility(View.VISIBLE);
										Toast.makeText(getApplicationContext(),""+response, Toast.LENGTH_LONG).show();
									}
								});
							}
						}catch(Exception ee){
							ee.printStackTrace();
						}																		
					}
				});
				auth.start();
			}
		});
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it = new Intent(LoginPage.this, Register.class);
				startActivity(it);
			}
		});
		
	}
}
