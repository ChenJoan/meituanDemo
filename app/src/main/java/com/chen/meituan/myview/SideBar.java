package com.chen.meituan.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chen.meituan.R;

public class SideBar extends View {
	// �����¼�
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26����ĸ
	public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "#" };
	private int choose = -1;// ѡ��
	private Paint paint = new Paint();

	private TextView mTextDialog;

	/**
	 * ΪSideBar������ʾ��ĸ��TextView
	 * 
	 * @param mTextDialog
	 */
	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}

	/**
	 * ��д�������
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ��ȡ����ı䱳����ɫ.
		int height = getHeight();// ��ȡ��Ӧ�߶�
		int width = getWidth(); // ��ȡ��Ӧ���
		int singleHeight = height / b.length;// ��ȡÿһ����ĸ�ĸ߶�

		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.rgb(33, 65, 98));
			// paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(singleHeight - 12);

			// x��������м�-�ַ�����ȵ�һ��.
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			// ѡ�е�״̬
			if (i == choose) {
				// jhd ���Ʒ���
				float wid = width * 2 / 3;
				float hei = singleHeight * 4 / 5;
				// jhd ����λ��
				float rectXPos = width / 2 - wid / 2;
				float rectYPos = singleHeight * i + singleHeight;
				// ����ѡ�е�ʱ��ķ���
				canvas.drawRect(rectXPos, rectYPos - hei, rectXPos + wid,
						rectYPos, paint);

				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);

			}
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();// ���û���
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// ���y����
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);// ���y������ռ�ܸ߶ȵı���*b����ĳ��Ⱦ͵��ڵ��b�еĸ���.

		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundResource(android.R.color.transparent);// ����͸��
			choose = -1;//
			invalidate(); // Ӧ������
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.GONE);
			}
			break;

		default:
			setBackgroundResource(R.drawable.sidebar_background);
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
						Log.e("jhd", "onTouchingLetterChanged ���ڴ���");
					}
					if (mTextDialog != null) {
						Log.e("jhd", "mTextDialog ��Ϊ null");
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}

					choose = c;
					invalidate();
				}
			}

			break;
		}
		return true;
	}

	/**
	 * ���⹫���ķ���
	 * 
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * �ӿ�
	 * 
	 * @author coder
	 * 
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}