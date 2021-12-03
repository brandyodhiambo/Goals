package com.kanyiakinyidevelopers.goals.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanyiakinyidevelopers.goals.data.MainRepository
import com.kanyiakinyidevelopers.goals.models.Goal
import com.kanyiakinyidevelopers.goals.utils.Event
import com.kanyiakinyidevelopers.goals.utils.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _goalsStatus = MutableLiveData<Resource<List<Goal>>>()
    val goalsStatus: LiveData<Resource<List<Goal>>> = _goalsStatus

    private val _achievedGoalsStatus = MutableLiveData<Resource<List<Goal>>>()
    val achievedGoalsStatus: LiveData<Resource<List<Goal>>> = _achievedGoalsStatus

    private val _addGoalStatus = MutableLiveData<Event<Resource<Any>>>()
    val addGoalStatus: LiveData<Event<Resource<Any>>> = _addGoalStatus

    init {
        getGoals()
        getAchievedGoals()
    }


    fun addGoal(goalTitle: String, goalDescription: String, goalColor: String) {
        val error = if (goalTitle.isEmpty() || goalDescription.isEmpty()) {
            "Empty strings"
        } else null

        error?.let {
            _addGoalStatus.postValue(Event(Resource.Error(it)))
            return
        }
        _addGoalStatus.postValue(Event(Resource.Loading()))

        viewModelScope.launch(Dispatchers.Main) {
            val result = mainRepository.addGoal(goalTitle, goalDescription, goalColor)
            _addGoalStatus.postValue(Event(result))
        }
    }

    fun getGoals() {
        _goalsStatus.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = mainRepository.getGoals()
            _goalsStatus.postValue(result)
        }
    }

    fun getAchievedGoals() {
        _achievedGoalsStatus.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = mainRepository.getAchievedGoals()
            _achievedGoalsStatus.postValue(result)
        }
    }
}