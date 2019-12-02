package main


object AsciiMapFormatter {

    const val EMPTY_INPUT_ERROR_MESSAGE = "Input cannot be empty"

    fun formatAsciiMapItems(asciiMap: String): List<AsciiMapItem> {
        val rowsWithData = removeBlankRows(asciiMap)
        val asciiMapItems = mutableListOf<AsciiMapItem>()
        if (rowsWithData.isEmpty()) throw Exception(EMPTY_INPUT_ERROR_MESSAGE)
        else {
            val rowsWithDataWithoutEmptyLeadingColumns = removeEmptyLeadingColumns(rowsWithData)
            val rowsWithAddedTrailingSpacesToMatchLongestRow = addTrailingSpacesToMatchLongestRow(rowsWithDataWithoutEmptyLeadingColumns)
            asciiMapItems.addAll(createAsciiMapItems(rowsWithAddedTrailingSpacesToMatchLongestRow))
        }
        return asciiMapItems
    }

    private fun removeBlankRows(asciiMap: String) = asciiMap.lines().filter { it.isNotBlank() }

    private fun removeEmptyLeadingColumns(rows: List<String>): List<String> {
        var firstNotEmptyColumnIndex = rows[0].indexOfFirst { it.toString().isNotBlank() }
        rows.forEach { row ->
            val indexOfFirstNotBlankInRow = row.indexOfFirst { it.toString().isNotBlank() }
            if (indexOfFirstNotBlankInRow < firstNotEmptyColumnIndex) firstNotEmptyColumnIndex = indexOfFirstNotBlankInRow
        }
        return rows.map { it.removeRange(0, firstNotEmptyColumnIndex) }
    }

    private fun addTrailingSpacesToMatchLongestRow(rows: List<String>): List<String> {
        return rows.map { row -> row.padEnd(longestRowLength(rows)) }
    }

    private fun createAsciiMapItems(rows: List<String>): List<AsciiMapItem> {
        val numberOfRows = rows.size
        val numberOfColumns = longestRowLength(rows)
        val asciiMapItems = mutableListOf<AsciiMapItem>()
        for (rowIndex in 0 until numberOfRows) {
            val currentRow = rows[rowIndex]
            for (columnIndex in 0 until numberOfColumns) {
                val currentCharacter = currentRow[columnIndex]
                asciiMapItems.add(AsciiMapItem(currentCharacter.toString(), rowIndex, columnIndex))
            }
        }
        return asciiMapItems
    }

    private fun longestRowLength(rows: List<String>) =
            rows.maxBy { it.length }!!.trimEnd().length
}