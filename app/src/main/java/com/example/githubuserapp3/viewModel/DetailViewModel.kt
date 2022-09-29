package com.example.githubuserapp3.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp3.Resource
import com.example.githubuserapp3.network.ApiConfig
import com.example.githubuserapp3.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val config = ApiConfig.getApiService()
    private val detailUser = MutableLiveData<Resource<User>>()

    fun getDetailUser(username: String?) : LiveData<Resource<User>> {
        detailUser.postValue(Resource.Loading())
        config.getDetailUser(username!!).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val result = response.body()
                detailUser.postValue(Resource.Success(result))
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                detailUser.postValue(Resource.Error(t.message))
            }
        })

        return detailUser
    }
}