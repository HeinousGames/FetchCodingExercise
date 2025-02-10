package net.heinousgames.app.fetch.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.heinousgames.app.fetch.api.Client
import net.heinousgames.app.fetch.api.HiringObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: MainInterface, ViewModel() {

    val tag = "MainViewModel"

    private val _hiringMap = MutableStateFlow<Map<Int, List<HiringObject>>>(emptyMap())
    val hiringMap: StateFlow<Map<Int, List<HiringObject>>> = _hiringMap.asStateFlow()

    private val apiService by lazy { Client().getApiService() }

    init {
        getHiringList(filteredMap = {
            _hiringMap.value = it
        })
    }

    override fun getHiringList(filteredMap: (Map<Int, List<HiringObject>>) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val getCall = apiService.getHiringList()
                getCall.enqueue(object : Callback<List<HiringObject>> {
                    override fun onResponse(
                        call: Call<List<HiringObject>>,
                        response: Response<List<HiringObject>>
                    ) {
                        if (response.isSuccessful) {
                            val list = response.body()
                            list?.let { listToFilter ->
                                listToFilter
                                    // Filter out any items where "name" is blank or null
                                    .filter { !it.name.isNullOrBlank() }
                                filteredMap(
                                    listToFilter
                                        // Filter out any items where "name" is blank or null
                                        .filter { !it.name.isNullOrBlank() }
                                        // Sort the results first by "listId" then by "name" when displaying
                                        .sortedWith(compareBy(
                                            {it.listId},
                                            // Convert the number string to int for numeric sorting
                                            {it.name!!.substring(5).toInt()}
                                        ))
                                        // Display all the items grouped by "listId"
                                        .groupBy { it.listId }
                                )
                            }
                        } else {
                            Log.d(tag, "api error: ${response.errorBody().toString()}")
                        }
                    }

                    override fun onFailure(call: Call<List<HiringObject>>, throwable: Throwable) {
                        Log.d(tag, "api failure: ${throwable.message}")
                    }

                })
            }
        }
    }
}