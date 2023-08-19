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
import com.example.myapp.source.local.room.model.Restaurant
import com.example.myapp.utils.Resource
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

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btn.setOnClickListener(this)
    }

    private fun initObese() {
        viewModel.getUserData.observe(viewLifecycleOwner){ it->
            when(it){
                is Resource.Success -> {
                    updateUi(it.data)
                    Log.e("TAG", "initObese: Success", )
                }
                is Resource.Error -> {
                    Log.e("TAG", "initObese: error"+it.error.toString() )
                }
                is Resource.Loading ->{
                    Log.e("TAG", "initObese: loading" )
                }
            }
        }
    }


    private fun updateUi(data: List<Restaurant>?) {
        for (i in data?.indices!!){
            val tv_dynamic = TextView(requireContext())
            tv_dynamic.textSize = 20f
            tv_dynamic.text = data.get(i).name + " \n " + data.get(i).address
            binding.llMainLayout.addView(tv_dynamic)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn -> {

            }
        }

    }


}