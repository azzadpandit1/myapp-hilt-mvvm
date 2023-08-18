package com.example.myapp.ui.fregment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapp.R
import com.example.myapp.databinding.FragmentHomeBinding
import com.example.myapp.utils.NetworkResult
import com.example.myapp.utils.toast
import com.example.myapp.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() ,View.OnClickListener{

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

        initObese()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserListRequest()
        Log.e("TAG", "request data: ", )



        setOnClickListner()
    }

    private fun setOnClickListner() {
        binding.btn.setOnClickListener(this)
    }

    private fun initObese() {
        viewModel.userResponseLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    Log.e("TAG", "initObese: succesfully", )
                    binding.txtView.text = it.data?.data.toString()
                    context?.toast({ "Done" })
                }
                is NetworkResult.Error -> {
                    context?.toast({ "Errro" })
                    Log.e("TAG", "initObese: error", )
                }
                is NetworkResult.Loading ->{
                    context?.toast({ "loading" })
                    Log.e("TAG", "initObese: loading ", )
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn -> {
                viewModel.getUserListRequest()
                context?.toast({ "request" })

            }
        }

    }


}