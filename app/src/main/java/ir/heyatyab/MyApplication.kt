package ir.heyatyab

import android.app.Application
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.usecase.GetEventListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    private val TAG = "MyApplication"

    override fun onCreate() {
        super.onCreate()

    }
}