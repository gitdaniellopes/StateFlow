package daniellopes.io.stateflowt.util

sealed class Resource{
    object Success: Resource()
    object Error: Resource()
    object Loading: Resource()
    object Initial: Resource()
}
