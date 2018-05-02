package com.yourfavoriteone.noisebackground

import android.graphics.Typeface
import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import com.yourfavoriteone.noisebackground.effects.Grunge
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*



class MainActivity : AppCompatActivity() {
    val FONTS_DIRECTORY = "fonts"
    val FONT_NAME = "cursivesweet.ttf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        val animationDrawable = GrungeBg.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()
        val typeface = Typeface.createFromAsset(assets, "$FONTS_DIRECTORY/$FONT_NAME")
        findViewById<TextView>(R.id.title).typeface = typeface
    }
}
