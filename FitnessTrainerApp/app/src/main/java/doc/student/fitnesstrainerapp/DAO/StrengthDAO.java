package doc.student.fitnesstrainerapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import doc.student.fitnesstrainerapp.Entities.StrengthTypeEntity;

@Dao
public interface StrengthDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(StrengthTypeEntity entity);

    @Update
    void update(StrengthTypeEntity entity);

    @Delete
    void delete(StrengthTypeEntity entity);

    @Query("DELETE FROM strength_table")
    void deleteAllWorkouts();

    @Query("SELECT * FROM strength_table ORDER BY workoutID ASC")
    List<StrengthTypeEntity> getAllWorkouts();

}
