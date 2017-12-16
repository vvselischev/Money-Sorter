package ru.fml239.myaspheroid;

import com.example.moneysorter.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SimpleMoneyCreator extends AbstractFactory
{
	private Bitmap image[] = new Bitmap[4];
	
	public SimpleMoneyCreator(Activity activity)
	{
		image[0] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.coin1);
		image[1] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.coin2);
		image[2] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.coin5);
		image[3] = BitmapFactory.decodeResource(activity.getResources(), R.drawable.coin10);
	}
	
	@Override
	public Body createMoney()
	{
		int randomNumber = random.nextInt(4);
		product = new SimpleMoney(image[randomNumber], startX, startY, velocityX, velocityY);
		product.value = values[randomNumber];
		return product;
	}
}
