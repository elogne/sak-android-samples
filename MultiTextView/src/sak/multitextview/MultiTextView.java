package sak.multitextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;

public class MultiTextView extends View {

	private int width = 55;
	private int height = 50;
	private String mainText = null;
	private String subText = null;
	
	public MultiTextView(Context context) {
		super(context);
	}
	
	public MultiTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttribute(attrs);
	}
	
	private void initAttribute(AttributeSet attrs){
		mainText = attrs.getAttributeValue(null, "mainText");
		subText = attrs.getAttributeValue(null, "subText");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {

		// 背景設定
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.DKGRAY);
		canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), bgPaint);
		
		// 文字設定（メインテキスト）
		Paint mainTextPaint = new Paint();
		mainTextPaint.setColor(Color.WHITE);
		mainTextPaint.setTextSize(25);
		mainTextPaint.setAntiAlias(true);

		// 描画（メインテキスト）
		if (mainText != null) {
			PointF textPoint = getTextPoint(mainTextPaint, mainText, width / 2 - 5, height / 2);
			canvas.drawText(mainText, textPoint.x, textPoint.y, mainTextPaint);
		}

		// 文字設定（サブテキスト）
		Paint subTextPaint = new Paint();
		subTextPaint.setColor(Color.LTGRAY);
		subTextPaint.setTextSize(12);
		subTextPaint.setAntiAlias(true);
		
		// 描画（サブテキスト）
		if (subText != null) {
			PointF textPoint1 = getTextPoint(subTextPaint, subText, 40, 35);
			canvas.drawText(subText, textPoint1.x, textPoint1.y, subTextPaint);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(width, height);
	}
	
	private PointF getTextPoint(Paint textPaint, String text, int centerX, int centerY){
		FontMetrics fontMetrics = textPaint.getFontMetrics();
		float textWidth = textPaint.measureText(text);
		float baseX = centerX - textWidth / 2;
		float baseY = centerY - (fontMetrics.ascent + fontMetrics.descent) / 2;
		return new PointF(baseX, baseY);
	}
}
