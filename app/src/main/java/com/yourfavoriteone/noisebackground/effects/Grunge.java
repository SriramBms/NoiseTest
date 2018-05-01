package com.yourfavoriteone.noisebackground.effects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.yourfavoriteone.noisebackground.R;

import no.agens.depth.lib.MathHelper;
import no.agens.depth.lib.headers.NoiseEffect;
import no.agens.depth.lib.headers.Renderable;
import no.agens.depth.lib.tween.FrameRateCounter;

/**
 * Created by Sriram S on 4/30/2018.
 */
public class Grunge extends View {
    public static final int WIND_RANDOMIZE_INTERVAL = 300;
    int index = 0;
    private float wind = 10f;
    float windRanomizerTarget;
    long lastWindRandomChange;
    float windRanomizerEased;
    private Renderable[] renderables;

    public Grunge(Context context) {
        super(context);

    }

    public Grunge(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Grunge(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (renderables == null) {
            init();
        }
    }

    private void init() {
        renderables = new Renderable[1];
        Bitmap grunge = BitmapFactory.decodeResource(getResources(), R.drawable.grunge);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        addGrunge(grunge);
    }

    private void addGrunge(Bitmap grunge) {
        NoiseEffect noise = new NoiseEffect(grunge, 100, 0.75f);
        renderables[index] = noise;
        noise.setNoiseIntensity(0.25f);
        index += 1;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destroyResources();
    }

    private void destroyResources() {

        for (Renderable renderable: renderables) {
            renderable.pause();
            renderable.destroy();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (renderables == null && getWidth() != 0)
            init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float deltaTime = FrameRateCounter.timeStep();
        if (lastWindRandomChange + WIND_RANDOMIZE_INTERVAL < System.currentTimeMillis()) {
            lastWindRandomChange = System.currentTimeMillis();
            float randomSpeedInterval = Math.max(wind / 2, 1);
            windRanomizerTarget = (float) MathHelper.rand.nextInt((int) randomSpeedInterval) - randomSpeedInterval / 2f;
        }
        windRanomizerEased += ((windRanomizerTarget - windRanomizerEased) * 4f) * deltaTime;
        for (Renderable renderable : renderables) {
            renderable.draw(canvas);
            renderable.update(deltaTime, wind + windRanomizerEased);
        }
        invalidate();
    }


}

