package app.akane.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer


class SnackbarMessage : SingleLiveEvent<String>() {
    fun observe(owner: LifecycleOwner, observer: SnackbarObserver) {
        super.observe(owner, object : Observer<String> {
            override fun onChanged(t: String?) {
                if (t == null) {
                    return
                }
                observer.onNewMessage(t)
            }
        })
    }

    interface SnackbarObserver {
        /**
         * Called when there is a new message to be shown.
         * @param snackbarMessageResourceId The new message, non-null.
         */
        fun onNewMessage(snackbarMessageResourceId: String)
    }
}