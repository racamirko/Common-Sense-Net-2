package ch.weather;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

/*
 * Class to manage main interface
 */
public class simpleWeather extends Activity {
	
	private GestureDetector mGestureDetector;
	private RotateAnimation rotateAnim;

    /** Called when the activity is first created. */
	@Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Create gesture detector scanner
        mGestureDetector = new GestureDetector(this, new myGestureListener());
        
        // Create Pie Chart
        List<PieItem> PieData = new ArrayList<PieItem>(0);
    	PieItem Item;
    	int MaxPieItems = 4;
    	int MaxCount = 0;
    	for (int i = 0; i < MaxPieItems ; i++) {
    	    Item       = new PieItem();
    	    Item.Count = i+1;
    	    Item.Label = "Valeur " + i;
    	    Item.Color = 0xff000000 + 800000*(i+1);
    	    PieData.add(Item);
    	    MaxCount += i+1;
    	}
    	
        // Generate Pie
    	int Size = 300;
        int BgColor = 0xFFFFFF;
        Bitmap mBackgroundImage = Bitmap.createBitmap(Size, Size, Bitmap.Config.RGB_565);
    	PieChart myPie = new PieChart(this);
        myPie.setLayoutParams(new LayoutParams(Size, Size));
        myPie.setGeometry(Size, Size, 5, 5, 5, 5, 0);
        myPie.setSkinParams(BgColor);
        myPie.setData(PieData, MaxCount);
        myPie.invalidate();
        
        // draw Pie on Canvas with specified bitmap
        myPie.draw(new Canvas(mBackgroundImage));
        myPie = null;
        
	    // Add created PieChart ImageView to ImageView interface
        ImageView image = (ImageView) findViewById(R.id.pieGraph);
        image.setImageBitmap(mBackgroundImage);
    }
	
	@Override public boolean onTouchEvent(MotionEvent ev) {
     
		if (mGestureDetector.onTouchEvent(ev))
			return true;
		else
			return false;
	}

    
    /*
     * Class to listen to gestures
     * @author: Julien Freudiger
     */
    public class myGestureListener extends GestureDetector.SimpleOnGestureListener{
    	private static final int SWIPE_MIN_DISTANCE = 120;
   	  	private static final int SWIPE_MAX_OFF_PATH = 250;
   	  	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    
    	/*
    	@Override public boolean onDown(MotionEvent ev){
    		Toast t = Toast.makeText(getApplicationContext(), "Down", Toast.LENGTH_SHORT);
    		t.show();
    		return true;
    	}
    	
    	@Override public boolean onSingleTapUp(MotionEvent ev) {
    		Toast t = Toast.makeText(getApplicationContext(), "SingleTapUp", Toast.LENGTH_SHORT);
    		t.show();
    		return true;
    	}
    	
    	
    	@Override public void onShowPress(MotionEvent ev) {
    		Toast t = Toast.makeText(getApplicationContext(), "ShowPress", Toast.LENGTH_SHORT);
    		t.show();
    	}
    	
    	@Override public void onLongPress(MotionEvent ev) {
    		Toast t = Toast.makeText(getApplicationContext(), "LongPress", Toast.LENGTH_SHORT);
    		t.show();
    	}
    	
    	@Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
    		Toast t = Toast.makeText(getApplicationContext(), "Scroll", Toast.LENGTH_SHORT);
    		t.show();
    	    return true;
    	}
    	*/
    	
    	@Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    		
    		
    		//RelativeLayout myLayout2 = (RelativeLayout) findViewById(R.id.pieGraph);
    		if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) // not a straight fling
    		    return false;
               
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // left swipe
            	Toast.makeText(getApplicationContext(), "LeftSwipe", Toast.LENGTH_SHORT).show();
            	rotate(0);
            }  
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	// right swipe
            	Toast.makeText(getApplicationContext(), "RightSwipe", Toast.LENGTH_SHORT).show();
            	rotate(1);
            }

            // top to bottom swipe
            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                // down to top swipe
            	Toast.makeText(getApplicationContext(), "DownTopSwipe", Toast.LENGTH_SHORT).show();
            	rotate(0);
            }  
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            	// top to down swipe
            	Toast.makeText(getApplicationContext(), "TopDownSwipe", Toast.LENGTH_SHORT).show();
            	rotate(1);
            }

	    	return false;
    	    
    	}
    	
    	/*
    	 * Method to define and apply animation 
    	 * @author: Julien Freudiger
    	 */
    	public void rotate(int direction){                      
    		 
    		ImageView myImage = (ImageView) findViewById(R.id.pieGraph);

    		// Define animation
			float from=0;
			float to = 260;
 			rotateAnim = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim.setInterpolator(new LinearInterpolator());
			rotateAnim.setDuration(2000);
			
			rotateAnim.setAnimationListener(new AnimationListener(){
				private boolean doEnd = true;
    			
    	        public void onAnimationEnd(Animation animation) {
    	            float angle = 260;
    	        	if (doEnd){
    	                doEnd = false;
    	                 
    	                ImageView myImage = (ImageView) findViewById(R.id.pieGraph);
    	        		Matrix mat = myImage.getImageMatrix();
    	        	    mat.postRotate(angle, myImage.getWidth()/2, myImage.getHeight()/2);
    	        	    myImage.setImageMatrix(mat);
    	        	    myImage.invalidate();
    	        		
    	            }
    	        }

    	        public void onAnimationRepeat(Animation animation) {
    	        }

    	        public void onAnimationStart(Animation animation) {
    	        }

    	    });
    
			// Apply animation
			myImage.startAnimation(rotateAnim);
    		
    		
    	}	
    }
}