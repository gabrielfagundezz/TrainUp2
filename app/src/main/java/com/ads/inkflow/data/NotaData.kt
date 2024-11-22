package com.ads.inkflow.data

import com.ads.inkflow.model.Nota

class NotasDataSource {
    fun carregarNotas(): List<Nota>{
        return listOf(
            Nota(titulo = "Nota exemplo", descricao = "Descrição exemplo"),
            Nota(titulo = "Nota exemplo", descricao = "Descrição exemplo"),
            Nota(titulo = "Nota exemplo", descricao = "Descrição exemplo"),
            Nota(titulo = "Nota exemplo", descricao = "Descrição exemplo"),
            Nota(titulo = "Nota exemplo", descricao = "Descrição exemplo")

        )
    }
}