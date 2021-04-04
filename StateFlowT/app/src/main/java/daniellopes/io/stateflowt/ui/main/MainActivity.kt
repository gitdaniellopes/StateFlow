package daniellopes.io.stateflowt.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import daniellopes.io.stateflowt.databinding.ActivityMainBinding
import daniellopes.io.stateflowt.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetSomething.setOnClickListener {
            viewModel.getSomething()
        }

        /*Primeira forma
        viewModel.uiStateFlow.asLiveData().observe(this, { result ->
            result?.let {
                when(result) {
                    Resource.Success -> {
                        binding.run {
                            progressBar.isVisible = false
                            txStateFlow.isVisible = true
                            binding.txStateFlow.text = "I am StateFlow"
                        }
                    }

                    Resource.Error -> {
                        binding.run {
                            progressBar.isVisible = false
                            txStateFlow.isVisible = true
                            binding.txStateFlow.text = "Error"
                        }
                    }

                    Resource.Loading -> {
                        binding.run {
                            progressBar.isVisible = true
                        }
                    }
                    Resource.Initial -> binding.progressBar.isVisible = false
                }
            }
        })
         */
    }

    // Segunda forma
    override fun onStart() {
        super.onStart()
        uiStateJob = lifecycleScope.launchWhenStarted {
            viewModel.uiStateFlow.collect { result ->
                when (result) {
                    Resource.Success -> {
                        binding.run {
                            progressBar.isVisible = false
                            txStateFlow.isVisible = true
                            binding.txStateFlow.text = "I am StateFlow"
                        }
                    }

                    Resource.Error -> {
                        binding.run {
                            progressBar.isVisible = false
                            txStateFlow.isVisible = true
                            binding.txStateFlow.text = "Error"
                        }
                    }

                    Resource.Loading -> {
                        binding.run {
                            progressBar.isVisible = true
                        }
                    }
                    Resource.Initial -> binding.progressBar.isVisible = false
                }
            }
        }
    }

    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
    }
}