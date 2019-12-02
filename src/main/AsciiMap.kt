package main


class AsciiMap(asciiMap: String) {

    val items: List<AsciiMapItem> = AsciiMapFormatter.formatAsciiMapItems(asciiMap)

    fun getOutput(): AsciiMapOutput {
        return AsciiMapOutput("", "")
    }
}