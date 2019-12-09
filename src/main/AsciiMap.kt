package main

class AsciiMap(private val asciiMapInput: String) {

    fun getOutput(): AsciiMapOutput {
        AsciiMapNavigator.assignMap(asciiMapInput)
        return AsciiMapNavigator.buildOutput()
    }
}