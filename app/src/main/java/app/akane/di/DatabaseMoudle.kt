package app.akane.di

import android.app.Application
import android.os.Debug
import androidx.room.Room
import app.akane.data.db.AkaneDataBase
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

}