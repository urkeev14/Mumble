package com.example.mumble.utils.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T : Any> LifecycleOwner.observeIn(
    lifecycleScope: LifecycleCoroutineScope,
    flow: Flow<T>,
    action: (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { item ->
                action(item)
            }
        }
    }
}
