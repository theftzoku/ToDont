package rocks.poopjournal.todont.model

import rocks.poopjournal.todont.utils.HabitStatus

data class HabitRecord (
    val id: Int? = null,
    val date: String,
    val status: String,
    val habitId: Int?,
)

