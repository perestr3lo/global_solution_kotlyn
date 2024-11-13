package com.example.gs_kotlyn.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DicaModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val description: String

)



