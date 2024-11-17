import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ChatViewModel : ViewModel() {

    // Clave de API de OpenAI
//    private val apiKey = BuildConfig.OPENAI_API_KEY // Reemplaza con tu clave de API
    private val apiKey = "Bearer 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"
    var responseText by mutableStateOf("")
    private var cooldownActive by mutableStateOf(false) // Control de cooldown
    private val cooldownTimeMillis = 5000L // 5 segundos de espera entre peticiones
    private var debounceJob: Job? = null // Trabajo para debounce

    // Método para manejar la entrada del usuario y debouncing
    fun onUserInputChanged(newInput: String) {
        debounceJob?.cancel() // Cancela el trabajo anterior
        debounceJob = viewModelScope.launch {
            delay(300) // Espera 300 ms
            sendMessageToOpenAi(newInput) // Envía la solicitud después del debounce
        }
    }

    // Metodo para enviar un mensaje a OpenAI
    fun sendMessageToOpenAi(userMessage: String) {
        if (cooldownActive) {
            responseText = "Please wait before sending another message."
            return
        }

        viewModelScope.launch {
            cooldownActive = true // Activa el cooldown
            try {
                // Construir el mensaje de solicitud
                val message = ChatMessage("user", userMessage)
                val request = ChatRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(message)
                )

                // Hacer la solicitud a la API
                val response = withContext(Dispatchers.IO) {
                    ApiClient.openAiApi.getChatCompletion(apiKey, request)
                }

                // Extraer el mensaje de respuesta
                responseText = response.choices.firstOrNull()?.message?.content ?: "No response"

            } catch (e: HttpException) {
                when (e.code()) {
                    429 -> responseText = "Error 429: Too many requests. Please wait."
                    else -> responseText = "HTTP error: ${e.code()} - ${e.message()}"
                }
            } finally {
                delay(cooldownTimeMillis) // Espera antes de permitir otra solicitud
                cooldownActive = false // Desactiva el cooldown
            }
        }
    }

    // Método para enviar el mensaje con reintentos
    suspend fun sendMessageWithRetry(userMessage: String) {
        var attempt = 0
        var delayTime = 1000L // 1 segundo inicial

        while (attempt < 3) {
            try {
                sendMessageToOpenAi(userMessage) // Envía el mensaje
                break // Sale del bucle si la solicitud es exitosa
            } catch (e: HttpException) {
                if (e.code() == 429) {
                    delay(delayTime) // Espera antes de reintentar
                    delayTime *= 2 // Incrementa el tiempo de espera
                    attempt++ // Incrementa el contador de intentos
                } else {
                    responseText = "HTTP error: ${e.code()} - ${e.message()}"
                    break
                }
            }
        }
    }
}
