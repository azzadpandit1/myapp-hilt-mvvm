package com.example.myapp.ui.fregment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentHomeBinding
import com.example.myapp.source.local.room.model.GetUserResponse
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObese()

        setOnClickListener()

        init()
    }

    private fun init() {

    }

    private fun setOnClickListener() {
        binding.btn.setOnClickListener(this)

        binding.tvGif.setOnClickListener(this)
        binding.tvVideo.setOnClickListener(this)
        binding.tvImage.setOnClickListener(this)
        binding.tvMp3.setOnClickListener(this)
    }



    private fun initObese() {
        viewModel.getUserData.observe(viewLifecycleOwner) { it ->
            when (it) {
                is NetworkResult.Success -> {
                    context?.toast({ "Done" })
                    updateUi(it.data)
                }

                is NetworkResult.Error -> {
                    context?.toast({ "Errro" })
                }

                is NetworkResult.Loading -> {
                    context?.toast({ "loading" })
                }

            }
        }
    }


    private fun updateUi(data: GetUserResponse?) {
       binding.tvGif.text = data?.data?.gif
       binding.tvImage.text = data?.data?.image
       binding.tvMp3.text = data?.data?.audio
       binding.tvVideo.text = data?.data?.video
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn -> {
                viewModel.getCacheFromNetwork()
            }
            R.id.tvVideo -> {
                val data = Bundle().apply { putString("url",binding.tvVideo.text.toString()) }
                findNavController().navigate(R.id.action_homeFragment_to_playerFragment,data)
            }
            R.id.tvMp3 -> {
                val data = Bundle().apply { putString("url",binding.tvMp3.text.toString()) }
                findNavController().navigate(R.id.action_homeFragment_to_playerFragment,data)
            }
            R.id.tvImage -> {
                val data = Bundle().apply { putString("url",binding.tvImage.text.toString()) }
                findNavController().navigate(R.id.action_homeFragment_to_playerFragment,data)
            }
            R.id.tvGif -> {
                val data = Bundle().apply { putString("url",binding.tvGif.text.toString()) }
                findNavController().navigate(R.id.action_homeFragment_to_playerFragment,data)
            }


        }

    }


}