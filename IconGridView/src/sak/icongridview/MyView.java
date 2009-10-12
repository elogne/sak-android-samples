package sak.icongridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Path.Direction;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

//	private Context context = null;
	private Bitmap bitmap = null;
	int width = 0;
	int height = 0;
	
	public MyView(Context context) {
		super(context);
//		this.context = context;
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mailwidget);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
	}
	
	public MyView(Context context, AttributeSet attrs) {
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

		BitmapDrawable drawable = new BitmapDrawable(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		
		int round = 15;
		RectF rect = new RectF(0, 0, width, height);
		Path path = new Path();
		path.addRoundRect(rect, round, round, Direction.CW);
		
		Region clip = new Region(0, 0, width, height);
		clip.setPath(path, clip);
		
		
		
//		offScreen.clipRegion(clip);
//		offScreen.drawBitmap(bitmap, 0, 0, null);
//
//		canvas.drawBitmap(newBitmap, 0, 0, null);
//		canvas.drawBitmap(bitmap, 0, 0, null);

	}

	
}
