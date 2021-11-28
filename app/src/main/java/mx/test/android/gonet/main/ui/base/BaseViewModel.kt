package mx.test.android.gonet.main.ui.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

@SuppressLint("StaticFieldLeak")
open class BaseViewModel constructor(private var context: Context) : ViewModel() {

    protected val disposable = CompositeDisposable()

    protected val showProgress = MutableLiveData<Boolean>()
    protected val showErrorMessage = MutableLiveData<String>()

    fun getShowProgress(): LiveData<Boolean> = showProgress
    fun getErrorMessage(): LiveData<String> = showErrorMessage
}