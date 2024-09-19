package com.nkee.kotlinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nkee.kotlinflow.ui.theme.KotlinFlowTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentClickRate = viewModel.currentClickRate.collectAsState()
            val currentCpS = viewModel.currentClickPerSecond.collectAsState()
            val currentUpgradeCostCR = viewModel.upgradeCostCR.collectAsState()
            val currentUpgradeCostCpS = viewModel.upgradeCostCps.collectAsState()
            KotlinFlowTheme {
                val viewModel = viewModel<MainViewModel>()
                val count = viewModel.currentScore.collectAsState()
                Box ( modifier = Modifier.fillMaxSize()) {
                    Button(
                        onClick = { viewModel.onCircleClick() },
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                            .height(200.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = "${count.value.toInt()}",
                            fontSize = 30.sp
                        )
                    }
                }
                Column (modifier = Modifier.fillMaxSize()) {
                    Row (modifier = Modifier
                        .padding(8.dp)) {
                        Text(
                            text = "Click Rate: ${currentClickRate.value}",
                            modifier = Modifier.padding(8.dp).weight(1f),
                            textAlign = TextAlign.Center

                        )
                        Text(
                            text = "CpS: ${currentCpS.value}",
                            modifier = Modifier.padding(8.dp).weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                    Row (modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                    ) {
                        Button(onClick = { viewModel.buyABonusToClickRate() },
                            modifier = Modifier.weight(1f)) {
                            Column {
                                Text("Upgrade")
                                Text("(${currentUpgradeCostCR.value.toInt()}$)")
                            }
                        }
                        Button(onClick = { viewModel.buyABonusToCpS() },
                            modifier = Modifier.weight(1f)) {
                            Column {
                                Text("Upgrade")
                                Text("(${currentUpgradeCostCpS.value.toInt()}$)")
                            }
                        }
                    }
                }
            }
        }
    }
}