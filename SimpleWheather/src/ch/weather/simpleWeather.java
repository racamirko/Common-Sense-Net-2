package ch.weather;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/*
 * Class to manage main interface
 */
public class simpleWeather extends Activity {
	
	private GestureDetector mGestureDetector;
	Animation hyperspaceJump ;

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
        
        /*
        // Create ImageView of Canvas
        ImageView mImageView = new ImageView(this);
	    mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	    mImageView.setBackgroundColor(BgColor);
	    mImageView.setImageBitmap( mBackgroundImage );
        
        // Add ImageView to Layout
        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.pieGraph);
        myLayout.addView(mImageView);
        */
        
	    // Add created PieChart ImageView to ImageView interface
        ImageView image = (ImageView) findViewById(R.id.pieGraph);
        //image.setImageBitmap(mBackgroundImage);
        image.setImageDrawable(new BitmapDrawable(mBackgroundImage));

    }
	
	@Override public boolean onTouchEvent(MotionEvent ev) {
     
		if (mGestureDetector.onTouchEvent(ev))
			return true;
		else
			return false;
	}
	
	public void rotate(int direction){                      
		int angle;
		
		if (direction == 0){
			angle = -280;
			hyperspaceJump = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fling_rotate_left);
		}
		else{
			angle = 280;
			hyperspaceJump = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fling_rotate_right);
		}
		
		hyperspaceJump.setFillBefore(false);
		ImageView myImage = (ImageView) findViewById(R.id.pieGraph);
		

		hyperspaceJump.setAnimationListener(new AnimationListener(){
	        private boolean doEnd = true;
			
	        @Override public void onAnimationEnd(Animation animation) {
	            if (doEnd){
	                doEnd = false;
	                hyperspaceJump.set
	                //setViewPos(view, animation, anim.getStartTime() + anim.getDuration());
	            }
	        }

	        @Override public void onAnimationRepeat(Animation animation) {
	        }

	        @Override public void onAnimationStart(Animation animation) {
	        }

	    });

		
		
		myImage.startAnimation(hyperspaceJump);
		
		/*Matrix mat = myImage.getImageMatrix();
	    mat.postScale(1, 1);
	    mat.postRotate(angle, myImage.getWidth()/2, myImage.getHeight()/2);
	    myImage.setImageMatrix(mat);
		myImage.setScaleType(ScaleType.CENTER);
		myImage.invalidate();
		*/

	}	
    
    /*
     * Class to listen to gestures
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
    }
}