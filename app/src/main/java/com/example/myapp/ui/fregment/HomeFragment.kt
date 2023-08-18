package com.example.myapp.ui.fregment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

//    var progressDialog: ProgressDialog? = null


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

        setOnClickListner()
    }

    private fun setOnClickListner() {
        binding.btn.setOnClickListener(this)
    }

    private fun initObese() {
        viewModel.userResponseLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {

                    binding.txtView.text = it.data?.data.toString()
                    context?.toast({ "Done" })
                }
                is NetworkResult.Error -> {
                    context?.toast({ "Errro" })
                }
                is NetworkResult.Loading ->{
                    context?.toast({ "loading" })
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