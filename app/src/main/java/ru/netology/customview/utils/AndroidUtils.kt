package ru.netology.customview.utils

import android.content.Context
import kotlin.math.ceil // округление

object AndroidUtils {

    // функция пересчета dp в пиксели
    // эту функцию будем использовать для расчета размеров
    fun dp(context: Context, dp: Int): Int =
        ceil(context.resources.displayMetrics.density * dp).toInt()

}