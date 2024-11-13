package com.example.gs_kotlyn.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gs_kotlyn.model.DicaModel


@Database(entities = [DicaModel::class], version = 1)
abstract class DicaDatabase : RoomDatabase() {

abstract fun DicaDao(): DicaDao

}