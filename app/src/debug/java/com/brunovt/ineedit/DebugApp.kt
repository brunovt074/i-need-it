package com.brunovt.ineedit

import com.brunovt.ineedit.data.SeedData
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DebugSeedEntryPoint {
    val seedData: SeedData
}

class DebugApp : App() {

    override fun onAppCreate() {
        val entryPoint = EntryPointAccessors.fromApplication(this, DebugSeedEntryPoint::class.java)
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
            entryPoint.seedData.seedIfNeeded()
        }
    }
}
