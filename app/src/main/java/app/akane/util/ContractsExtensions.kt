package app.akane.util


inline fun String?.notNullOrEmpty(lazyMessage: () -> Any): String {
    require(!this.isNullOrEmpty(), lazyMessage)
    return this
}
