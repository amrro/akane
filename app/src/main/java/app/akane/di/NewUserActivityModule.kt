package app.akane.di

import app.akane.ui.auth.NewUserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewUserActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeNewUserActivity(): NewUserActivity
}