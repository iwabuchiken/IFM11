package ifm11.views;

import ifm11.utils.CONS;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

// CanvasView
public class CV extends View {
	
	Context con;
	
	// �`�悷��_�i�[�p���X�g
	private ArrayList<Point> points;
	
	private Paint paint;	

	Paint paint_2;
	float x1, y1, x2, y2;

	public static Canvas can;

	////////////////////////////////

	// flags

	////////////////////////////////
	private boolean draw_Line = false;

	private boolean f_RGB_Lines;
	
	////////////////////////////////
	
	// test
	
	////////////////////////////////
	
	// �R���X�g���N�^
	public CV(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		
		// Log
		String msg_Log = "CV => constructed";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// vars

		////////////////////////////////
		this.con		= context;
		this.paint_2	= new Paint();
//		
//		paint_2.setColor(Color.BLUE);
////      paint.setColor(Color.argb(75, 255, 255, 255));
//		paint_2.setStrokeWidth(5);
//      paint_2.setStrokeWidth(1);

	}

	// onMeasure���\�b�h(�r���[�̃T�C�Y�ݒ菈��)
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		
		// Log
		String msg_Log = "Dimension => set";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	// onDraw���\�b�h(�`�揈��)
	@Override
	protected void 
	onDraw
	(Canvas canvas) {
		
		String msg_Log;
		
		if (CV.can == null) {
			
			CV.can = canvas;
			
		}
		
		////////////////////////////////

		// draw: lines

		////////////////////////////////
		if (this.draw_Line == true) {
			
			canvas.drawLine(this.x1, this.y1, this.x2, this.y2, this.paint);
			
		}
		
		////////////////////////////////

		// draw: RGB lines

		////////////////////////////////
		if (CONS.Canvas.f_RGB_Lines == true) {
			
			_Draw_RGB_Lines(canvas);
			
			CONS.Canvas.f_RGB_Lines = false;
			
		}
		
	}//onDraw

	private void 
	_Draw_RGB_Lines
	(Canvas canvas) {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "_Draw_RGB_Lines";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Log
		if (CONS.Canvas.col_R_adj == null) {
			
			// Log
			msg_Log = "CONS.Canvas.col_R_adj => null";
			Log.d("CV.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {

			msg_Log = "CONS.Canvas.col_R_adj => " + CONS.Canvas.col_R_adj.length;
			Log.d("CV.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		
		}
		
	}//_Draw_RGB_Lines

	public void _go() {
		
		// Log
		String msg_Log = "_onDraw_DrawLine => started";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
        // Log
		msg_Log = "_onDraw_DrawLine => done";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		this.invalidate();
		
	}
	
	public void _clear() {
		
		// Log
		String msg_Log = "_clear => started";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Log
		msg_Log = "_clear => done";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		this.invalidate();
		
	}
	
	// clearDrawList���\�b�h(�N���A����)
	public void clearDrawList() {
		points.clear();
		this.invalidate();
	}


	////////////////////////////////

	// DrawableView.java

	public void 
	drawLine
	(float x1, float y1, float x2, float y2, Paint p) {
		
//		this.drawLine(10, 10, 100, 100, p);

		////////////////////////////////

		// flag: on

		////////////////////////////////
		this.draw_Line = true;
		
		////////////////////////////////

		// prep: vals

		////////////////////////////////
		this.paint	= p;

		this.x1		= x1;
		this.x2		= x2;
		this.y1		= y1;
		this.y2		= y2;
		
		// Log
		String msg_Log = "drawLine() => done";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Log
		msg_Log = "p.getStrokeWidth() => " + p.getStrokeWidth();
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		this.invalidate();
		
	}//drawLine
	
}
