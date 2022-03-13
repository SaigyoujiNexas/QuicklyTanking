package com.example.modulescore.main.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.lang.annotation.Annotation;

//数据库持有者，并作为与应用持久关联数据的底层连接的主要访问点。在运行时，
//通过Room.databaseBuilder() 或者 Room.inMemoryDatabaseBuilder()获取Database实例。
@Database(entities = {RunningRecord.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase Instance;
    public MyDataBase() {

    }

    // 单实例模式，节省资源
    public static MyDataBase getsInstance(Context context) {
        if (Instance == null) {
            synchronized (MyDataBase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDataBase.class, "runningDb").build();
                }
            }
        }
        return Instance;
    }

    // 定义获取Dao的方法
    public abstract RunningDao runningDao();

}
