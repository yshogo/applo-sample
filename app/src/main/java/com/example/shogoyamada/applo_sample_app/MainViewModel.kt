package com.example.shogoyamada.applo_sample_app

import UserQuery
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException

class MainViewModel: ViewModel() {

    val userName = ObservableField<String>()
    val avatarUrl = ObservableField<String>()
    val webSiteUrl = ObservableField<String>()

    fun getRepositoryList() {
        val repository = MainUseRepository()
        repository.getRepository().enqueue(object : ApolloCall.Callback<UserQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                print(e.toString())
            }

            override fun onResponse(response: Response<UserQuery.Data>) {
                val user = response.data()?.user()
                userName.set(user?.name())
                avatarUrl.set(user?.avatarUrl())
            }
        })
    }
}