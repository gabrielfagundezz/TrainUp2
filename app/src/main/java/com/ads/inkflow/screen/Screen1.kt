package com.ads.inkflow.screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ads.inkflow.components.NoteCaixaTexto
import com.ads.inkflow.model.Nota
import com.ads.inkflow.ui.theme.CorDoTitulo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen1(
    notas: List<Nota>,
    onAddNota: (Nota) -> Unit, // Função para adicionar uma nova nota
    onUpdateNota: (Nota) -> Unit, // Função para atualizar uma nota existente
    onRemoveNota: (Nota) -> Unit,
    modifier: Modifier = Modifier
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var notaParaEditar by remember { mutableStateOf<Nota?>(null) }

    val context = LocalContext.current

    Column(modifier = Modifier.padding(6.dp)) {

        TopAppBar(
            title = {
                Text(
                    text = "TrainUp",
                    fontSize = 40.sp,
                    color = White,
                    fontWeight = FontWeight(600)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(CorDoTitulo),
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Caixa de texto para o título
            NoteCaixaTexto(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                text = titulo,
                label = "Título do Treino",
                onTextChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) titulo = it
                }
            )

            // Caixa de texto para a descrição
            NoteCaixaTexto(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                text = descricao,
                label = "Descrição do Treino",
                onTextChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) descricao = it
                }
            )

            // Botão para salvar ou atualizar
            BotaoNota(
                text = if (notaParaEditar == null) "Salvar Treino" else "Atualizar Treino",
                onClick = {
                    if (titulo.isNotEmpty() && descricao.isNotEmpty()) {
                        if (notaParaEditar == null) {
                            // Adiciona uma nova nota
                            onAddNota(Nota(titulo = titulo, descricao = descricao))
                        } else {
                            // Atualiza a nota existente
                            val updatedNote = notaParaEditar!!.copy(titulo = titulo, descricao = descricao)
                            onUpdateNota(updatedNote)
                        }

                        // Limpa os campos de entrada
                        titulo = ""
                        descricao = ""

                        // Reseta a nota que está sendo editada
                        notaParaEditar = null
                    }
                }
                ,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = CorDoTitulo)
            )
        }

        Divider(modifier = Modifier.padding(10.dp))

        LazyColumn {
            items(notas) { note ->
                NoteRow(
                    nota = note,
                    onNoteClicked = {
                        // Preenche os campos com a nota para editar
                        notaParaEditar = note
                        titulo = note.titulo.toString()
                        descricao = note.descricao.toString()
                    },
                    onNoteLongClicked = {
                        onRemoveNota(note)
                        Toast.makeText(context, "Nota removida", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteRow(
    nota: Nota,
    onNoteClicked: (Nota) -> Unit, // Para editar a nota
    onNoteLongClicked: (Nota) -> Unit, // Para remover a nota
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { onNoteClicked(nota) } // Clique para editar
            .combinedClickable(
                onClick = { onNoteClicked(nota) }, // Clique curto: editar
                onLongClick = { onNoteLongClicked(nota) } // Clique longo: deletar
            ),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFeef1ef)) // Mantendo o cinza original
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Estilo do título
                Text(
                    text = nota.titulo ?: "Sem título", // Adiciona um valor padrão caso seja nulo
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Estilo da descrição
                Text(
                    text = nota.descricao ?: "Sem descrição", // Adiciona um valor padrão caso seja nulo
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black //
                )
            }
        }
    }
}





@Composable
fun BotaoNota(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors // Aplica as cores do botão
    ) {
        Text(
            text = text,
            color = Color.White, // Define a cor do texto como branca
            fontWeight = FontWeight.Bold, // Deixa o texto em negrito
            fontSize = 16.sp // Ajuste o tamanho da fonte para melhor legibilidade
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCaixaTexto(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    onTextChange: (String) -> Unit
) {
    androidx.compose.material3.TextField(
        value = text,
        onValueChange = onTextChange,
        label = {
            Text(
                text = label,
                color = Color.Black // Define o rótulo sempre visível
            )
        },
        colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.White, // Cor do texto quando focado
            unfocusedTextColor = Color.Black, // Cor do texto quando desfocado
            containerColor = Color(0xFFF5F5F5), // Cor do fundo
            cursorColor = Color.Black, // Cor do cursor
            focusedIndicatorColor = Color.Gray, // Cor da linha inferior ao focar
            unfocusedIndicatorColor = Color.Gray, // Cor da linha inferior quando desfocado
            focusedLabelColor = Color.Black, // Cor do rótulo ao focar
            unfocusedLabelColor = Color.Gray // Cor do rótulo quando desfocado
        ),
        modifier = modifier
    )
}



