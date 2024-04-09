package com.yasinmaden.improveyourposturein30days.model

import androidx.annotation.StringRes

data class Exercises(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @StringRes val videoUrlRes: Int
)
