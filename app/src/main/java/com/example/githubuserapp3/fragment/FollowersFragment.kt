package com.example.githubuserapp3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp3.R
import com.example.githubuserapp3.Resource
import com.example.githubuserapp3.ViewStateCallBack
import com.example.githubuserapp3.adapter.UserAdapter
import com.example.githubuserapp3.databinding.FragmentFollowersBinding
import com.example.githubuserapp3.response.User
import com.example.githubuserapp3.viewModel.FollowersViewModel

class FollowersFragment : Fragment(), ViewStateCallBack<List<User>> {

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun getInstance(username: String) : Fragment {
            return FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

    private var binding: FragmentFollowersBinding? = null
    private lateinit var viewModel: FollowersViewModel
    private lateinit var userAdpater: UserAdapter
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        userAdpater = UserAdapter()
        binding?.rvListFollowers?.apply {
            adapter = userAdpater
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getFollowers(username.toString()).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        })
    }
    override fun onLoading() {
        binding?.apply {
            followersProgressBar.visibility = visible
        }
    }

    override fun onSuccess(data: List<User>) {
        userAdpater.setAllData(data)
        binding?.apply {
            followersProgressBar.visibility = invisible
            rvListFollowers.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        binding?.apply {
            if (message == null){
                tvMessage.text = resources.getString(R.string.followers_not_found)
                tvMessage.visibility = visible
            } else {
                tvMessage.visibility = visible
            }
        }
    }
}