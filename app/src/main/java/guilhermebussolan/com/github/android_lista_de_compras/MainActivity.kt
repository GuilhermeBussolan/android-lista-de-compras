package guilhermebussolan.com.github.android_lista_de_compras

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import guilhermebussolan.com.github.android_lista_de_compras.viewmodel.ItemsAdapter
import guilhermebussolan.com.github.android_lista_de_compras.viewmodel.ItemsViewModel
import guilhermebussolan.com.github.android_lista_de_compras.viewmodel.ItemsViewModelFactory

/**
 * A activity principal da aplicação.
 * Esta activity é responsável por exibir a lista de itens e fornecer uma interface para adicionar novos itens à lista.
 * A activity usa um `ItemsViewModel` para interagir com o banco de dados.
 *
 * @property viewModel O ViewModel usado para interagir com o banco de dados.
 * @author Ewerton Carreira
 * @version 1.1 // Versão atualizada para refletir os novos campos
 * @since 2023-02-01
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemsViewModel
    private lateinit var editTextName: EditText // Referência ao campo de nome
    private lateinit var editTextPrice: EditText // Referência ao campo de preço
    private lateinit var editTextDescription: EditText // Referência ao campo de descrição
    private lateinit var addButton: Button // Referência ao botão de adicionar

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
        supportActionBar?.title = "Lista de Compras"

        // Encontra o RecyclerView pelo seu ID.
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Cria um novo adaptador para o RecyclerView. O adaptador é responsável por exibir os itens na lista.
        // Quando um item é clicado, ele é removido da lista.
        val itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }
        // Define o adaptador do RecyclerView.
        recyclerView.adapter = itemsAdapter

        // Encontra os campos de texto e o botão pelos seus IDs.
        // IDs atualizados conforme activity_main.xml
        editTextName = findViewById(R.id.editTextName)
        editTextPrice = findViewById(R.id.editTextPrice)
        editTextDescription = findViewById(R.id.editTextDescription)
        addButton = findViewById(R.id.button) // O ID do botão permanece o mesmo

        // Define o que acontece quando o botão é clicado.
        addButton.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val priceString = editTextPrice.text.toString().trim()
            val description = editTextDescription.text.toString().trim()

            // Validação do campo nome
            if (name.isEmpty()) {
                editTextName.error = "Nome é obrigatório"
                return@setOnClickListener // Impede a adição se o nome estiver vazio
            }

            // Validação e conversão do campo preço
            val price = priceString.toDoubleOrNull()
            if (price == null || price < 0) {
                editTextPrice.error = "Preço inválido" // Define um erro se o preço não for um número ou for negativo
                return@setOnClickListener // Impede a adição se o preço for inválido
            }

            // Adiciona o item ao ViewModel.
            // Se a descrição estiver vazia após o trim(), passamos 'null' para o ViewModel,
            // caso contrário, passamos a descrição.
            viewModel.addItem(name, price, if (description.isEmpty()) null else description)

            // Limpa todos os campos após a adição bem-sucedida do item.
            editTextName.text.clear()
            editTextPrice.text.clear()
            editTextDescription.text.clear()
            editTextName.requestFocus() // Coloca o foco de volta no campo de nome para facilitar a próxima entrada.
        }

        // Cria uma nova fábrica para o ViewModel.
        val viewModelFactory = ItemsViewModelFactory(application)
        // Obtém uma instância do ViewModel.
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        // Observa as mudanças na lista de itens e atualiza o adaptador quando a lista muda.
        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }
    }
}