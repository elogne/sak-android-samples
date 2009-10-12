package sak.icongridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.View;

public class MyView2 extends View {

//	private Context context = null;
	private Bitmap bitmap = null;
	int width = 0;
	int height = 0;
	
	public MyView2(Context context) {
		super(context);
//		this.context = context;
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mailwidget);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
	}
	
	public MyView2(Context context, AttributeSet attrs) {
		super(context, attrs);
//		this.context = context;
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.airdo);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); 
		Canvas offScreen = new Canvas(newBitmap);

		int round = 15;
//		RectF rect = new RectF(offScreen.getClipBounds());
		RectF rect = new RectF(0, 0, width, height);
		Path path = new Path();
		path.addRoundRect(rect, round, round, Direction.CW);
		Region clip = new Region(0, 0, width, height);
		clip.setPath(path, clip);
		offScreen.clipRegion(clip);
		offScreen.drawBitmap(bitmap, 0, 0, null);

		canvas.drawBitmap(newBitmap, 0, 0, null);
//		canvas.drawBitmap(bitmap, 0, 0, null);

	}

	
}
