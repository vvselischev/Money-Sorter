package ru.fml239.myaspheroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TimeMoneyCreator extends AbstractFactory
{
	private Bitmap image;
	
	public TimeMoneyCreator(Activity activity)
	{
		//image = BitmapFactory.decodeResource(activity.getResources(), R.drawable.money);
	}
	
	@Override
	public Body createMoney()
	{
		product = new TimeMoney(image, startX, startY, velocityX, velocityY);
		product.value = values[random.nextInt(4)+ 1];
		return product;
	}
	
}
