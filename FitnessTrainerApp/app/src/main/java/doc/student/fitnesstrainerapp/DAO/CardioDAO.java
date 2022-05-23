package doc.student.fitnesstrainerapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import doc.student.fitnesstrainerapp.Entities.CardioTypeEntity;

@Dao
public interface CardioDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CardioTypeEntity cardioTypeEntity);

    @Update
    void update(CardioTypeEntity cardioTypeEntity);

    @Delete
    void delete(CardioTypeEntity cardioTypeEntity);

    @Query("DELETE FROM cardio_table")
    void deleteAllWorkouts();

    @Query("SELECT * FROM cardio_table ORDER BY workoutID ASC")
    List<CardioTypeEntity> getAllWorkouts();

}
