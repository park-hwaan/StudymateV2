package com.example.studymatetwo.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MentorDto(
    val id: Int,
    val name: String,
    val nickname: String,
    val part: String,
    val email: String,
    val tel: String?,
    val expertiseField: String,
    val interests: String,
    val blogUrl: String,
    val publicRelations: String,
    val job: String,
    val heart: Int,
    val starAverage: Double,
    val solved: Int,
    val matchingCount: Int,
    val reviewCount: Int
) : Parcelable

data class MentorListDto(
    val memberList: List<MentorDto>
)
