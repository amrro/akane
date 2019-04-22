package app.akane.data

import android.app.Application
import android.os.Debug
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [APIModule::class])
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Application): AkaneDb {
        val builder = Room.databaseBuilder(
            context,
            AkaneDb::class.java,
            "akane_reddit.db"
        ).fallbackToDestructiveMigration()

        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Provides
    fun providePostDao(db: AkaneDb) = db.postDao()

    @Provides
    fun provideImagePreviewDao(db: AkaneDb) = db.imagePreviewDao()

    @Provides
    fun provideSubredditDao(db: AkaneDb) = db.subredditDao()
}