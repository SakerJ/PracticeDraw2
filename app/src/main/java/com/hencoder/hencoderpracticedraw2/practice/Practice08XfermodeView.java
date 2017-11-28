package com.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw2.R;

public class Practice08XfermodeView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap1;
    Bitmap bitmap2;

    public Practice08XfermodeView(Context context) {
        super(context);
    }

    public Practice08XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice08XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private PorterDuffXfermode mXfermodeSrc;
    private PorterDuffXfermode mXfermodeDstIn;
    private PorterDuffXfermode mXfermodeDstOut;

    {
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo);

        mXfermodeSrc = new PorterDuffXfermode(PorterDuff.Mode.SRC);
        mXfermodeDstIn = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mXfermodeDstOut = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 paint.setXfermode() 设置不同的结合绘制效果

        // 别忘了用 canvas.saveLayer() 开启 off-screen buffer
        /*
        off-screen buffer的步骤：
         1. saveLayer()后的绘制操作会在缓冲区执行
         2. 绘制完成后需要清空Xfermode
         3. 再用restoreToCount()将缓冲区的内容绘制到canvas
          */
        int saveLayer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bitmap1, 0, 0, paint);
        // 第一个：PorterDuff.Mode.SRC
        paint.setXfermode(mXfermodeSrc);
        canvas.drawBitmap(bitmap2, 0, 0, paint);
        paint.setXfermode(null);// 清空Xfermode
        canvas.restoreToCount(saveLayer);

        saveLayer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bitmap1, bitmap1.getWidth() + 100, 0, paint);
        // 第二个：PorterDuff.Mode.DST_IN
        paint.setXfermode(mXfermodeDstIn);
        canvas.drawBitmap(bitmap2, bitmap1.getWidth() + 100, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveLayer);

        saveLayer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bitmap1, 0, bitmap1.getHeight() + 20, paint);
        // 第三个：PorterDuff.Mode.DST_OUT
        paint.setXfermode(mXfermodeDstOut);
        canvas.drawBitmap(bitmap2, 0, bitmap1.getHeight() + 20, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveLayer);

        // 用完之后使用 canvas.restore() 恢复 off-screen buffer
    }
}
