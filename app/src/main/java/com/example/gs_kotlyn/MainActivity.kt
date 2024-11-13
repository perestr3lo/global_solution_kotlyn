package com.example.gs_kotlyn
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_kotlyn.R
import com.example.gs_kotlyn.viewmodel.DicasAdapter
import com.example.gs_kotlyn.viewmodel.DicasViewModel
import com.example.gs_kotlyn.viewmodel.DicasViewModelFactory


/**
 * A activity principal da aplicação.
 * Esta activity é responsável por exibir a lista de itens e fornecer uma interface para adicionar novos itens à lista.
 * A activity usa um `ItemsViewModel` para interagir com o banco de dados.
 *
 * @property viewModel O ViewModel usado para interagir com o banco de dados.
 * @author Ewerton Carreira
 * @version 1.0
 * @since 2023-02-01
 */
class MainActivity : AppCompatActivity() {

    // O ViewModel usado para interagir com o banco de dados.
    private lateinit var viewModel: DicasViewModel

    /**
     * Chamado quando a activity é criada.
     * Este método configura a interface do usuário e inicializa o ViewModel.
     *
     * @param savedInstanceState Se a activity está sendo recriada a partir de um estado salvo, este é o estado.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Chama o método onCreate da superclasse para completar a criação da activity.
        super.onCreate(savedInstanceState)
        // Define o layout da activity.
        setContentView(R.layout.activity_main)

        // Encontra a barra de ferramentas pelo seu ID e a define como a barra de ação para esta activity.
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Define o título da barra de ação.
        supportActionBar?.title = "Lista de Dicas"

        // Encontra o RecyclerView pelo seu ID.
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Cria um novo adaptador para o RecyclerView. O adaptador é responsável por exibir os itens na lista.
        // Quando um item é clicado, ele é removido da lista.
        val dicasAdapter = DicasAdapter { item ->
            viewModel.removeItem(item)
        }
        // Define o adaptador do RecyclerView.
        recyclerView.adapter = dicasAdapter

        // Encontra o botão e o campo de texto pelo seus IDs.
        val button = findViewById<Button>(R.id.button)
        val editTextTtile = findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)

        // Define o que acontece quando o botão é clicado.
        button.setOnClickListener {
            // Se o campo de texto estiver vazio, exibe um erro e retorna.
            if (editTextTtile.text.isEmpty()) {
                editTextTtile.error = "Preencha um valor para o titulo"
                return@setOnClickListener
            }
            if (editTextDescription.text.isEmpty()) {
                editTextDescription.error = "Preencha um valor para a descricao"
                return@setOnClickListener
            }


            // Adiciona o item ao ViewModel e limpa o campo de texto.
            viewModel.addItem(editTextTtile.text.toString(), editTextDescription.text.toString())
            editTextTtile.text.clear()
            editTextDescription.text.clear()
        }

        // Cria uma nova fábrica para o ViewModel.
        val viewModelFactory = DicasViewModelFactory(application)
        // Obtém uma instância do ViewModel.
        viewModel = ViewModelProvider(this, viewModelFactory).get(DicasViewModel::class.java)

        // Observa as mudanças na lista de itens e atualiza o adaptador quando a lista muda.
        viewModel.dicasLiveData.observe(this) { items ->
            dicasAdapter.updateDicas(items)
        }
    }
}