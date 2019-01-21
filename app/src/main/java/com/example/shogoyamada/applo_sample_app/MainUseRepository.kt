package com.example.shogoyamada.applo_sample_app

import UserQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import okhttp3.OkHttpClient

class MainUseRepository {

    companion object {
        private const val BASE_URL = "https://api.github.com/graphql"
        private const val GITHUB_ACCESS_TOKENS = ""
    }

    fun getRepository() : ApolloQueryCall<UserQuery.Data> {

        val okHttpClient = OkHttpClient.Builder()
            .authenticator { _, response ->
                response.request().newBuilder().addHeader("Authorization", "Bearer $GITHUB_ACCESS_TOKENS").build()
            }.build()

        val apolloClient = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()

        val query = UserQuery.builder().build()

        return apolloClient.query(query)
    }
}