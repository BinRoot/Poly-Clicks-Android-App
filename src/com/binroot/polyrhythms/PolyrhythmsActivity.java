package com.binroot.polyrhythms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

import com.binroot.polyrhythms.GraphicObject.Coordinates;

public class PolyrhythmsActivity extends Activity {
    /** Called when the activity is first created. */
	
	private ArrayList<GraphicObject> _graphics = new ArrayList<GraphicObject>();
	private ArrayList<LeftGraphic> leftGraphics = new ArrayList<LeftGraphic>();
	private ArrayList<RightGraphic> rightGraphics = new ArrayList<RightGraphic>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
    }
    
    
    class Panel extends SurfaceView implements SurfaceHolder.Callback {
    	private TutorialThread _thread;
    	String numOfTouch = "---";
    	String leftTouch = "---";
    	String rightTouch = "---";
    	
    	
        public Panel(Context context) {
            super(context);
            getHolder().addCallback(this);
            
            // Left side
            LeftGraphic graphic = new LeftGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon), null);
            graphic.getCoordinates().setX(40 - graphic.getGraphic().getWidth() / 2);
            graphic.getCoordinates().setY(100 - graphic.getGraphic().getHeight() / 2);
            leftGraphics.add(graphic);
            
            LeftGraphic graphic2 = new LeftGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon), graphic);
            graphic2.getCoordinates().setX(40 - graphic.getGraphic().getWidth() / 2);
            graphic2.getCoordinates().setY(200 - graphic.getGraphic().getHeight() / 2);
            leftGraphics.add(graphic2);
            
            LeftGraphic graphic3 = new LeftGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon), graphic2);
            graphic3.getCoordinates().setX(40 - graphic.getGraphic().getWidth() / 2);
            graphic3.getCoordinates().setY(300 - graphic.getGraphic().getHeight() / 2);
            leftGraphics.add(graphic3);
            
            LeftGraphic graphic4 = new LeftGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon), graphic3);
            graphic4.getCoordinates().setX(40 - graphic.getGraphic().getWidth() / 2);
            graphic4.getCoordinates().setY(400 - graphic.getGraphic().getHeight() / 2);
            leftGraphics.add(graphic4);
            
         // Right side
            RightGraphic graphic1b = new RightGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon), null);
            graphic1b.getCoordinates().setX(400 - graphic.getGraphic().getWidth() / 2);
            graphic1b.getCoordinates().setY(100 - graphic.getGraphic().getHeight() / 2);
            rightGraphics.add(graphic1b);
            
            RightGraphic graphic2b = new RightGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon), graphic1b);
            graphic2b.getCoordinates().setX(400 - graphic.getGraphic().getWidth() / 2);
            graphic2b.getCoordinates().setY(250 - graphic.getGraphic().getHeight() / 2);
            rightGraphics.add(graphic2b);
            
            RightGraphic graphic3b = new RightGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon), graphic2b);
            graphic3b.getCoordinates().setX(400 - graphic.getGraphic().getWidth() / 2);
            graphic3b.getCoordinates().setY(400 - graphic.getGraphic().getHeight() / 2);
            rightGraphics.add(graphic3b);

            
            
            _thread = new TutorialThread(getHolder(), this);
            setFocusable(true);
        }
     
        @Override
        public void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            Bitmap bitmap;
            
            for (RightGraphic graphic : rightGraphics) {
                bitmap = graphic.getGraphic();
                canvas.drawBitmap(bitmap,  graphic.getCoordinates().getX(),  graphic.getCoordinates().getY(), null);
            }
            
            for (LeftGraphic graphic : leftGraphics) {
                bitmap = graphic.getGraphic();
                canvas.drawBitmap(bitmap, graphic.getCoordinates().getX(), graphic.getCoordinates().getY(), null);
            }
            
            Paint p = new Paint();
            p.setColor(Color.WHITE);
            canvas.drawText(numOfTouch, new Float(100), new Float(100), p);
            canvas.drawText(leftTouch, new Float(100), new Float(150), p);
            canvas.drawText(rightTouch, new Float(100), new Float(180), p);
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            synchronized (_thread.getSurfaceHolder()) {
            	
            	String locs = "";
            		
            	for(int i=0; i<event.getPointerCount(); i++) {
            		Log.d("Graphics", event.getX(i)+", "+event.getY(i));
            		locs += "("+event.getX(i)+", "+event.getY(i)+") ";
            	}
            	//numOfTouch = "touch count: "+event.getPointerCount();// + " " + locs;
            	
            	
            	
            	numOfTouch = event.getAction() + "" ;
            	
            	
            	
            	if(event.getAction() == 261) {
            		float x1 = event.getX(0);
            		float y1 = event.getY(0);
            		float x2 = event.getX(1);
            		float y2 = event.getY(1);
            		
            		boolean leftFound = false;
            		boolean rightFound = false;
            		for(LeftGraphic gObj : leftGraphics) {
            			
            			int gx = gObj.getCoordinates().getX() + gObj.getGraphic().getWidth()/2;
            			int gy = gObj.getCoordinates().getY() + gObj.getGraphic().getHeight()/2;
            			if( (x1<gx+30) && (x1>gx-30) && (y1<gy+30) && (y1>gy-30)) {
            				leftTouch = "worked1!";
            				gObj.setGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
            				leftFound = true;
            				
            			}
            			if( (x2<gx+30) && (x2>gx-30) && (y2<gy+30) && (y2>gy-30)) {
            				rightTouch = "worked2!";
            				gObj.setGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
            				rightFound = true;
            				
            			}
            			
            		}
            		for(GraphicObject gObj : _graphics) {
            			
            			int gx = gObj.getCoordinates().getX() + gObj.getGraphic().getWidth()/2;
            			int gy = gObj.getCoordinates().getY() + gObj.getGraphic().getHeight()/2;
            			if( (x1<gx+30) && (x1>gx-30) && (y1<gy+30) && (y1>gy-30)) {
            				leftTouch = "worked1!";
            				gObj.setGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
            				leftFound = true;
            				
            			}
            			if( (x2<gx+30) && (x2>gx-30) && (y2<gy+30) && (y2>gy-30)) {
            				rightTouch = "worked2!";
            				gObj.setGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
            				rightFound = true;
            				
            			}
            		}
            		
            		if(!leftFound) {
            			leftTouch = "---";
            		}
            		if(!rightFound) {
            			rightTouch = "---";
            		}
            	}
            	
            	if(event.getAction() == MotionEvent.ACTION_DOWN) {
            		float x1 = event.getX();
            		float y1 = event.getY();
            		for(RightGraphic gObj : rightGraphics) {
            			
            			int gx = gObj.getCoordinates().getX() + gObj.getGraphic().getWidth()/2;
            			int gy = gObj.getCoordinates().getY() + gObj.getGraphic().getHeight()/2;
            			if( (x1<gx+30) && (x1>gx-30) && (y1<gy+30) && (y1>gy-30)) {
            				// TODO: change graphics
            				
            				if(gObj.getPrevGraphicState()==true) {
            					gObj.setClicked(true);
            					gObj.setGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
                				break;
            				}

            			}
            		}
            		for(LeftGraphic gObj : leftGraphics) {
            			
            			int gx = gObj.getCoordinates().getX() + gObj.getGraphic().getWidth()/2;
            			int gy = gObj.getCoordinates().getY() + gObj.getGraphic().getHeight()/2;
            			if( (x1<gx+30) && (x1>gx-30) && (y1<gy+30) && (y1>gy-30)) {
            				// TODO: change graphics
            				
            				if(gObj.getPrevGraphicState()==true) {
            					gObj.setClicked(true);
                				gObj.setGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
                				break;
            				}
            				
            			}
            		}
            	}
            	
            	return true;
            }
        }
        
        public void updatePhysics() {
            // STUD 
        }

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			_thread.setRunning(true);
		    _thread.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			boolean retry = true;
		    _thread.setRunning(false);
		    while (retry) {
		        try {
		            _thread.join();
		            retry = false;
		        } catch (InterruptedException e) {
		            // we will try it again and again...
		        }
		    }
		}
    }
    
    class TutorialThread extends Thread {
        private SurfaceHolder _surfaceHolder;
        private Panel _panel;
        private boolean _run = false;
     
        public TutorialThread(SurfaceHolder surfaceHolder, Panel panel) {
            _surfaceHolder = surfaceHolder;
            _panel = panel;
        }
        
        public SurfaceHolder getSurfaceHolder() {
            return _surfaceHolder;
        }
     
        public void setRunning(boolean run) {
            _run = run;
        }
     
        @Override
        public void run() {
            Canvas c;
            while (_run) {
                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                    	_panel.updatePhysics();
                        _panel.onDraw(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
    
    /**
	 * Menu display
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add("Clear");
		return result;   
	}

	
	/**            
	 * Menu event listener
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Clear")) {
			for(RightGraphic gObj : rightGraphics) {
				gObj.setGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
				gObj.setClicked(false);
			}
			for(LeftGraphic gObj : leftGraphics) {
				gObj.setGraphic(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
				gObj.setClicked(false);
			}
		}
		return true;
	}
}