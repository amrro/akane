package app.akane.data.mapper

interface Mapper<F, T> {
    operator fun invoke(from: List<F>): List<T>
}