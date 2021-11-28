package mx.test.android.gonet.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import mx.test.android.gonet.main.R
import mx.test.android.gonet.main.databinding.ActivityMainBinding
import mx.test.android.gonet.main.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navComponent: NavController
    private lateinit var navHostFragment: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initObservablesViewModel()
        initLoading()
    }

    override fun initComponents() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navComponent = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.navView, navComponent)

        binding.navView.itemIconTintList = null
    }

    override fun initObservablesViewModel() {

    }
}