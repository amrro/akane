package app.akane.util

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun ShowSnackbar(
        view: View?,
        message: String?
//        @Snackbar.Duration duration: Int = Snackbar.LENGTH_LONG
) {
    if (view == null || message == null) return
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}