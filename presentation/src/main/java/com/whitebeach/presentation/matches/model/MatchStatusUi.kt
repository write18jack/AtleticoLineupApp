package com.whitebeach.presentation.matches.model

enum class MatchStatusUi(
    val sectionTitle: String,
) {
    FINISHED(
        sectionTitle = "Finished Matches",
    ),
    UPCOMING(
        sectionTitle = "Upcoming Matches",
    ),
    UNKNOWN(
        sectionTitle = "Other Matches",
    ),
}