package com.example.sudokogame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var gameBoard: SudokuBoard
    private lateinit var gameBoardSolver:Solver
    private lateinit var solveBtn :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameBoard = findViewById(R.id.sudoku_board)
        gameBoardSolver = gameBoard.getSolver()
        solveBtn = findViewById(R.id.solveButton)

    }


    fun btnOnePress(view:View){
        gameBoardSolver.setNumberPosition(1)
        gameBoard.invalidate()
    }

    fun btnTwoPress(view:View){
        gameBoardSolver.setNumberPosition(2)
        gameBoard.invalidate()
    }

    fun btnThreePress(view:View){
        gameBoardSolver.setNumberPosition(3)
        gameBoard.invalidate()
    }

    fun btnFourPress(view:View){
        gameBoardSolver.setNumberPosition(4)
        gameBoard.invalidate()
    }

    fun btnFivePress(view:View){
        gameBoardSolver.setNumberPosition(5)
        gameBoard.invalidate()
    }

    fun btnSixPress(view:View){
        gameBoardSolver.setNumberPosition(6)
        gameBoard.invalidate()
    }


    fun btnSevenPress(view:View){
        gameBoardSolver.setNumberPosition(7)
        gameBoard.invalidate()
    }


    fun btnEightPress(view:View){
        gameBoardSolver.setNumberPosition(8)
        gameBoard.invalidate()
    }


    fun btnNinePress(view:View){
        gameBoardSolver.setNumberPosition(9)
        gameBoard.invalidate()
    }

    fun solve(view: View) {
        if (solveBtn.text.toString() == getString(R.string.solve)) {
            solveBtn.text = getString(R.string.clear)

            gameBoardSolver.getEmptyBoxIndexs()

            val solveBoardThread = SolveBoardThread()

            Thread(solveBoardThread).start()
            gameBoard.invalidate()

        } else {
            solveBtn.text = getString(R.string.solve)
            gameBoardSolver.resetBoard()
            gameBoard.invalidate()
        }
    }

    inner class SolveBoardThread:Runnable{
        override fun run() {
            gameBoardSolver.solve(gameBoard)
        }

    }

}