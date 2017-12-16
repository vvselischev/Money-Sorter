package ru.fml239.myaspheroid;

import java.util.ArrayList;
import java.util.Random;

import com.example.moneysorter.R;

import android.view.*;
import android.app.*;
import android.graphics.*;
import android.content.*;
import android.util.*;

public class SpheroidView extends SurfaceView implements SurfaceHolder.Callback
{
	public static final int ACTION_MOVE = 1;
	public static final int ACTION_DOWN = 2;
	public static final int ACTION_UP = 3;
	public static final int ACTION_DOUBLE_TAP = 4;

	private static final int PHASE_SETUP = 1;
	private static final int PHASE_MOVING = 2;
	private static final int PHASE_RESULT = 3;

	private SpheroidThread spheroidThread;
	private Activity activity;
	private boolean dialogIsDisplayed = false;
	private boolean gameOver = false;
	private int gamePhase;
    private float curDeltaTimeBetween = 0;
	private Body moneyTapped = new Body();
	private Coins coins; 
	private Body backGround;
	private float curStartPositionX;
	private float curStartPositionY;
	private Vector2 moneyVelocity;

	public static float screenHeight;
	public static float screenWidth;
	private Paint paint;
	private Paint textPaint;
	private Paint backgroundPaint;

	private float xMouse,yMouse;
	private boolean moveMouse = false;
	private String log = "";
	
	private Body curMoneyTapped = new Body();
	
	private AbstractFactory currentFactory;
	private SimpleMoneyCreator simpleMoneyCreator;
	private BombMoneyCreator bombMoneyCreator;
	private TimeMoneyCreator timeMoneyCreator;
    private Collider[] colliders = new Collider[2];
	public static int lives;
	private int score;
	private Random random = new Random();
	
	public SpheroidView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		activity = (Activity)context;
		Log.d("CONSTRUCTOR", "Executing");

		getHolder().addCallback(this);

		paint = new Paint();
		textPaint = new Paint();
		backgroundPaint = new Paint();
		
		
		
		simpleMoneyCreator = new SimpleMoneyCreator(activity);
		bombMoneyCreator = new BombMoneyCreator(activity);
		timeMoneyCreator = new TimeMoneyCreator(activity);
		currentFactory = simpleMoneyCreator;
		
		gamePhase = PHASE_SETUP;
		//gestureDetector = new GestureDetector(context,	new MyGestureListener());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		screenHeight = h;
		screenWidth = w;
		backGround = new BackTexture(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bktexturet));
		moneyVelocity = new Vector2(0, 200);
		coins = new Coins();
		curStartPositionX = w/2.2f;
		curStartPositionY = -71;
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(h / 20);
		
		AbstractFactory.startX = curStartPositionX;
		AbstractFactory.startY = curStartPositionY;
		AbstractFactory.velocityX = moneyVelocity.x;
		AbstractFactory.velocityY = moneyVelocity.y;
		AbstractFactory.random = random;
		
		colliders[0] = new Collider(new RectF(w/2.5f, h/2.4f, w/2.2f, h/2.1f), 10);
		colliders[1] = new Collider(new RectF(w/1.82f, h/1.76f, w/1.62f, h/1.6f), 5);
		lives = 3;
		
		newGame();
	}

	public void newGame()
	{
		// insert initial new game here
		if (gameOver)
		{
			gameOver = false;
			spheroidThread = new SpheroidThread(getHolder());
			spheroidThread.start();
		}
	}

	private void updateAll(double deltaT)
	{
		switch (gamePhase)
		{
			case PHASE_SETUP:
				  if (lives == 0)
					  gamePhase = PHASE_RESULT;
				break;
			case PHASE_MOVING:
				break;
			case PHASE_RESULT:
				  spheroidThread.setRunning(false);
				  gameOver = true;
				break;
		}
		
		if (curDeltaTimeBetween <= 0)
		{
			coins.money.add(currentFactory.createMoney());
			//curDeltaTimeBetween = (float)(coins.money.get(coins.money.size()-1).sizeH / moneyVelocity.len());
			curDeltaTimeBetween = .325f;
		}
		else
			curDeltaTimeBetween -= deltaT;
		
		for (int i = 0; i < colliders.length; i++)
			score += coins.updateScore((float)deltaT, colliders[i]); 
	}

	private void drawAll(Canvas canvas)
	{
		backGround.draw(canvas, paint, screenWidth, screenHeight);
		
		coins.drawAll(canvas, paint, screenWidth, screenHeight);
		canvas.drawText("" + score, 50, 100, textPaint);
		canvas.drawText("" + lives, 300, 100, textPaint);
		
		for (int i = 0; i < colliders.length; i++)
			canvas.drawRect(colliders[i].rect, colliders[i].paint);	
		
	}
	@Override
	public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4)
	{
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		Log.d("SURFACE", "Creating");
		spheroidThread = new SpheroidThread(holder);
		spheroidThread.setRunning(true);
		spheroidThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		boolean retry = true;
		spheroidThread.setRunning(false);
		Log.d("SURFACE", "Destroing");

		while (retry)
		{
			try
			{
				spheroidThread.join();
				retry = false;
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	// stops the game
	public void stopGame()
	{
		if (spheroidThread != null)
			spheroidThread.setRunning(false);
	} // end method stopGame

	// releases resources; called by CannonGame's onDestroy method 
	public void releaseResources()
	{
		//soundPool.release(); // release all resources used by the SoundPool
		//soundPool = null; 
	} // end method releaseResources

	public void motionEvent(int eventType, MotionEvent event)
	{
		switch (eventType)
		{
			
			case ACTION_DOWN:
				if(gamePhase == PHASE_SETUP)
				{
					moneyTapped = coins.tappedMoney(event.getX(), event.getY());
				}
				break;
			case ACTION_UP:
				if(gamePhase == PHASE_SETUP)
				{

				}
				break;
			case ACTION_MOVE:
				if(gamePhase == PHASE_SETUP)
				{
					moneyTapped.setPos(event.getX(), event.getY());
				}
				break;
			case ACTION_DOUBLE_TAP:
				break;
		}
	}

	public void motionScrollEvent(MotionEvent e1, MotionEvent e2, float dx, float dy)
	{
		
	}

	private class SpheroidThread extends Thread
	{
		private SurfaceHolder surfaceHolder;
		private boolean threadIsRunning = true;

		public SpheroidThread(SurfaceHolder holder)
		{
			surfaceHolder = holder;
			setName("SpheroidThread");
		}

		public void setRunning(boolean running)
		{
			threadIsRunning = running;
		}

		@Override
		public void run()
		{
			Canvas canvas = null;
			double lastTime = System.currentTimeMillis() / 1000.0;
			double currentTime;
			double deltaT;
			Log.d("THREAT", "Starting");

			while (threadIsRunning)
			{
				try
				{
					canvas = surfaceHolder.lockCanvas(null);
					if (canvas != null)
						synchronized (surfaceHolder)
						{
							currentTime = System.currentTimeMillis() / 1000.0;
							deltaT = currentTime - lastTime;
							lastTime = currentTime;
							updateAll(deltaT);
							drawAll(canvas);
						}
					else
						Log.d("THREAD", "Canvas = null");
				}
				finally
				{
					if (canvas != null)
						surfaceHolder.unlockCanvasAndPost(canvas);
				} // finally
			} // while
		} // run
	}
}


