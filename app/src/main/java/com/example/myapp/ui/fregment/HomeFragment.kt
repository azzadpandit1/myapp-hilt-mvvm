package com.example.myapp.ui.fregment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapp.databinding.FragmentHomeBinding
import com.example.myapp.utils.NetworkResult
import com.example.myapp.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    private val viewModel by activityViewModels<UserViewModel>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserListRequest()

        initObese()
    }

    private fun initObese() {
        viewModel.userResponseLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    Log.e("TAG", "initObeser: "+it.data?.data.toString() )
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading ->{

                }
            }
        }
    }


}