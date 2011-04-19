package ch.weather;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;



/*
 * Class to manage main interface
 */
public class simpleWeather extends Activity {

    /** Called when the activity is first created. */
	@Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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
        //Bitmap mBackgroundImage = Bitmap.createBitmap(Size, Size, Bitmap.Config.RGB_565);
    	PieChart myPie = new PieChart(this);
    	myPie.setLayoutParams(new LayoutParams(Size, Size));
        myPie.setGeometry(Size, Size, 5, 5, 5, 5, 0);
    	myPie.setSkinParams(BgColor);
    	myPie.setData(PieData, MaxCount);
        myPie.invalidate();
        
        // draw Pie on Canvas with specified bitmap
       // myPie.draw(new Canvas(mBackgroundImage));
        
	    // Add created PieChart ImageView to ImageView interface
        //ImageView image = (ImageView) findViewById(R.id.pieGraph);
        //image.setImageBitmap(mBackgroundImage);
        
        FrameLayout frame = (FrameLayout) findViewById(R.id.pie_graph);
        frame.addView(myPie); 
    }
	

	public class PieItem {
		public int    Count;
		public String Label;
		public float  Percent;
		public int    Color;
	}

	public class PieChart extends View {
		private static final int WAIT = 0;
		private static final int IS_READY_TO_DRAW = 1;
		private static final int IS_DRAW = 2;
		private static final float START_INC = 30;
		private Paint mBgPaints   = new Paint();
		private Paint mLinePaints = new Paint();
		private int   mOverlayId;
		private int   mWidth;
		private int   mHeight;
		private int   mGapLeft;
		private int   mGapRight;
		private int   mGapTop;
		private int   mGapBottom;
		private int   mBgColor;
		private int   mState = WAIT;
		private float mStart;
		private float mSweep;
		private int   mMaxConnection;
		private List<PieItem> mDataArray;
		private GestureDetector mGestureDetector;
		Animation rotateAnim;
		private Matrix translate;
		Bitmap mBackgroundImage;
		
		public PieChart (Context context){
			super(context);
		
			translate = new Matrix();
	        // Create gesture detector scanner
			mGestureDetector = new GestureDetector(simpleWeather.this, new myGestureListener(this));
			
			int Size = 300;
	        mBackgroundImage = Bitmap.createBitmap(Size, Size, Bitmap.Config.RGB_565);
	        
		}
		
		public PieChart(Context context, AttributeSet attrs) {
	        super(context, attrs);
		}
	   
	    @Override protected void onDraw(Canvas canvas) {
	    	super.onDraw(canvas);

            Canvas singleUseCanvas = new Canvas(); 
            singleUseCanvas.setBitmap(mBackgroundImage);
	    	
	        //if (mState != IS_READY_TO_DRAW) return;
	        singleUseCanvas.drawColor(mBgColor);
	    	
	    	mBgPaints.setAntiAlias(true);
	    	mBgPaints.setStyle(Paint.Style.FILL);
	    	mBgPaints.setColor(0x88FF0000);
	    	mBgPaints.setStrokeWidth(0.5f);
	    	
	    	mLinePaints.setAntiAlias(true);
	    	mLinePaints.setStyle(Paint.Style.STROKE);
	    	mLinePaints.setColor(0xff000000);
	    	mLinePaints.setStrokeWidth(0.5f);
	    	
	    	RectF mOvals = new RectF( mGapLeft, mGapTop, mWidth - mGapRight, mHeight - mGapBottom);
	    	
	    	mStart = START_INC;
	    	PieItem Item;
	    	for (int i = 0; i < mDataArray.size(); i++) {
	    		Item = (PieItem) mDataArray.get(i);
	    		mBgPaints.setColor(Item.Color);
	    		mSweep = (float) 360 * ( (float)Item.Count / (float)mMaxConnection );
	    		singleUseCanvas.drawArc(mOvals, mStart, mSweep, true, mBgPaints);
	    		singleUseCanvas.drawArc(mOvals, mStart, mSweep, true, mLinePaints);
	    		mStart += mSweep;
	        }

	    	Options options = new BitmapFactory.Options();
	        options.inScaled = false;
	        
	        if (mOverlayId > 0){
	        	Bitmap OverlayBitmap = BitmapFactory.decodeResource(getResources(), mOverlayId, options);
	        	canvas.drawBitmap(OverlayBitmap, 0.0f, 0.0f, null);        	
	        }
	        
	        canvas.drawBitmap(mBackgroundImage, translate , null);
	        
	    	mState = IS_DRAW;
	    }
	    

	    public void setGeometry(int width, int height, int GapLeft, int GapRight, int GapTop, int GapBottom, int OverlayId) {
	    	mWidth     = width;
	   	 	mHeight    = height;
	   	 	mGapLeft   = GapLeft;
	   	 	mGapRight  = GapRight;
	   	 	mGapTop    = GapTop;
	   	 	mGapBottom = GapBottom;
	   	 	mOverlayId = OverlayId;
	    }

	    public void setSkinParams(int bgColor) {
	   	 	mBgColor   = bgColor;
	    }
	    
	    public void setData(List<PieItem> data, int MaxConnection) {
	    	mDataArray = data;
	    	mMaxConnection = MaxConnection;
	    	mState = IS_READY_TO_DRAW;
	    }

	    public void setState(int State) {
	    	mState = State;
	    }

	    public int getColorValue( int Index ) {
	   	 	if (mDataArray == null) return 0;
	   	 	if (Index < 0){
	   	 		return ((PieItem)mDataArray.get(0)).Color;
	   	 	} else if (Index >= mDataArray.size()){
	   	 		return ((PieItem)mDataArray.get(mDataArray.size()-1)).Color;
	   	 	} else {
	   	 		return ((PieItem)mDataArray.get(mDataArray.size()-1)).Color;
	   	 	}
	    }
		
	    /*
		 * Method to define and apply animation 
		 * @author: Julien Freudiger
		 */
		public void rotate(int direction){                      
			 			
			// Define animation
			float from = 0;
			float to = 560;
			rotateAnim = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim.setInterpolator(new DecelerateInterpolator());
			rotateAnim.setDuration(3000);
			rotateAnim.setFillAfter(true);
			
			// Apply animation
			startAnimation(rotateAnim);
			translate.postRotate(to, getWidth()/2, getHeight()/2);
    	    invalidate();
		}	
		
		
		@Override public boolean onTouchEvent(MotionEvent ev) {
//            return mGestureDetector.onTouchEvent(ev);

			if (mGestureDetector.onTouchEvent(ev))
   				return true;
            else
            	return false;
		}
	}
	   
	/*
     * Class to listen to gestures
     * @author: Julien Freudiger
     */
    public class myGestureListener extends GestureDetector.SimpleOnGestureListener{
    	private static final int SWIPE_MIN_DISTANCE = 120;
   	  	private static final int SWIPE_MAX_OFF_PATH = 250;
   	  	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
   	   	  	
   	  	PieChart view;  
   	  	public myGestureListener(PieChart view) {  
   	  		this.view = view;  
     	}  
    	
    	@Override public boolean onDown(MotionEvent ev){
    		//Toast.makeText(getApplicationContext(), "Down", Toast.LENGTH_SHORT).show();
    		//view.rotate(0);
    		return true;
    	}
    	
    	@Override public boolean onSingleTapUp(MotionEvent ev) {
    		//Toast.makeText(getApplicationContext(), "SingleTapUp", Toast.LENGTH_SHORT).show();
    		return true;
    	}
    	
    	
    	@Override public void onShowPress(MotionEvent ev) {
    		//Toast.makeText(getApplicationContext(), "ShowPress", Toast.LENGTH_SHORT).show();
    		
    	}
    	
    	@Override public void onLongPress(MotionEvent ev) {
    		//Toast.makeText(getApplicationContext(), "LongPress", Toast.LENGTH_SHORT).show();
    		
    	}
    	
    	@Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
    	//	Toast.makeText(getApplicationContext(), "Scroll", Toast.LENGTH_SHORT).show();
    	    return true;
    	}
    	
    	
    	@Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    		
    		//RelativeLayout myLayout2 = (RelativeLayout) findViewById(R.id.pieGraph);
    		if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) // not a straight fling
    		    return false;
               
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // left swipe
            	Toast.makeText(getApplicationContext(), "LeftSwipe", Toast.LENGTH_SHORT).show();
            	view.rotate(0);
            }  
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	// right swipe
            	Toast.makeText(getApplicationContext(), "RightSwipe", Toast.LENGTH_SHORT).show();
            	view.rotate(1);
            }

            // top to bottom swipe
            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                // down to top swipe
            	Toast.makeText(getApplicationContext(), "DownTopSwipe", Toast.LENGTH_SHORT).show();
            	view.rotate(0);
            }  
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            	// top to down swipe
            	Toast.makeText(getApplicationContext(), "TopDownSwipe", Toast.LENGTH_SHORT).show();
            	view.rotate(1);
            }

	    	return false;
    	    
    	}
    }
}