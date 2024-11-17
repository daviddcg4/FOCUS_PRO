data class Goal(
    val id: Int,
    val title: String,
    var progress: Float, // Progreso en porcentaje (0.0 a 1.0)
    var isCompleted: Boolean = false
)
