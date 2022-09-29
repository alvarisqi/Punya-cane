package com.example.githubuserapp3.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp3.Resource
import com.example.githubuserapp3.network.ApiConfig
import com.example.githubuserapp3.response.SearchResponse
import com.example.githubuserapp3.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val config = ApiConfig.getApiService()
    private val user = MutableLiveData<Resource<List<User>>>()

    fun searchUser(query: String): LiveData<Resource<List<User>>> {
        user.postValue(Resource.Loading())
        config.searchUser(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val list = response.body()?.items
                if (list.isNullOrEmpty())
                    user.postValue(Resource.Error(null))
                else
                    user.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                user.postValue(Resource.Error(t.message))
            }

        })
        return user
    }
}