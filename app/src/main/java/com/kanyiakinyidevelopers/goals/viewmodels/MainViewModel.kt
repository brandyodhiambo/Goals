package com.kanyiakinyidevelopers.goals.viewmodels

import androidx.lifecycle.ViewModel
import com.kanyiakinyidevelopers.goals.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {
}