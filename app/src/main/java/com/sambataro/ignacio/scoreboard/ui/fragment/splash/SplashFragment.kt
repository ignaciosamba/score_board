package com.sambataro.ignacio.scoreboard.ui.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sambataro.ignacio.scoreboard.R
import com.sambataro.ignacio.scoreboard.databinding.SplashFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class SplashFragment : Fragment() {

    private lateinit var binding : SplashFragmentBinding

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: SplashViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, SplashViewModelFactory(activity.application))
            .get(SplashViewModel::class.java)
    }

    val fragmentScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.splash_fragment,
            container,
            false
        )
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.sendUserToSelectorFragment.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController()
                    .navigate(SplashFragmentDirections.actionShowSelectorFragment())
                viewModel.displayMainFragmentDone()
            }
        })
        return binding.root
    }
}