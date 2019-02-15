package app.akane.di

import android.app.Application
import app.akane.AkaneApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            MainActivityModule::class,
//            AppAssistedModule::class,
            DateSourceModule::class
        ]
)
interface AppComponent {

    @dagger.Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(akaneApp: AkaneApp)
}