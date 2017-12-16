package ru.fml239.myaspheroid;

import android.graphics.Bitmap;

public class SimpleMoney extends Body
{
	SimpleMoney(Bitmap image, double startX, double startY, double velocityX, double velocityY)
	{
		super(image, startX, startY, velocityX, velocityY);
	}
	
	@Override
	int makeReward()
	{
		return value;
	}
}
