package com.example.android_chat_app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_chat_app.data.Event
import com.example.android_chat_app.data.Result


abstract class DefaultViewModel : ViewModel() {
    protected val mSnackbarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackbarText

    private val  mDataLoading = MutableLiveData<Event<Boolean>>()
    val dataLoading: LiveData<Event<Boolean>> = mDataLoading

    protected fun <T> onResult(mutableLiveData: MutableLiveData<T>? = null, result: Result<T>){
        when(Result){
            is Result.Loading -> mDataLoading.value = Event(true)

            is Result.Error -> {
                mDataLoading.value = Event(false)
                result.msg?.let {snackBarText.value = Event(it)}
            }

            is Result.Success -> {
                mDataLoading.value = Event(false)
                result.data?.let {mutableLiveData?.value = it}
                result.msg?.let{ mSnackbarText.value = Event(it) }
            }
        }
    }
}