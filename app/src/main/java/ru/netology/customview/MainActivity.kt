package ru.netology.customview

import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import ru.netology.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // заполним данными наше view
        /*
        binding.statsView.data = listOf(
            0.25F,
            0.25F,
            0.25F,
            0.25F,
        )*/

        val view = binding.statsView
        view.data = listOf(
            500F,
            500F,
            500F,
            500F,
        )



/*
        view.animate()
            .rotation(360F)
            .scaleX(1.2F)
            .scaleY(1.2F)
            .setInterpolator(LinearInterpolator())
            .setStartDelay(500)
            .setDuration(3000)
            .start()
*/

        // получим доступ к информации анимации anim/animation.xml
        // Пример 1 android.view.animation
        /*
        val textView = binding.label
        view.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.animation).apply {
                // у нас есть возможность прослеживать прогресс анимации
                // для этого добавим слушателя
                setAnimationListener(object : Animation.AnimationListener {
                    @SuppressLint("SetTextI18n")
                    override fun onAnimationStart(animation: Animation?) {
                        textView.text = "on Animation Start"
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onAnimationEnd(animation: Animation?) {
                        textView.text = "on Animation End"
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onAnimationRepeat(animation: Animation?) {
                        textView.text = "on Animation Repeat"
                    }

                })
            }
        )
*/
        // Конец примера 1

        // Пример 2 ObjectAnimator через рефлексию
        /*
        ObjectAnimator.ofFloat(view, "alpha", 0.25F, 1F).apply {
            startDelay = 500
            duration = 3000
            interpolator = BounceInterpolator()
        }.start()
        */
        // Конец примера 2

        // Пример 3 ObjectAnimator через готовые property
        /*
        ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply {
            startDelay = 500
            duration = 3000
            interpolator = BounceInterpolator()
        }.start()
        */
        // Конец примера 3


        // Пример 4 Анимация нескольких свойств через PropertyValuesHolder
        /*
        val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 0F, 360F)
        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
        ObjectAnimator.ofPropertyValuesHolder(view, rotation, alpha)
            .apply {
                startDelay = 500
                duration = 2000
                interpolator = LinearInterpolator()
            }.start()
        */
        // Конец примера 4

        // Пример 5 Использование ViewPropertyAnimator
        /*
        view.animate()
            .rotation(360F)
            .scaleX(1.2F)
            .scaleY(1.2F)
            .setInterpolator(LinearInterpolator())
            .setStartDelay(500)
            .setDuration(3000)
            .start()

         */
        // Конец примера 5

        // Пример 6 Комбинация нескольких анимаций через AnimatorSet
        /*
        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply {
            duration = 3000
            interpolator = LinearInterpolator()
        }
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F, 1F)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F, 1F)
        val scale = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY).apply {
            duration = 3000
            interpolator = BounceInterpolator()
        }
        AnimatorSet().apply {
            startDelay = 500
            playSequentially(scale, alpha)
        }.start()
        */
        // Конец примера 6


    }
}