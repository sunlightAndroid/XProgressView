package me.eric.progress;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import me.eric.progress.util.DisplayUtil;


/**
 * <pre>
 *     author : eric
 *     time   : 2021/06/27
 *     desc   : 带斜线的进度条
 * </pre>
 */
public class XProgressView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF saveArea = new RectF(); //保存的区域
    private RectF backgroundArea = new RectF(); //整个进度条区域
    private RectF proArea = new RectF(); //当前进度的区域
    private float progress = 0.7f; // 当前的进度
    // 默认整个进度条的宽高
    private int WIDTH = DisplayUtil.dip2px(getContext(), 300);
    private int HEIGHT = DisplayUtil.dip2px(getContext(), 40);
    // 背景色
    private int backgroundColor = getResources().getColor(R.color.background);
    // 进度条的颜色
    private int progressColor = getResources().getColor(R.color.progress);
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    // 换种写法，带有斜线的进度条
    private Path progressPath = new Path();
    // 斜线的偏移量
    private int offset = DisplayUtil.dip2px(getContext(), 4);

    // 加个动画
    private ObjectAnimator objectAnimator;
    // 是否使用动画
    private boolean useAnimator = false;

    // 变化率
    private float fraction = 0f;

    // 进度条的样式
    private ProgressStyle style = ProgressStyle.DEFAULT;

    public XProgressView(Context context) {
        this(context, null);
    }

    public XProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttrs(context, attrs);
    }

    @SuppressLint("ResourceAsColor")
    private void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XProgressView);
        this.progress = typedArray.getFloat(R.styleable.XProgressView_c_progress, 0);
        this.backgroundColor = typedArray.getColor(R.styleable.XProgressView_background_color, getResources().getColor(R.color.background));
        this.progressColor = typedArray.getColor(R.styleable.XProgressView_progress_color, getResources().getColor(R.color.progress));
        this.useAnimator = typedArray.getBoolean(R.styleable.XProgressView_animation_enabled, false);
        int type = typedArray.getInt(R.styleable.XProgressView_progress_style, 1);
        if (type == ProgressStyle.SLASH.type) {
            style = ProgressStyle.SLASH;
        } else {
            style = ProgressStyle.DEFAULT;
        }
        typedArray.recycle();
    }


    private void init() {
        saveArea.set(0, 0, WIDTH, HEIGHT);
        backgroundArea.set(0, 0, WIDTH, HEIGHT);
        proArea.set(0, 0, WIDTH * progress, HEIGHT);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = getWidth();
        HEIGHT = getHeight();
        init();
        if (useAnimator) {
            postDelayed(() -> getAnimator().start(), 500);
            fraction = 0f;
        } else {
            fraction = 1.0f;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(saveArea, 100, 100, paint);
        int saved = canvas.saveLayer(saveArea, paint);

        canvas.drawRoundRect(saveArea, 100, 100, paint);

        paint.setXfermode(xfermode);
        drawBackground(canvas);
        switch (style) {
            case DEFAULT:
                drawProgress(canvas);
                break;
            case SLASH:
                drawProgressX(canvas);
                break;
        }
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }


    /**
     * 绘制当前进度 斜线类型的
     */
    private void drawProgressX(Canvas canvas) {

        progressPath.reset();
        progressPath.lineTo(0, 0);

        progressPath.lineTo((WIDTH * progress + offset) * fraction, 0);
        progressPath.lineTo((WIDTH * progress - offset) * fraction, HEIGHT);
        progressPath.lineTo(0, HEIGHT);
        progressPath.close();
        paint.setColor(progressColor);
        canvas.drawPath(progressPath, paint);
    }


    /**
     * 绘制当前进度
     */
    private void drawProgress(Canvas canvas) {

        proArea.set(0, 0, WIDTH * progress * fraction, HEIGHT);
        paint.setColor(progressColor);
        canvas.drawRect(proArea, paint);
    }

    /**
     * 绘制整个进度条的背景
     */
    private void drawBackground(Canvas canvas) {
        paint.setColor(backgroundColor);
        canvas.drawRect(backgroundArea, paint);
    }


    /**
     * 添加属性动画
     */
    private ObjectAnimator getAnimator() {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "fraction", 0f, 1.0f);
        }
        return objectAnimator;
    }

    public float getFraction() {
        return fraction;
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }

    //-------------------------对外暴露方法，设置属性----------------------//

    /**
     * 设置进度 范围 0～1
     */
    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * 设置背景色
     *
     * @param color
     */
    public void setBackgroundColor(int color) {
        backgroundColor = color;
        invalidate();
    }


    /**
     * 设置进度条的颜色
     *
     * @param color
     */
    public void setProgressColor(int color) {
        progressColor = color;
        invalidate();
    }

    /**
     * 是否使用动画
     *
     * @param enable
     */
    public void enableAnimator(boolean enable) {
        this.useAnimator = enable;
    }

    /**
     * 设置进度条的样式
     */
    public void setProgressStyle(ProgressStyle style) {
        this.style = style;
    }

    /**
     * 进度条类型
     */
    public enum ProgressStyle {

        DEFAULT(1, "默认直线类型"),
        SLASH(2, "斜线类型"),
        ;

        int type;
        String description;

        ProgressStyle(int type, String description) {
            this.type = type;
            this.description = description;
        }

    }

}


