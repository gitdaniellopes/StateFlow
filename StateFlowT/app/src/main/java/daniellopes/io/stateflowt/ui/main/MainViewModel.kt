package daniellopes.io.stateflowt.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import daniellopes.io.stateflowt.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<Resource>(Resource.Initial)
    val uiStateFlow: StateFlow<Resource> get() = _uiStateFlow

    private var errorControl: Boolean = false

    fun getSomething() = viewModelScope.launch {
        _uiStateFlow.value = Resource.Loading
        delay(3000)
        errorControl = Random.nextBoolean()

        _uiStateFlow.value = if (errorControl) {
            Resource.Error
        } else {
            Resource.Success
        }
    }


}