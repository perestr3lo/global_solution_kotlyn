
import android.app.Application
import android.icu.text.CaseMap.Title
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.gs_kotlyn.data.DicaDao
import com.example.gs_kotlyn.data.DicaDatabase
import com.example.gs_kotlyn.model.DicaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DicasViewModel(application: Application) : AndroidViewModel(application) {

    private val dicaDao: DicaDao

    val dicasLiveData: LiveData<List<DicaModel>>

    init {

        val database = Room.databaseBuilder(
            getApplication(),
            DicaDatabase::class.java,
            "dicas_database"
        ).build()

        dicaDao = database.DicaDao()

        dicasLiveData = dicaDao.getAll()
    }


    fun addItem(title: String, description: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val newItem = DicaModel(title = title, description = description)

            dicaDao.insert(newItem)
        }
    }

    fun removeItem(item: DicaModel) {
        // Inicia uma nova corrotina no escopo do ViewModel. As corrotinas são leves e não
        // bloqueiam a thread principal.
        // O Dispatcher.IO é usado para executar a corrotina em uma thread que é otimizada para
        // operações de E/S, como acesso ao banco de dados.
        viewModelScope.launch(Dispatchers.IO) {
            // Remove o item do banco de dados. Como esta operação pode levar algum tempo, ela é
            // executada em uma corrotina para evitar bloquear a thread principal.
            dicaDao.delete(item)
        }
    }
}