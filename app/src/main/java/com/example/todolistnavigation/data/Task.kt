package com.example.todolistnavigation.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Обьекты данных
@Entity(tableName = "tasks")
class Task {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "itemId")
    var id: Int = 0

    @ColumnInfo(name = "itemTask")
    var item: String =""

    @ColumnInfo(name = "is_checked")
    var chekItem: Boolean = false

    constructor() {}

    constructor(item: String, chekItem: Boolean) {
        this.item = item
        this.chekItem = chekItem
    }
}