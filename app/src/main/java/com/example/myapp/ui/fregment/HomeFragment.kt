package com.example.myapp.ui.fregment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapp.R
import com.example.myapp.databinding.FragmentHomeBinding
import com.example.myapp.source.remote.response.UserListResponse
import com.example.myapp.utils.NetworkResult
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObese()

        viewModel.getUserListRequest()

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btn.setOnClickListener(this)
    }

    private fun initObese() {
        viewModel.getUserData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    updateUi(it.data?.data)
                    Log.e("TAG", "initObese: Success", )
                }
                is NetworkResult.Error -> {
                    Log.e("TAG", "initObese: error"+it.message )
                }
                is NetworkResult.Loading ->{
                    Log.e("TAG", "initObese: loading" )
                }
            }
        }
    }


    private fun updateUi(data: List<UserListResponse.Data>?) {
        for (i in data?.indices!!){
            val tv_dynamic = TextView(requireContext())
            tv_dynamic.textSize = 20f
            tv_dynamic.text = data.get(i).first_name + " " + data.get(i).last_name
            binding.llMainLayout.addView(tv_dynamic)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn -> {
                viewModel.getUserListRequest()
            }
        }

    }


}