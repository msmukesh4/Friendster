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

public class Register extends Activity {

	EditText et_register_pass,et_register_user,et_register_email;
	Button reg;
	ArrayList<NameValuePair> registeration_form;
	String response;
	Thread t;
	TextView hide3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		
		et_register_email = (EditText) findViewById(R.id.email);
		et_register_user = (EditText) findViewById(R.id.name);
		et_register_pass = (EditText) findViewById(R.id.pass);
		reg = (Button) findViewById(R.id.btn_register_form);
		hide3 = (TextView) findViewById(R.id.hide3);
		hide3.setVisibility(View.INVISIBLE);
		registeration_form = new ArrayList<NameValuePair>();
		
		reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				registeration_form.add(new BasicNameValuePair("reg_name", et_register_user.getText().toString()));
				registeration_form.add(new BasicNameValuePair("reg_pass", et_register_pass.getText().toString()));
				registeration_form.add(new BasicNameValuePair("reg_email", et_register_email.getText().toString()));
				
				t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try{
								final String em = et_register_email.getText().toString(); 
								final String us = et_register_user.getText().toString();
								final String pa = et_register_pass.getText().toString();
								if(!em.equalsIgnoreCase("")	&& !us.equalsIgnoreCase("")	&&
									!pa.equalsIgnoreCase("")){									
									// TODO Auto-generated method stub
									HttpClient client = new DefaultHttpClient();
									HttpPost post = new HttpPost("http://webserver123.esy.es/Images/register.php");
									post.setEntity(new UrlEncodedFormEntity(registeration_form));
									ResponseHandler<String> responseHandler = new BasicResponseHandler();
									response = client.execute(post,responseHandler);
									
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											if (response.equalsIgnoreCase("1")) {
												Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG).show();
												Intent it  = new Intent(Register.this, LoginPage.class);
												startActivity(it);
											}else{
												Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_LONG).show();
												hide3.setText(response);
												hide3.setVisibility(View.VISIBLE);
											}
							
										}
									});
								}else{
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											Toast.makeText(Register.this,"All Fields are Mandatory !! " ,Toast.LENGTH_LONG ).show();
											hide3.setText("All Fields are Mandatory!!");
											hide3.setVisibility(View.VISIBLE);
										}
									});
								}
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				});
				t.start();
				
			}
		});
		

		
	}
}
