package ru.fml239.myaspheroid;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Coins
{
	public ArrayList<Body> money;
	private Body tappedCoin = new Body();   
	
	Coins()
	{
		money = new ArrayList<Body>();
	}
	
	int updateScore(float deltaT, Collider collider)
	{
		int result = 0;
		for (int i = money.size() - 1; i >= 0; i--)
		{
			Body currentCoin = money.get(i);
			if (currentCoin.isOutOfScreen())
				money.remove(i);
			else
			{
				currentCoin.update(new Vector2(0, 0), deltaT);
				if (currentCoin.collides(collider))
				{
					if (currentCoin.isEqual(collider))
					{
						result = currentCoin.makeReward();
						collider.changeColor();
					}
					else
						SpheroidView.lives--;
					money.remove(i);
				}
			}
		}
		return result;
	}
	
	void drawAll(Canvas canvas, Paint paint, float w, float h)
	{
		for (int i = 0; i < money.size(); i++)
		{
			money.get(i).draw(canvas, paint, w, h);
			
		}
	}
	
	void setVelocityAll()
	{
		
	}
	
	void destroy(int strength)
	{
		
	}
	
	Body tappedMoney(float x, float y)
	{
		float offset = 1000;
		for (int i = 0; i < money.size(); i++)
		{
			float len = (float)money.get(i).pos.minus(new Vector2(x, y)).len();
			if (len < offset)
			{
				offset = len;
				tappedCoin = money.get(i);
			}
		}
		return tappedCoin;
	}
}

