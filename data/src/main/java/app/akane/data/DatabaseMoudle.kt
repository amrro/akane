package app.akane.data

import android.app.Application
import android.os.Debug
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseMoudle {

    @Singleton
    @Provides
    fun provideDatabase(context: Application): AkaneDataBase {
        val builder = Room.databaseBuilder(
            context,
            AkaneDataBase::class.java,
            "akane_reddit.db"
        ).fallbackToDestructiveMigration()

        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Provides
    fun providePostDao(db: AkaneDataBase) = db.postDao()

    @Provides
    fun provideImagePreviewDao(db: AkaneDataBase) = db.imagePreviewDao()

    @Provides
    fun provideSubredditDao(db: AkaneDataBase) = db.subredditDao()
}