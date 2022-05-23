package doc.student.fitnesstrainerapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import doc.student.fitnesstrainerapp.DAO.CardioDAO;
import doc.student.fitnesstrainerapp.DAO.CustomerDAO;
import doc.student.fitnesstrainerapp.DAO.LoginDAO;
import doc.student.fitnesstrainerapp.DAO.SessionDAO;
import doc.student.fitnesstrainerapp.DAO.StrengthDAO;
import doc.student.fitnesstrainerapp.DAO.TrainerDAO;
import doc.student.fitnesstrainerapp.DAO.WorkoutDetailsDAO;
import doc.student.fitnesstrainerapp.Entities.CardioTypeEntity;
import doc.student.fitnesstrainerapp.Entities.CustomerEntity;
import doc.student.fitnesstrainerapp.Entities.LoginEntity;
import doc.student.fitnesstrainerapp.Entities.SessionEntity;
import doc.student.fitnesstrainerapp.Entities.StrengthTypeEntity;
import doc.student.fitnesstrainerapp.Entities.TrainerEntity;
import doc.student.fitnesstrainerapp.Entities.WorkoutDetailsEntity;

@Database(entities = {CardioTypeEntity.class, CustomerEntity.class, SessionEntity.class, StrengthTypeEntity.class, TrainerEntity.class, LoginEntity.class, WorkoutDetailsEntity.class}, version = 7, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract CardioDAO cardioDAO();
    public abstract CustomerDAO customerDAO();
    public abstract SessionDAO sessionDAO();
    public abstract StrengthDAO strengthDAO();
    public abstract TrainerDAO trainerDAO();
    public abstract LoginDAO loginDAO();
    public abstract WorkoutDetailsDAO workoutDetailsDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "MyDatabase.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
