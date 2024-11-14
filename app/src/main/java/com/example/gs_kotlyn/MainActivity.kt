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
import androidx.appcompat.widget.SearchView


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

    private lateinit var viewModel: DicasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "EcoDicas"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val dicasAdapter = DicasAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter = dicasAdapter

        val button = findViewById<Button>(R.id.button)
        val editTextTtile = findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)

        button.setOnClickListener {
            if (editTextTtile.text.isEmpty()) {
                editTextTtile.error = "Preencha um valor para o titulo"
                return@setOnClickListener
            }
            if (editTextDescription.text.isEmpty()) {
                editTextDescription.error = "Preencha um valor para a descricao"
                return@setOnClickListener
            }


            viewModel.addItem(editTextTtile.text.toString(), editTextDescription.text.toString())
            editTextTtile.text.clear()
            editTextDescription.text.clear()
        }

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dicasAdapter.filter(newText ?: "")
                return true
            }
        })


        val viewModelFactory = DicasViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DicasViewModel::class.java)

        viewModel.dicasLiveData.observe(this) { items ->
            dicasAdapter.updateDicas(items)
        }
    }
}