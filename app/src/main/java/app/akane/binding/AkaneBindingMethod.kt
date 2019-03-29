package app.akane.binding

import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


@BindingMethods(
    BindingMethod(
        type = SwipeRefreshLayout::class,
        attribute = "isRefreshing",
        method = "setRefreshing"
    )
)
class AkaneBindingMethod