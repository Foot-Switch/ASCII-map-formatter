package view

import model.AsciiMapOutput
import viewModel.AsciiMapNavigator

class AsciiMap(private val asciiMapNavigator: AsciiMapNavigator) {

    fun getOutput(): AsciiMapOutput {
        return asciiMapNavigator.buildOutput()
    }

    fun printOutput() {
        asciiMapNavigator.printOutput()
    }
}