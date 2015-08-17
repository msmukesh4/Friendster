package com.example.friendster;

import java.io.ByteArrayOutputStream;
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
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends Activity {
    ArrayList<NameValuePair> nameValuePairs;
    Button upload,select_image,retrive;
    ImageView image;
    EditText caption;
    TextView wait;
    Thread t;
    String image_str,response;
    Bitmap bitmap;
    ByteArrayOutputStream stream;
    private static int RESULT_LOAD_IMAGE = 1;
    
    
        @Override
    public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            setContentView(R.layout.activity_main);
    
            upload = (Button) findViewById(R.id.button1);
            select_image = (Button) findViewById(R.id.button2);
            image = (ImageView) findViewById(R.id.imageView1);
            caption = (EditText) findViewById(R.id.editText1);
            retrive = (Button) findViewById(R.id.button3);  
            wait = (TextView) findViewById(R.id.upload_text);
            wait.setVisibility(View.INVISIBLE);
 
            upload.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
						
					
					 	String name_str = caption.getText().toString();
					 	nameValuePairs = new  ArrayList<NameValuePair>();
					 	
					 	stream = new ByteArrayOutputStream();
				        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
				        image_str = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
			            nameValuePairs.add(new BasicNameValuePair("image",image_str));
			            nameValuePairs.add(new BasicNameValuePair("name", name_str));
			            
			            t = new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										wait.setVisibility(View.VISIBLE);
										caption.setText("");
									}
								});
								upload();								
							}
						});
			            t.start();
			           
				}
			});
            
            select_image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(Intent.ACTION_PICK,
	                     android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                 
	                startActivityForResult(i, RESULT_LOAD_IMAGE);
					
					
				}
			});
            
            retrive.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,Images.class );
					startActivity(intent);
				}
			});
            
                   
        }
        public void upload(){
        	
        	try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://webserver123.esy.es/Images/SaveImage.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> handler = new BasicResponseHandler();
                response = httpclient.execute(httppost,handler);	
                final long s =  response.length();
                runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (response.equalsIgnoreCase("1")) {
							Toast.makeText(MainActivity.this,"Upload Successful",Toast.LENGTH_LONG).show();
							wait.setVisibility(View.INVISIBLE);
						}
//							Toast.makeText(MainActivity.this,"Content Length"+s,Toast.LENGTH_LONG).show();
						
					}
				});
                
                
            }catch(final Exception e){
                 runOnUiThread(new Runnable() {	                             
                       @Override
                       public void run() {
                           Toast.makeText(MainActivity.this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();
                           e.printStackTrace();
                       }
                 });       
            }  
        }
        
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
             
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
     
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
     
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                
                bitmap = BitmapFactory.decodeFile(picturePath);           
                image.setImageBitmap(bitmap);
               
            }
         
         
        }
}