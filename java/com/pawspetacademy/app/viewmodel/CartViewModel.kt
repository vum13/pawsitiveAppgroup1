package com.pawspetacademy.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

data class CartItem(val id: String, val name: String, val price: Double, val qty: Int = 1) {
    val lineTotal: Double get() = price * qty
}

class CartViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val items: StateFlow<List<CartItem>> = _items.asStateFlow()

    private val _orderPlaced = MutableStateFlow(false)
    val orderPlaced: StateFlow<Boolean> = _orderPlaced.asStateFlow()

    fun addItem(id: String, name: String, price: Double) = _items.update { list ->
        if (list.any { it.id == id }) list.map { if (it.id == id) it.copy(qty = it.qty + 1) else it }
        else list + CartItem(id, name, price)
    }

    fun changeQty(id: String, delta: Int) = _items.update { list ->
        list.mapNotNull { item ->
            if (item.id != id) item else (item.qty + delta).takeIf { it > 0 }?.let { item.copy(qty = it) }
        }
    }

    fun removeItem(id: String) = _items.update { list -> list.filterNot { it.id == id } }

    fun checkout() {
        if (_items.value.isEmpty()) return
        _items.value = emptyList()
        _orderPlaced.value = true
    }

    fun dismissOrderConfirmation() { _orderPlaced.value = false }
}
