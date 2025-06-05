package guilhermebussolan.com.github.android_lista_de_compras.data

import androidx.room.Database
import androidx.room.RoomDatabase
import guilhermebussolan.com.github.android_lista_de_compras.model.ItemModel

/**
 * Uma classe abstrata que serve como o ponto principal de acesso ao banco de dados SQLite para o aplicativo.
 * Esta classe é anotada com @Database, listando todas as entidades que devem ser criadas no banco de dados.
 * A classe estende RoomDatabase, que é uma classe do Room Persistence Library que contém o banco de dados.
 * Dentro da classe, você define um método abstrato para cada @Dao que está associado ao banco de dados.
 *
 * @author Ewerton Carreira
 * @version 1.0
 * @since 2023-03-01
 */
@Database(entities = [ItemModel::class], version = 3)
abstract class ItemDatabase : RoomDatabase() {

    /**
     * Este método abstrato serve como um ponto de acesso para a classe DAO.
     * Quando este método é chamado, o Room irá gerar uma implementação.
     *
     * @return uma instância da classe DAO (ItemDao).
     */
    abstract fun itemDao(): ItemDao
}