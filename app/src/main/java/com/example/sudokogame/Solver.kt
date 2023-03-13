package com.example.sudokogame

class Solver {

    private var board = arrayOf<IntArray>()
    private var emptyBoxIndex = ArrayList<ArrayList<Int>>()



    private var selectedRow: Int? = null
    private var selectedColumn: Int? = null

    init {
        selectedColumn = -1
        selectedRow = -1
        board =Array(9) {IntArray(9)}

        for (r in 0 until 9) {
            for (c in 0 until 9) {
                board[r][c] = 0
            }

        }

        emptyBoxIndex = ArrayList()

    }

    fun getEmptyBoxIndexs() {
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                if (this.board[r][c] == 0) {
                    this.emptyBoxIndex.add(ArrayList())
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size -1).add(r)
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size -1).add(c)
                }
            }
        }

    }


    private fun check(row:Int,col:Int):Boolean{
        if(this.board[row][col] > 0){
            for (i in 0..8){
                if (this.board[i][col] == this.board[row][col] && row != i){
                    return false
                }
                if (this.board[row][i] == this.board[row][col] && col != i){
                    return false
                }
            }
            val boxRow:Int = row/3
            val boxCol:Int = col/3

            for (r in boxRow*3 until boxRow*3 + 3) {
                for (c in boxCol*3 until boxCol*3 + 3){
                    if (this.board[r][c] == this.board[row][col] && row != r && col != c){
                        return false
                    }
                }
            }
        }
        return true
    }



    fun solve(display:SudokuBoard):Boolean{
        var row :Int = -1
        var col :Int = -1

        for (r in 0 until 9){
            for (c in 0 until 9){
                if (this.board[r][c] == 0){
                    row = r
                    col = c
                    break
                }
            }
        }

        if (row == -1 || col == -1){
            return true
        }
        for (i in 1 until 10){
            this.board[row][col] = i
            display.invalidate()
            if (check(row,col)){
                if (solve(display)){
                    return true
                }
            }
            this.board[row][col] = 0
        }
        return false
    }

    fun resetBoard(){

        for (r in 0 until 9) {
            for (c in 0 until 9) {
                board[r][c] = 0
            }

        }
        this.emptyBoxIndex.clear()
    }


    fun setNumberPosition(num:Int){
        if (this.selectedRow != -1 && this.selectedColumn != -1){
            if (this.board[this.selectedRow!!-1][this.selectedColumn!!-1] == num ){
                this.board[this.selectedRow!!-1][this.selectedColumn!!-1] = 0
            }
            else{
                this.board[this.selectedRow!!-1][this.selectedColumn!!-1] = num
            }
        }
    }


    fun getBoard(): Array<IntArray> {
        return this.board
    }



    fun getEmptyBoxIndex(): ArrayList<ArrayList<Int>> {
        return this.emptyBoxIndex
    }

    fun getSelectedRow(): Int? {
        return selectedRow
    }

    fun getSelectedColumn(): Int? {
        return selectedColumn
    }

    fun setSelectedRow(row: Int) {
        selectedRow = row
    }

    fun setSelectedColumn(column: Int) {
        selectedColumn = column
    }
}
