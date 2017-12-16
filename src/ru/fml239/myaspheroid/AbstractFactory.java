package ru.fml239.myaspheroid;

import java.util.Random;

public abstract class AbstractFactory {
	
	protected static Random random;
	protected static double startX;
	protected static double startY;
	protected static double velocityX;
	protected static double velocityY;
	protected Body product;
	protected int[] values = {1, 2, 5, 10};
	public abstract Body createMoney();

}
