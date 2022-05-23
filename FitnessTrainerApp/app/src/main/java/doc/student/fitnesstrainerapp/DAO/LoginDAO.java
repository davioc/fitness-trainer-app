package doc.student.fitnesstrainerapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import doc.student.fitnesstrainerapp.Entities.LoginEntity;

@Dao
public interface LoginDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LoginEntity loginEntity);

    @Update
    void update(LoginEntity loginEntity);

    @Delete
    void delete(LoginEntity loginEntity);

    @Query("DELETE FROM login_table")
    void deleteAllLogins();

    @Query("SELECT * FROM login_table ORDER BY loginID ASC")
    List<LoginEntity> getAllLogins();
}
