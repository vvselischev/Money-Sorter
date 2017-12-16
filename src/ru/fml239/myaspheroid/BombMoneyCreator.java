package ru.fml239.myaspheroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BombMoneyCreator extends AbstractFactory
{
	private Bitmap image[];
	
	public BombMoneyCreator(Activity activity)
	{
		//image = BitmapFactory.decodeResource(activity.getResources(), R.drawable.);
		//image[0] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.coin1);
	}
	
	@Override
	public Body createMoney()
	{
		//product = new BombMoney(image, startX, startY, velocityX, velocityY);
		product.value = values[random.nextInt(4)+ 1];
		return product;
	}
}
