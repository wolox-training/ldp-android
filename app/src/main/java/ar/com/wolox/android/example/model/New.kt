package ar.com.wolox.android.example.model

data class New(
    val id: Int,
    val createdAt: String,
    val likes: List<Int>,
    val picture: String,
    val title: String,
    val text: String
)