import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.kotlin_cleanarchitecture_todolist.data.local.TodoDao
import com.example.kotlin_cleanarchitecture_todolist.data.local.TodoDatabase
import com.example.kotlin_cleanarchitecture_todolist.data.repository.TodoRepositoryImpl
import com.example.kotlin_cleanarchitecture_todolist.domain.repository.TodoRepository
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.DeleteTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.GetAllTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.InsertTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.UpdateTodoUseCase

object AppModule{
    private var database: TodoDatabase? = null
    private var todoDao: TodoDao? = null
    private var repository: TodoRepository? = null

    fun provideDatabase(context: Context): TodoDatabase{
        return database?: Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build().also{database = it}
    }

    fun provideTodoDao(database: TodoDatabase): TodoDao{
        return todoDao ?:database.todoDao().also { todoDao = it }
    }

    fun provideRepository(dao: TodoDao): TodoRepository{
        return repository ?: TodoRepositoryImpl(dao).also { repository = it }
    }

    fun providerGetAllTodoUseCase(repository: TodoRepository): GetAllTodoUseCase{
        return GetAllTodoUseCase(repository)
    }

    fun provideInsertTodoUsecase(repository: TodoRepository): InsertTodoUseCase {
        return InsertTodoUseCase(repository)
    }

    fun provideUpdateTodoUsecase(repository: TodoRepository): UpdateTodoUseCase {
        return UpdateTodoUseCase(repository)
    }

    fun provideDeleteTodoUseCase(repository: TodoRepository) : DeleteTodoUseCase {
        return DeleteTodoUseCase(repository)
    }

}