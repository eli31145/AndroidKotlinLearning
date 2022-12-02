package com.example.exercise802_catagentforegroundservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.exercise802_catagentforegroundservice.RouteTrackingService.Companion.EXTRA_SECRET_CAT_AGENT_ID
import com.example.exercise802_catagentforegroundservice.worker.CatFurGroomingWorker
import com.example.exercise802_catagentforegroundservice.worker.CatLitterBoxSittingWorker
import com.example.exercise802_catagentforegroundservice.worker.CatStretchingWorker
import com.example.exercise802_catagentforegroundservice.worker.CatSuitUpWorker

class MainActivity : AppCompatActivity() {
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val networkConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val catAgentId = "CatAgent1"

        val catStretchingRequest =
            OneTimeWorkRequest.Builder(CatStretchingWorker::class.java)
                .setConstraints(networkConstraints)
                .setInputData(getCatAgentIdInputData(CatStretchingWorker.INPUT_DATA_CAT_AGENT_ID, catAgentId))
                .build()

        val catFurGroomingRequest =
            OneTimeWorkRequest.Builder(CatFurGroomingWorker::class.java)
                .setConstraints(networkConstraints)
                .setInputData(getCatAgentIdInputData(CatFurGroomingWorker.INPUT_DATA_CAT_AGENT_ID, catAgentId))
                .build()

        val catLitterBoxSittingRequest =
            OneTimeWorkRequest.Builder(CatLitterBoxSittingWorker::class.java)
                .setConstraints(networkConstraints)
                .setInputData(getCatAgentIdInputData(CatLitterBoxSittingWorker.INPUT_DATA_CAT_AGENT_ID, catAgentId))
                .build()

        val catSuitUpRequest =
            OneTimeWorkRequest.Builder(CatSuitUpWorker::class.java)
                .setConstraints(networkConstraints)
                .setInputData(getCatAgentIdInputData(CatSuitUpWorker.INPUT_DATA_CAT_AGENT_ID, catAgentId))
                .build()

        workManager.beginWith(catStretchingRequest)
            .then(catFurGroomingRequest)
            .then(catLitterBoxSittingRequest)
            .then(catSuitUpRequest)
            .enqueue()

        workManager.getWorkInfoByIdLiveData(catStretchingRequest.id)
            .observe(this, Observer{info ->
                if(info.state.isFinished) {
                    showResult("Agent done stretching")
                }
            })

        workManager.getWorkInfoByIdLiveData(catFurGroomingRequest.id)
            .observe(this, Observer { info ->
                if (info.state.isFinished) {
                    showResult("Agent done grooming its fur")
                }
            })

        workManager.getWorkInfoByIdLiveData(catLitterBoxSittingRequest.id)
            .observe(this, Observer { info ->
                if (info.state.isFinished) {
                    showResult("Agent done sitting in litter box")
                }
            })

        workManager.getWorkInfoByIdLiveData(catSuitUpRequest.id)
            .observe(this, Observer { info ->
                if (info.state.isFinished){
                    showResult("Agent done suiting up. Ready to go!")
                    //additional launch of service here
                    launchTrackingService()
                }
            })

    }

    private fun getCatAgentIdInputData(catAgentIdKey: String, catAgentIdValue: String) =
        Data.Builder().putString(catAgentIdKey, catAgentIdValue)
            .build()

    private fun showResult(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun launchTrackingService(){
        RouteTrackingService.trackingCompletion.observe(this, Observer {
            agentId -> showResult("Agent $agentId has arrived!")
        })
        val serviceIntent = Intent(this, RouteTrackingService::class.java).apply {
            putExtra(EXTRA_SECRET_CAT_AGENT_ID, "OO7")
        }
        ContextCompat.startForegroundService(this, serviceIntent)
    }

}