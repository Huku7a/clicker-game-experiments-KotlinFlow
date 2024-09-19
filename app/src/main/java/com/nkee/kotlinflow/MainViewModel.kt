package com.nkee.kotlinflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.math.round
import kotlin.math.roundToLong

class MainViewModel : ViewModel() {

    private val _upgradeCostCps = MutableStateFlow(50f)
    val upgradeCostCps = _upgradeCostCps.asStateFlow()

    private val _upgradeCostCR = MutableStateFlow(50f)
    val upgradeCostCR = _upgradeCostCR.asStateFlow()

    private val _currentClickRate = MutableStateFlow(1f)
    val currentClickRate = _currentClickRate.asStateFlow()

    private val _currentClickPerSecond = MutableStateFlow(1f)
    val currentClickPerSecond = _currentClickPerSecond.asStateFlow()

    private val _currentScore = MutableStateFlow(0f)
    val currentScore = _currentScore.asStateFlow()

    fun onCircleClick() {
        _currentScore.value += _currentClickRate.value
    }

    private fun onClickPerSecondTick() {
        _currentScore.value += _currentClickPerSecond.value
    }

    fun buyABonusToClickRate(){
        if (_currentScore.value >= _upgradeCostCR.value) {
            _currentScore.value -= _upgradeCostCR.value
            _currentClickRate.value += 1
            _upgradeCostCR.value = round(_upgradeCostCR.value.times(1.03f))
        }
    }

    fun buyABonusToCpS() {
        if (_currentScore.value >= _upgradeCostCps.value) {
            _currentScore.value -= _upgradeCostCps.value
            _currentClickPerSecond.value += 1
            _upgradeCostCps.value = round(_upgradeCostCps.value.times(1.03f))
        }
    }

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                onClickPerSecondTick()
            }
        }
    }
}