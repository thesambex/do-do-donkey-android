package com.github.thesambex.dododonkey.domain.artists


data class Artist(
    val id: Long,
    val name: String
) {
    /**
     * Check if artist returned by content resolver is unknown
     */
    fun isUnknown() = name == "<unknown>"
}
