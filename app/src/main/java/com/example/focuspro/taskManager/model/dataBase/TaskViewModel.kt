import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focuspro.taskManager.model.entity.Task
import com.example.focuspro.taskManager.model.entity.TaskPriority
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private val auth: FirebaseAuth = FirebaseAuth.getInstance()


class TaskViewModel : ViewModel() {
    //Accedo al id unico de cada usuario al momento de estar rfegistrado, lo que me permite asociarle tareas
    private val database = FirebaseDatabase.getInstance().reference

    // Obtener el ID del usuario actual, si está autenticado
    private val userId: String? get() = auth.currentUser?.uid

    // Referencia a la colección de tareas del usuario actual
    private val taskRef get() = database.child("tasks").child(userId ?: "unknown_user")

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> get() = _taskList

    init {
        // Escuchar cambios en Firebase solo si el usuario está autenticado
        userId?.let {
            taskRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tasks = snapshot.children.mapNotNull {
                        it.getValue(Task::class.java)?.apply { id = it.key }
                    }
                    _taskList.value = tasks
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejo de errores
                }
            })
        }
    }

    // Agregar una nueva tarea para el usuario actual en Firebase
    fun addTask(title: String, priority: TaskPriority) {
        userId?.let {
            val newTaskRef = taskRef.push()
            val task =
                Task(id = newTaskRef.key, title = title, priority = priority, createdById = it)
            newTaskRef.setValue(task.toMap())
        }
    }

    // Cambiar el estado de completado de una tarea
    fun toggleTaskCompletion(task: Task) {
        userId?.let {
            task.id?.let { taskId ->
                val updatedTask = task.copy(isCompleted = !task.isCompleted)
                viewModelScope.launch {
                    taskRef.child(taskId).updateChildren(updatedTask.toMap())
                }
            }
        }
    }

    // Eliminar una tarea para el usuario actual
    fun deleteTask(task: Task) {
        userId?.let {
            task.id?.let { taskId ->
                viewModelScope.launch {
                    taskRef.child(taskId).removeValue()
                }
            }
        }
    }
}
