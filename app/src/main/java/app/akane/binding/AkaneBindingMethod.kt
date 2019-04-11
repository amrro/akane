package app.akane.binding

import android.view.View
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingMethods(
    BindingMethod(
        type = SwipeRefreshLayout::class,
        attribute = "isRefreshing",
        method = "setRefreshing"
    ),
    BindingMethod(
        type = View::class,
        attribute = "onTouchListener",
        method = "setOnTouchListener"
    )
)
class AkaneBindingMethod