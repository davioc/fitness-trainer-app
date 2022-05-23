package doc.student.fitnesstrainerapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import doc.student.fitnesstrainerapp.Entities.CustomerEntity;

@Dao
public interface CustomerDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CustomerEntity customerEntity);

    @Update
    void update(CustomerEntity customerEntity);

    @Delete
    void delete(CustomerEntity customerEntity);

    @Query("DELETE FROM customer_table")
    void deleteAllCustomers();

    @Query("SELECT * FROM customer_table ORDER BY customerID ASC")
    List<CustomerEntity> getAllCustomers();

}
