package com.example.exercise802_catagentforegroundservice.worker

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class CatLitterBoxSittingWorker(context: Context, workerParams: WorkerParameters):
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val catAgentId = inputData.getString(INPUT_DATA_CAT_AGENT_ID)
        Thread.sleep(3000)
        val outputData = Data.Builder()
            .putString(OUTPUT_DATA_CAT_AGENT_ID, catAgentId)
            .build()
        return Result.success(outputData)
    }

    companion object {
        const val INPUT_DATA_CAT_AGENT_ID = "inId"
        const val OUTPUT_DATA_CAT_AGENT_ID = "outId"
    }
}