package doc.student.fitnesstrainerapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import doc.student.fitnesstrainerapp.Entities.WorkoutDetailsEntity;

@Dao
public interface WorkoutDetailsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WorkoutDetailsEntity entity);

    @Update
    void update(WorkoutDetailsEntity entity);

    @Delete
    void delete(WorkoutDetailsEntity entity);

    @Query("DELETE FROM workout_details_table")
    void deleteAllWorkouts();

    @Query("SELECT * FROM workout_details_table ORDER BY workoutID ASC")
    List<WorkoutDetailsEntity> getAllWorkouts();

}
