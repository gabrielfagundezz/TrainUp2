package com.ads.inkflow.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "notas_tbl")
data class Nota(
    @PrimaryKey val id: String = UUID.randomUUID().toString(), // ID como String
    val titulo: String? = null,
    val descricao: String? = null,
    val data: Long? = null
)
 {
    @JvmOverloads
    constructor() : this(id = UUID.randomUUID().toString(), titulo = null, descricao = null, data = null)

}
