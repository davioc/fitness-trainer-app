package doc.student.fitnesstrainerapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import doc.student.fitnesstrainerapp.Entities.SessionEntity;

@Dao
public interface SessionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SessionEntity entity);

    @Update
    void update(SessionEntity entity);

    @Delete
    void delete(SessionEntity entity);

    @Query("DELETE FROM session_table")
    void deleteAllSessions();

    @Query("SELECT * FROM session_table ORDER BY sessionID ASC")
    List<SessionEntity> getAllSessions();


}
