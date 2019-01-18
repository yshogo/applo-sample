package com.example.shogoyamada.applo_sample_app

import AddReactionToIssueMutation
import LoginQuery
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import type.ReactionContent

class MainActivity : AppCompatActivity() {

    companion object {
        private const val BASE_URL = "https://api.github.com/graphql"
        private const val GITHUB_ACCESS_TOKENS = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient.Builder()
            .authenticator { _, response ->
                response.request().newBuilder().addHeader("Authorization", "Bearer $GITHUB_ACCESS_TOKENS")
                    .build()
            }.build()

        val apolloClient = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()

        val query = LoginQuery.builder().build()

        apolloClient.query(query).enqueue(object : ApolloCall.Callback<LoginQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.e("エラーが発生しました。", e.toString())
            }

            override fun onResponse(response: Response<LoginQuery.Data>) {
                val viewer = response.data()?.viewer()
                val userLogin = viewer?.login()
                print(userLogin)
            }
        })

        button.setOnClickListener {
            val mutation = AddReactionToIssueMutation.builder()
                .subjectId("MDU6SXNzdWU0MDA1ODMwODk=")
                .content(ReactionContent.THUMBS_DOWN)
                .build()

            apolloClient.mutate(mutation).enqueue(object : ApolloCall.Callback<AddReactionToIssueMutation.Data>() {
                override fun onFailure(e: ApolloException) {
                    print(e.toString())
                }

                override fun onResponse(response: Response<AddReactionToIssueMutation.Data>) {
                    print("成功")
                }
            })
        }
    }
}
