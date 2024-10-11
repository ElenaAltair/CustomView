package ru.netology.customview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.withStyledAttributes
import ru.netology.customview.R
import ru.netology.customview.utils.AndroidUtils
import kotlin.math.min
import kotlin.random.Random

// этот класс мы будем использовать в вёрстке
class StatsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null, // набор аттрибутов, которые можно передать через xml
    defStyleAttr: Int = 0, // стиль атрибута по умолчанию
    defStyleRes: Int = 0, // стиль по умолчнию
) : View(
    context,
    attributeSet,
    defStyleAttr,
    defStyleRes,
) {

    // размер текста
    private var textSise = AndroidUtils.dp(context, 20).toFloat()

    // Ширина строки для кисти
    private var lineWith = AndroidUtils.dp(context, 5)

    private var colors = emptyList<Int>()

    private var sumAllValues = 0 // какое должно быть значение суммы всех элементов массива при 100%
    private var countSection = 4 // размер массива при 100%

    // обработаем атрибуты, которые мы указали в разметке нашей кастомной view
    init {
        context.withStyledAttributes(attributeSet, R.styleable.StatsView) {
            textSise = getDimension(R.styleable.StatsView_textSize, textSise)
            lineWith = getDimension(R.styleable.StatsView_lineWidth, lineWith.toFloat()).toInt()
            sumAllValues = getInt(R.styleable.StatsView_sumAllValues, sumAllValues)
            countSection = getInt(R.styleable.StatsView_countSection, countSection)
            colors = listOf(
                getColor(R.styleable.StatsView_color1, generateRandomColor()),
                getColor(R.styleable.StatsView_color2, generateRandomColor()),
                getColor(R.styleable.StatsView_color3, generateRandomColor()),
                getColor(R.styleable.StatsView_color4, generateRandomColor()),
            )
        }
    }

    // объявим данные
    // данные будут приходить в види списка чисел
    var data: List<Float> = emptyList()
        set(value) {

            // value.map { it / value.sum() } приведем значения к процентам
            field = value.map { it / sumAllValues } // value // обновим данные

            // переведем данные в
            invalidate() // invalidate() спровоцирует вызов функции onDraw
        }

    // чтобы использовать область отрисовки, создадим прямоугольник типа RectF
    private var oval = RectF()

    // расчитаем радиус
    // индикатор прогресса, который мы реализуем, должен быть в виде окружности
    private var radius = 0F

    // нам требуется точка центра окружности
    private var center = PointF()

    private var colorFirst = generateRandomColor()

    // Кисть
    private val paint = Paint(
        Paint.ANTI_ALIAS_FLAG // флаг, который отвечает за сглаживание
    ).apply {
        // настроим кисть
        // укажем ширину строки
        strokeWidth = lineWith.toFloat()
        // настроим стиль отрисовки (укажим, что отрисовываем именно строки)
        style = Paint.Style.STROKE
        // настроим округление краев при отрисовке
        // мы будем округлять концы линий и округлять их при пересечении
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    // кисть для отрисовки текста
    private val textPaint = Paint(
        Paint.ANTI_ALIAS_FLAG // флаг, который отвечает за сглаживание
    ).apply {
        // настроим кисть
        // размер текста
        textSize = this@StatsView.textSise
        // настроим стиль отрисовки (выберем стиль заливка)
        style = Paint.Style.FILL
        // укажем гравитацию текста (в центре)
        textAlign = Paint.Align.CENTER
    }

    // расчитываем размеры нашей view
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // w - ширина, h - высота
        // для получения радиуса, возьмём минимальное значение от ширины и высоты
        // и разделим его на 2
        // lineWith - добавим отступ, чтобы наша окружность, поместилась при отрисовке,
        // где 5 размер линии (5dp)
        radius = min(w, h) / 2F - lineWith

        // расчитаем центр окружности
        // w / 2F - координата x (т.е. ширина поделенная на 2)
        // h / 2F - координата y
        center = PointF(w / 2F, h / 2F)
        // заполним область отрисовки данными, при изменении размеров
        oval = RectF(
            center.x - radius, // размер левой грани
            center.y - radius, // расчитаем положение верхней грани
            center.x + radius, // расчитаем положение правой грани
            center.y + radius, // расчитаем положение нижней грани
        )
    }

    // переходим к отрисовке нашей view
    // Canvas - объект, который представляет собой холст, на котором можно рисовать
    // Paint() - объект, который представляем собой кисть
    override fun onDraw(canvas: Canvas) {
        // нарисуем круг
        // canvas.drawCircle(center.x, center.y, radius, paint)

        if (data.isEmpty()) {
            return // если список с данными пустой, но выходим
        }

        // определим переменную стартовый угол поворота
        // берем (- 90F), чтобы начинать отрисовку сверху
        var startAngle = -90F


        // обходим список элементов
        data.forEachIndexed { index, datum ->
            // и рассчитаем угол поворота каждого
            // умножим данные на 360, чтобы получить угол

            Log.d("MyLog", "datum: $datum, data.sum(): ${data.sum()} ")
            val angle = (datum / data.sum()) * (360 / countSection) * data.size
            // назначим каждому элементу свой цвет (воспользуемся генерацией случайного числа)
            // расчитываем случайное число, которое находится от черного до белого цвета


            paint.color = colors.getOrElse(index) { generateRandomColor() }
            if (index == 0) {
                colorFirst = paint.color
            }
            // отрисуем дугу
            canvas.drawArc(oval, startAngle, angle, false, paint)
            // добавим отступ к стартовому углу поворота, чтобы не рисовать на одном месте
            startAngle += angle
        }

        // исправим верхний хвостик (сделаем правильной отображение на 0 часов)
        val angle = ((data[0] / data.sum()) * (360 / countSection) * data.size)/2
        paint.color = colorFirst
        startAngle = -90F
        canvas.drawArc(oval, startAngle, angle, false, paint)

        // отрисуем текст
        canvas.drawText(
            // data.sum() складываем все цифры, что пришли и умножаем на 100,
            // чтобы получить проценты
            "%.2f%%".format(data.sum() * 100),
            // укажем положение текста на экране
            center.x,
            center.y + textPaint.textSize / 4,
            // кисть
            textPaint
        )

    }

    private fun generateRandomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())

}