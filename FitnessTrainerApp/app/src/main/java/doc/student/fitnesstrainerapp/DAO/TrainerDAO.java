package doc.student.fitnesstrainerapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import doc.student.fitnesstrainerapp.Entities.TrainerEntity;

@Dao
public interface TrainerDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TrainerEntity entity);

    @Update
    void update(TrainerEntity entity);

    @Delete
    void delete(TrainerEntity entity);

    @Query("DELETE FROM trainer_table")
    void deleteAllTrainers();

    @Query("SELECT * FROM trainer_table ORDER BY trainerID ASC")
    List<TrainerEntity> getAllTrainers();

}
