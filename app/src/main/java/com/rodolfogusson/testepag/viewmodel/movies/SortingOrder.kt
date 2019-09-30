package com.rodolfogusson.testepag.viewmodel.movies

import android.content.Context
import com.rodolfogusson.testepag.R

enum class SortingOrder {
    ASCENDING, DESCENDING, UNSORTED
}

fun textFor(order: SortingOrder, context: Context): String {
    return when (order) {
        SortingOrder.ASCENDING -> context.getString(R.string.ascending_order)
        SortingOrder.DESCENDING -> context.getString(R.string.descending_order)
        SortingOrder.UNSORTED -> context.getString(R.string.unsorted)
    }
}