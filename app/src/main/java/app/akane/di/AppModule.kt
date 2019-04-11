package app.akane.di

import android.app.Application
import app.akane.util.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import net.dean.jraw.android.AndroidHelper
import net.dean.jraw.android.ManifestAppInfoProvider
import net.dean.jraw.android.SharedPreferencesTokenStore
import net.dean.jraw.models.PersistedAuthData
import net.dean.jraw.oauth.AccountHelper
import java.util.TreeMap
import java.util.UUID
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideTokenStore(app: Application): SharedPreferencesTokenStore {
        return SharedPreferencesTokenStore(app.applicationContext).apply {
            this.load()
            this.autoPersist = true
        }
    }

    @Singleton
    @Provides
    fun providesAccountHelper(
        app: Application,
        tokenStore: SharedPreferencesTokenStore
    ): AccountHelper {
        // Get UserAgent and OAuth2 data from AndroidManifest.xml
        val provider = ManifestAppInfoProvider(app.applicationContext)

        // Ideally, this should be unique to every device
        val deviceUuid = UUID.randomUUID()
        return AndroidHelper.accountHelper(provider, deviceUuid, tokenStore).apply {
            val data = TreeMap<String, PersistedAuthData>(tokenStore.data())
            val usernames = ArrayList<String>(data.keys)
            if (usernames.isNotEmpty()) {
                this.switchToUser(usernames[0])
            }
        }
    }

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )
}
