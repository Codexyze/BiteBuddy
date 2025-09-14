
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class Food(
    @PrimaryKey val id: Int = 0,
    val type: String? = null,
    val foodname: String,
    val pergram: Double? = null,
    val calories: Double? = null,
    val protein: Double? = null,
    val calcium: Double? = null,
    val iron: Double? = null,
    val magnesium: Double? = null,
    val vitA: Double? = null,
    val vitB12: Double? = null,
    val vitC: Double? = null,
    val vitD: Double? = null,
    val safeInPregnancy: Boolean = false,
    val menstrualSafe: Boolean = false,
    val femaleImportant: Boolean = false,
    val maleImportant: Boolean = false
)
