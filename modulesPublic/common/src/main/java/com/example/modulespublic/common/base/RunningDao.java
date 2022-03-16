package com.example.modulespublic.common.base;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RunningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertRunningRecord(RunningRecord... record);//插入多个数据,冲突策略是取代旧数据同时继续事务
    @Insert
    public void insertBothRunningRecords(RunningRecord record1, RunningRecord record2);
    @Update
    public void updateRunningRecordss(RunningRecord... records);
    @Delete
    public void deleteRunningRecordss(RunningRecord... records);
    @Query("SELECT * FROM RunningRecord")
    public RunningRecord[] loadAllRunningRecordss();
    @Query("SELECT * FROM RunningRecord where id = :id")
    public RunningRecord queryRunningRecordById(Long id);
}
