package com.example.githubuserapp3.response

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("login")
    val username: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("public_repos")
    val repository: String? = null,

    @field:SerializedName("followers")
    val followers: String? = null,

    @field:SerializedName("following")
    val following: String? = null,

    @field:SerializedName("avatar_url")
    val avatar: String? = null
)