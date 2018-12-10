package ar.com.wolox.android.example.ui.home.news

import android.support.annotation.NonNull
import ar.com.wolox.android.example.model.New

interface INewClickListener {

    fun onNewClicked(@NonNull new: New)
}