import react.*
import react.dom.*
import kotlinext.js.*
import kotlinx.html.js.*
import kotlinx.coroutines.*

private val scope = MainScope()

val app = fc<Props> { _ ->
    var shoppingList by useState(emptyList<ShoppingListItem>())

    useEffectOnce {
        scope.launch {
            shoppingList = getShoppingList()
        }
    }

    ul(classes = "list-group"){
        li(classes = "list-group-item") {
            h2 {
                +"Kotlin Full-stack Tutorial"
            }
            div(classes = "list-group") {
                button(classes = "list-group-item list-group-item-action active") {
                    +"Shopping List"
                }
                shoppingList.sortedByDescending(ShoppingListItem::priority).forEach { item ->
                    button(classes = "list-group-item list-group-item-action") {
                        key = item.toString()
                        attrs.onClickFunction = {
                            scope.launch {
                                deleteShoppingListItem(item)
                                shoppingList = getShoppingList()
                            }
                        }
                        attrs.set("data-bs-toggle", "tooltip")
                        attrs.set("title", "Click to remove the item")
                        +"[${item.priority}] ${item.description} "
                    }
                }
            }
        }
        li(classes = "list-group-item"){
            a {
                + "Input the item here. Ex: Apple!!! -> translates to Apple with priority 3"
            }
            child(inputComponent) {
                attrs.onSubmit = { input ->
                    val cartItem = ShoppingListItem(input.replace("!", ""), input.count { it == '!' })
                    scope.launch {
                        addShoppingListItem(cartItem)
                        shoppingList = getShoppingList()
                    }
                }
            }
        }
    }
}