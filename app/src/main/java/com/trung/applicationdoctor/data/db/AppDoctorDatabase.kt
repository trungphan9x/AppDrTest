package com.trung.applicationdoctor.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.trung.applicationdoctor.data.db.AppDoctorDatabase.Companion.DB_VERSION
import com.trung.applicationdoctor.data.db.dao.ChannelListDao
import com.trung.applicationdoctor.data.db.dao.ChannelDetailDao
import com.trung.applicationdoctor.data.db.dao.ChannelCategoryDao
import com.trung.applicationdoctor.data.db.entity.ChannelListEntity
import com.trung.applicationdoctor.data.db.entity.ChannelDetailEntity
import com.trung.applicationdoctor.data.db.entity.ChannelCategoryEntity

@Database(entities = [ChannelListEntity::class, ChannelCategoryEntity::class, ChannelDetailEntity::class], version = DB_VERSION)
abstract class AppDoctorDatabase : RoomDatabase() {
    abstract fun getChannelListDao(): ChannelListDao
    abstract fun getChannelCategoryDao(): ChannelCategoryDao
    abstract fun getChannelDetailDao() : ChannelDetailDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        const val DB_VERSION = 1
        private const val DB_NAME = "app_doctor_database"

        @Volatile
        private var INSTANCE: AppDoctorDatabase? = null

        fun getInstance(context: Context): AppDoctorDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDoctorDatabase::class.java, DB_NAME)
                .addMigrations(MIGRATION_1_TO_2)
                .build()

        private val MIGRATION_1_TO_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

    }
}