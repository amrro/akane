package app.akane.di

import android.app.Application
import app.akane.AkaneApp
import app.akane.data.DatabaseMoudle
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
            AppAssistedModule::class,
            DatabaseMoudle::class
        ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(akaneApp: AkaneApp)
}