package com.example.githubuserapp3.activity

import android.content.Intent.EXTRA_USER
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp3.Resource
import com.example.githubuserapp3.R
import com.example.githubuserapp3.ViewStateCallBack
import com.example.githubuserapp3.adapter.ViewPagerAdapter
import com.example.githubuserapp3.databinding.ActivityDetailBinding
import com.example.githubuserapp3.response.User
import com.example.githubuserapp3.viewModel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity(), ViewStateCallBack<User?> {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.followers,
        R.string.following
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_USER)
        if (username != null) {
            viewModel.getDetailUser(username).observe(this, {
                when(it) {
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> onSuccess(it.data)
                    is Resource.Error -> onFailed(it.message)
                }
            })

            val pageAdapter = ViewPagerAdapter(this, username.toString())
            binding.apply {
                viewPager.adapter = pageAdapter
                TabLayoutMediator(tabs, viewPager) { tabs, position ->
                    tabs.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onLoading() {
        binding.apply {
            mainProgressBar.visibility = invisible
        }
    }

    override fun onSuccess(data: User?) {
        binding.apply {
            tvUsername.text = data?.username
            tvName.text = data?.name
            tvCompany.text = data?.company
            tvLocation.text = data?.location
            tvRepositoryValue.text = data?.repository.toString()
            tvFollowersValue.text = data?.followers.toString()
            tvFollowingValue.text = data?.following.toString()

            Glide.with(this@DetailActivity)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(ivAvatar)
        }
    }

    override fun onFailed(message: String?) {

    }
}
