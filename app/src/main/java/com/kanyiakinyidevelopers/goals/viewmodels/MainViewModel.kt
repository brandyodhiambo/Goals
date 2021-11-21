package com.kanyiakinyidevelopers.goals.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanyiakinyidevelopers.goals.data.MainRepository
import com.kanyiakinyidevelopers.goals.utils.Event
import com.kanyiakinyidevelopers.goals.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _addGoalStatus = MutableLiveData<Event<Resource<Any>>>()
    val addGoalStatus: LiveData<Event<Resource<Any>>> = _addGoalStatus

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
}