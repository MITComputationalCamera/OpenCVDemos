package com.example.opencvimgprocess;

import java.io.IOException;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private static final String  TAG = "OpenCV_img_blur_ex";
	
	private Mat imgOri;
	private Mat imgPross;
	
	private Bitmap bitmapImg;
	
	private ImageView view;
	
	
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");

                    try {
                    	
                    	//CODE: here the image is loaded, processed and presented
                    	view = (ImageView) findViewById(R.id.imageView1);
                    	
                    	imgOri = Utils.loadResource(this.mAppContext, R.drawable.lenna);
                    	imgPross = imgOri.clone();
                    	
                    	bitmapImg = Bitmap.createBitmap(imgPross.width(), imgPross.height(), Config.ARGB_8888);
                    	
                    	Imgproc.blur(imgOri, imgPross, new Size(30,30));
                    
                    		
                    	Imgproc.cvtColor(imgPross, imgPross, Imgproc.COLOR_BGR2RGBA);
                    	
                    	Utils.matToBitmap(imgPross, bitmapImg);
                    	
                    	view.setImageBitmap(bitmapImg);                   	
                    	
                    	
					} catch (IOException e) {
						e.printStackTrace();
					}
                    
                    
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	
	public void onPause()
    {
        super.onPause();
    }
	
	
	public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

}
