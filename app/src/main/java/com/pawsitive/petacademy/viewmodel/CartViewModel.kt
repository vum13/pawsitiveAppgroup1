package com.pawsitive.petacademy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawsitive.petacademy.data.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CartItem(val product: Product, val quantity: Int) {
    val lineTotal: Double get() = product.price * quantity
}

data class PaymentReceipt(val reference: String, val amountPaid: Double, val itemCount: Int)

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val isProcessing: Boolean = false,
    val receipt: PaymentReceipt? = null
) {
    val itemCount: Int get() = items.sumOf { it.quantity }
    val subtotal: Double get() = items.sumOf { it.lineTotal }
    val delivery: Double
        get() = if (items.isEmpty() || subtotal >= CartViewModel.FREE_DELIVERY_THRESHOLD) 0.0
        else CartViewModel.DELIVERY_FEE
    val total: Double get() = subtotal + delivery
}

/** Activity-scoped so the cart survives navigation between Shop, Home and Checkout. */
class CartViewModel : ViewModel() {

    private val _state = MutableStateFlow(CartUiState())
    val state: StateFlow<CartUiState> = _state.asStateFlow()

    private fun editItems(transform: (List<CartItem>) -> List<CartItem>) {
        _state.update { current ->
            if (current.isProcessing) current else current.copy(items = transform(current.items))
        }
    }

    fun addToCart(product: Product) = editItems { items ->
        if (items.any { it.product.id == product.id }) {
            items.map { if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it }
        } else {
            items + CartItem(product, 1)
        }
    }

    fun increase(productId: String) = editItems { items ->
        items.map { if (it.product.id == productId) it.copy(quantity = it.quantity + 1) else it }
    }

    fun decrease(productId: String) = editItems { items ->
        items.mapNotNull {
            when {
                it.product.id != productId -> it
                it.quantity <= 1 -> null
                else -> it.copy(quantity = it.quantity - 1)
            }
        }
    }

    fun remove(productId: String) = editItems { items -> items.filterNot { it.product.id == productId } }

    /** Simulated payment: short delay, then success receipt and an emptied cart. */
    fun pay() {
        val snapshot = _state.value
        if (snapshot.items.isEmpty() || snapshot.isProcessing) return
        viewModelScope.launch {
            _state.update { it.copy(isProcessing = true) }
            delay(1800)
            val receipt = PaymentReceipt(
                reference = "PPA-${(100000..999999).random()}",
                amountPaid = snapshot.total,
                itemCount = snapshot.itemCount
            )
            _state.value = CartUiState(receipt = receipt)
        }
    }

    fun dismissReceipt() = _state.update { it.copy(receipt = null) }

    companion object {
        const val DELIVERY_FEE = 60.0
        const val FREE_DELIVERY_THRESHOLD = 500.0
    }
}
