package ar.com.wolox.android.example.model

import java.io.Serializable

data class New(
    val id: Int,
    val createdAt: String,
    val likes: List<Int>,
    val picture: String,
    val title: String,
    val text: String
) : Serializable {
    override fun equals(other: Any?) = if (other !is New) false else (id == other.id)
    override fun hashCode() = super.hashCode()
}