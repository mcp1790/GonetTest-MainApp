package mx.test.android.gonet.main.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    protected val baseActivity by lazy {
        requireActivity() as BaseActivity
    }

    abstract fun initComponents()
    abstract fun initObservablesViewModel()

    fun showErrorMessage(message: String){
        baseActivity.showErrorMessage(message)
    }
    fun showProgressDialog(show: Boolean){
        baseActivity.showProgressDialog(show)
    }
}