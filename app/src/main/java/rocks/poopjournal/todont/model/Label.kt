package rocks.poopjournal.todont.model

data class Label (
    var labelId: Int? = null,
    var name: String? = null,
){
    var habitCount: Int = 0
    override fun toString(): String {
        return name!!
    }
}

