package com.ljwj.ddb.taimian.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class SweepView extends ViewGroup {

	protected static final String TAG = "SweepView";
	private static boolean flags;
	private View mLeftChild;
	private View mRightChild;
	
	public boolean isOpen = false;//默认关闭
	//帮助实现拖拽的工具类
	private ViewDragHelper mViewDragHelper;
	private int x_down;
	private int x_up;

	public static void isFlag(boolean flag){
		flags=flag;
	}

	public SweepView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//step1 初始ViewDragHelper
		mViewDragHelper = ViewDragHelper.create(this, mCallback);
	}
	
	//step3:实现回调
	private Callback mCallback = new Callback() {
		
		/**
		 * 处理ACTION_DOWN的回调
		 * 
		 * @param 尝试捕捉的孩子 View (点击位置的孩子)
		 * 
		 * @return true 表示允许捕捉孩子
		 */
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			Log.d(TAG, "tryCaptureView ");
			return true;
		}
		/**
		 * 水平方向拖动
		 * 
		 * @param child 要拖拽的孩子
		 * @param left 要拖动孩子的参考的left位置
		 * @return 返回要拖动孩子新的左边的位置
		 * 
		 */
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (child == mLeftChild) {
				//左边孩子left位置的范围 （0， -右边孩子的宽度）
				if(left > 0) {
					left = 0;
				} else if (left < -mRightChild.getWidth()){
					left = -mRightChild.getWidth();
				}
			} else if(child == mRightChild) {
				//右边孩子的范围（屏幕宽度减去右边孩子宽度， 屏幕宽度）
				int min =  getWidth() - mRightChild.getWidth();
				if (left < min) {
					left = min;
				} else if (left > getWidth()) {
					left = getWidth();
				}
			}
			return left;
		}
		/**
		 * 当view的位置发生变化之后回调
		 * @param changedView 位置发生变化的孩子
		 * @param changedView当前的left位置
		 * @param dx changedView在x轴的变化量
		 * 
		 */
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			//当拖动左边孩子的时候拉出右边孩子
			if (changedView == mLeftChild) {
				//重新布局右边孩子
				//左边孩子在x轴上发生了dx的变化量，右边孩子就应该发生同样的变化量
				mRightChild.layout(mRightChild.getLeft() + dx, mRightChild.getTop(),
						mRightChild.getRight() + dx, mRightChild.getBottom());
			} else if (changedView == mRightChild) {
				//拖动右边孩子的时候拉动左边孩子
				//右边孩子在x轴上发生了dx的变化量，左边孩子就应该发生同样的变化量
				mLeftChild.layout(mLeftChild.getLeft() + dx, mLeftChild.getTop(),
						mLeftChild.getRight() + dx, mLeftChild.getBottom());
			}
		}

		/**
		 * ACTION_UP的回调
		 * 
		 * @param 松开的孩子
		 */
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			int threshold = getWidth() - mRightChild.getWidth() / 2;
			//如果右边孩子的左边位置小于临界点，松开，全部滚出来
			if (mRightChild.getLeft() < threshold) {
				//用viewdraghelper实现平滑滚动
				//child 要滚动的view
				int finalLeft = getWidth() - mRightChild.getWidth();//最终位置左边位置
				//mViewDragHelper封装了scroller, mScroller.startScroll(startLeft, startTop, dx, dy, duration);
				mViewDragHelper.smoothSlideViewTo(mRightChild, finalLeft, 0);
				invalidate();//触发绘制
				
				isOpen = true;
			} else {
				//如果右边孩子的左边位置大于或者等于临界点，松开，全部滚进去
				//用viewdraghelper实现平滑滚动
				//child 要滚动的view
				int finalLeft = getWidth();//最终位置左边位置
				//mViewDragHelper封装了scroller, mScroller.startScroll(startLeft, startTop, dx, dy, duration);
				mViewDragHelper.smoothSlideViewTo(mRightChild, finalLeft, 0);
				invalidate();//触发绘制
				
				isOpen = false;
			}
		}
	};
	
	public boolean isOpen() {
		return isOpen;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//测量孩子
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mLeftChild = getChildAt(0);
		//布局左边孩子
		mLeftChild.layout(0, 0, mLeftChild.getMeasuredWidth(), mLeftChild.getMeasuredHeight());
		//布局右边孩子
		mRightChild = getChildAt(1);
		int right = mLeftChild.getMeasuredWidth() + mRightChild.getMeasuredWidth();
		mRightChild.layout(mLeftChild.getMeasuredWidth(), 0, right, mRightChild.getMeasuredHeight());
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//step2:将touch事件传给ViewDragHelper处理
		mViewDragHelper.processTouchEvent(event);
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				x_down=(int)event.getX();
				break;
			case MotionEvent.ACTION_UP:
				x_up=(int)event.getX();
				break;
		}
		if(x_up<x_down && x_up-x_down<-50){
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void computeScroll() {
		//continueSettling内部封装mScroller.computeScrollOffset();
		//并且实现了滚动，continueSettling返回true，表示滚动还没有结束
		if (mViewDragHelper.continueSettling(true)) {
//			invalidate();//触发重新绘制-》computeScroll
			ViewCompat.postInvalidateOnAnimation(this);//兼容性
		}
	}

	public void close() {
		//如果右边孩子的左边位置大于或者等于临界点，松开，全部滚进去
		//用viewdraghelper实现平滑滚动
		//child 要滚动的view
		int finalLeft = getWidth();//最终位置左边位置
		//mViewDragHelper封装了scroller, mScroller.startScroll(startLeft, startTop, dx, dy, duration);
		mViewDragHelper.smoothSlideViewTo(mRightChild, finalLeft, 0);
		invalidate();//触发绘制
		
		isOpen = false;		
	}
}
