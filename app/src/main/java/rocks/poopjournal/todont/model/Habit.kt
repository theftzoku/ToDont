package rocks.poopjournal.todont.model

data class Habit(
    val id: Int? = null,
    val date: String,
    val name: String,
    val description: String?,
    var countAvoided: Int,
    var countDone: Int,
    val labelId: Int
){
    var label:Label? = null
}


