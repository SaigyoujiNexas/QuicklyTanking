package com.xiyou.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.xiyou.shop.entite.ShopItem
import com.xiyou.shop.service.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel
    @Inject constructor(val repository: ShopRepository): ViewModel() {
        val allItem = repository.allItem

    fun insert(item: ShopItem) = viewModelScope.launch {
        repository.insert(item)
    }
    fun delete(item: ShopItem) = viewModelScope.launch {
        repository.delete(item)
    }
}