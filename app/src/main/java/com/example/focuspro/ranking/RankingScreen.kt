import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.*

@Composable
fun RankingScreen() {
    var users by remember { mutableStateOf(listOf<UserScore>()) }

    LaunchedEffect(Unit) {
        val database = FirebaseDatabase.getInstance().reference.child("users")
        database.orderByChild("score").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = snapshot.children.mapNotNull {
                    val name = it.child("name").getValue(String::class.java)
                    val score = it.child("score").getValue(Int::class.java) ?: 0
                    name?.let { UserScore(name, score) }
                }.sortedByDescending { it.score }
                users = userList
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(users) { userScore ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = userScore.name)
                Text(text = "${userScore.score} pts")
            }
        }
    }
}

data class UserScore(val name: String, val score: Int)
