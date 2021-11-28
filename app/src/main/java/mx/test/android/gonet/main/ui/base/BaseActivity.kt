package mx.test.android.gonet.main.ui.base

import android.app.Dialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mx.test.android.gonet.main.R

abstract class BaseActivity: AppCompatActivity() {

    private var progressDialog: Dialog? = null

    abstract fun initComponents()
    abstract fun initObservablesViewModel()

    fun initLoading(){
        progressDialog = Dialog(this, R.style.Dialog_Transparent)
        progressDialog?.setContentView(R.layout.view_progress_dialog)
        progressDialog?.setCancelable(false)
    }

    fun showProgressDialog(show: Boolean){
        if (show) { progressDialog?.show() } else { progressDialog?.dismiss() }
    }

    fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}