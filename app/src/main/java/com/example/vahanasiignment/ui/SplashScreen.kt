package com.example.vahanasiignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.example.vahanasiignment.databinding.ActivitySplashScreenBinding
import com.example.vahanasiignment.ui.MainActivity

class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    private lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lottieAnimationView = binding.lottieanim

        // Start the Lottie animation
        lottieAnimationView.playAnimation()

        // Delay for a few seconds and then transition to the main activity
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
//        binding.lottieanim.addAnimatorListener(object : Animator.AnimatorListener{
//            override fun onAnimationStart(p0: Animator?) {
//
//            }
//
//            override fun onAnimationEnd(p0: Animator?) {
//                val intent = Intent(this@SplashScreen,MainActivity::class.java)
//                startActivity(intent)
//            }
//
//            override fun onAnimationCancel(p0: Animator?) {
//
//            }
//
//            override fun onAnimationRepeat(p0: Animator?) {
//
//            }
//        })

    /*android:exported="true">
    <intent-filter>
    <action android:name="android.intent.action.MAIN" />

    <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>*/

}