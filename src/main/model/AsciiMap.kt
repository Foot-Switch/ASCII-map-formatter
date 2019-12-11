package model

import viewModel.AsciiMapNavigator

class AsciiMap(private val asciiMapInput: String) {

    fun getOutput(): AsciiMapOutput {
        val mapNavigator = AsciiMapNavigator(asciiMapInput)
        return mapNavigator.buildOutput()
    }
}