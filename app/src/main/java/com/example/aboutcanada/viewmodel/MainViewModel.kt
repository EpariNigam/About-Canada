package com.example.aboutcanada.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aboutcanada.R
import com.example.aboutcanada.model.Fact
import com.example.aboutcanada.ws.IApiService
import com.example.aboutcanada.ws.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private var call: Call<Fact>? = null
    private val liveData = MutableLiveData<AppResult<Any>>()

    fun getFacts() {
        liveData.value = AppResult.Loading
        call = RetrofitService.createService(IApiService::class.java).facts()
        call?.enqueue(object : Callback<Fact> {
            override fun onFailure(call: Call<Fact>, t: Throwable) {
                liveData.value = t.message?.let { AppResult.Error(it) }
            }

            override fun onResponse(call: Call<Fact>, response: Response<Fact>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        liveData.value = AppResult.Success(response.body()!!)
                    } else {
                        liveData.value = AppResult.IntError(R.string.something_went_wrong)
                    }
                } else {
                    liveData.value = AppResult.IntError(R.string.we_are_unable_to_talk)
                }
            }
        })
    }

    fun getLiveData(): MutableLiveData<AppResult<Any>> {
        return liveData
    }

    override fun onCleared() {
        super.onCleared()
        call?.let {
            if (!it.isExecuted) {
                it.cancel()
            }
        }
    }
}