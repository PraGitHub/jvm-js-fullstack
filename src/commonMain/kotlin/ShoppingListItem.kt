import io.ktor.util.date.*
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItem(val description: String, val priority: Int) {
//    val id: Int = description.hashCode()
    val id: Int = "${description}${priority}${getTimeMillis()}".hashCode()

    companion object {
        const val path = "/shoppingList"
    }
}