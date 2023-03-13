package com.example.sudokogame

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.roundToInt

class SudokuBoard(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var boardColor:Int? = null
    private var cellFillColor:Int? = null
    private var cellsHighLightColor:Int? = null
    private var cellSize:Int? = null
    private var letterColor:Int? = null
    private var letterColorSolve:Int? = null

    private var boardColorPaint:Paint = Paint()
    private var cellFillColorPaint:Paint = Paint()
    private var cellsHighLightColorPaint:Paint = Paint()
    private var letterPaint:Paint = Paint()


    private var letterPaintBounds:Rect = Rect()

    private val solver = Solver()





    val a:TypedArray = context.theme.obtainStyledAttributes(attrs,R.styleable.SudokuBoard,
        0,0)

    init {
        try {
            boardColor = a.getInteger(R.styleable.SudokuBoard_boardColor,0)
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor,0)
            cellsHighLightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighLightColor,0)
            letterColor= a.getInteger(R.styleable.SudokuBoard_letterColor,0)
            letterColorSolve = a.getInteger(R.styleable.SudokuBoard_letterColorSolve,0)
        }finally {
            a.recycle()
        }
    }

    override fun onMeasure(width: Int, height: Int) {
        super.onMeasure(width, height)

        val dimension = min(this.measuredWidth,this.measuredHeight)
        cellSize = dimension/9
        setMeasuredDimension(dimension,dimension)
    }

    override fun onDraw(canvas: Canvas?) {
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 16f
        boardColor?.let { boardColorPaint.color = it }
        boardColorPaint.isAntiAlias = true

        cellFillColorPaint.style = Paint.Style.FILL
        cellFillColorPaint.isAntiAlias = true
        cellFillColor?.let { cellFillColorPaint.color = it }

        cellsHighLightColorPaint.style = Paint.Style.FILL
        cellsHighLightColorPaint.isAntiAlias = true
        cellsHighLightColor?.let { cellsHighLightColorPaint.color = it }

        letterPaint.style = Paint.Style.FILL
        letterPaint.isAntiAlias = true
        letterPaint.color = letterColor!!


        coloCell(canvas, solver.getSelectedRow()!!, solver.getSelectedColumn()!!)
        canvas?.drawRect(0F, 0F, width.toFloat(), height.toFloat(),boardColorPaint)
        drawBoard(canvas)
        drawNumbers(canvas)

    }

    private fun drawThickLine(){
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 10f
        boardColor?.let { boardColorPaint.color = it }
    }

    private fun drawThinLine(){
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 4f
        boardColor?.let { boardColorPaint.color = it }
    }

    private fun drawBoard(canvas: Canvas?){
        for (c in 0 until 10) {
            if (c % 3 == 0) {
                drawThickLine()
            } else {
                drawThinLine()
            }
            canvas?.drawLine((cellSize!! * c).toFloat(), 0F,
                (cellSize!! * c).toFloat(), width.toFloat(),boardColorPaint)
        }

        for (r in 0 until 10) {
            if (r % 3 == 0) {
                drawThickLine()
            } else {
                drawThinLine()
            }
            canvas?.drawLine(0F, (cellSize!! * r).toFloat(),
                width.toFloat(), (cellSize!! * r).toFloat(),boardColorPaint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var isValid:Boolean? =null

        val x:Float = event!!.x
        val y:Float = event.y

        val action = event.action

        if (action == MotionEvent.ACTION_DOWN){
            solver.setSelectedRow(ceil((y/ cellSize!!).toDouble()).roundToInt())
            solver.setSelectedColumn(ceil((x/ cellSize!!).toDouble()).roundToInt())
            isValid = true
        }else{
            isValid = false
        }


        return isValid
    }

    private fun coloCell(canvas: Canvas?, row:Int, column:Int,){

        if (solver.getSelectedColumn() != -1 && solver.getSelectedRow() != -1){

            canvas?.drawRect(((column-1)* cellSize!!).toFloat(), 0f ,
                (column * cellSize!!).toFloat(), (cellSize!! * 9).toFloat(),cellsHighLightColorPaint)

            canvas?.drawRect(0f, ((row-1)* cellSize!!).toFloat() ,
                (cellSize!! * 9).toFloat(), (row * cellSize!!).toFloat(),cellsHighLightColorPaint)

            canvas?.drawRect(((column-1)* cellSize!!).toFloat(), ((row-1)* cellSize!!).toFloat() ,
                (column * cellSize!!).toFloat(), (row * cellSize!!).toFloat(),cellsHighLightColorPaint)
        }

        invalidate()

    }

    private fun drawNumbers(canvas: Canvas?) {
        letterPaint.textSize = cellSize!!.toFloat()
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                if (solver.getBoard()[r][c] != 0) {
                    val text: String = solver.getBoard()[r][c].toString()
                    var width: Float? = null
                    var height: Float? = null

                    letterPaint.getTextBounds(text, 0, text.length, letterPaintBounds)
                    width = letterPaint.measureText(text)
                    height = letterPaintBounds.height().toFloat()

                    canvas!!.drawText(
                        text, (c * cellSize!!) + ((cellSize!! - width) / 2),
                        (r * cellSize!! + cellSize!!) - ((cellSize!! - height) / 2), letterPaint
                    )

                }
            }
        }

        letterPaint.color = letterColorSolve!!
        for (letter in solver.getEmptyBoxIndex()) {
            val r = letter[0]
            val c = letter[1]

            val text = solver.getBoard()[r][c].toString()

            var width: Float? = null
            var height: Float? = null

            letterPaint.getTextBounds(text, 0, text.length, letterPaintBounds)
            width = letterPaint.measureText(text)
            height = letterPaintBounds.height().toFloat()

            canvas?.drawText(
                text, (c * cellSize!!) + ((cellSize!! - width) / 2),
                (r * cellSize!! + cellSize!!) - ((cellSize!! - height) / 2), letterPaint
            )
        }

    }
    fun getSolver():Solver{
        return this.solver
    }
}
