package com.example.studymatetwo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.studymatetwo.repository.MentorSearchRepository
import javax.inject.Inject

class MentorSearchViewModel @Inject constructor(private val repository: MentorSearchRepository) : ViewModel() {

}