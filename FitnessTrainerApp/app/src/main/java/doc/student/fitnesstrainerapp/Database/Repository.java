package doc.student.fitnesstrainerapp.Database;

import android.app.Application;

import java.util.List;

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

public class Repository {
    private CardioDAO mCardio;
    private CustomerDAO mCustomer;
    private SessionDAO mSession;
    private StrengthDAO mStrength;
    private TrainerDAO mTrainer;
    private LoginDAO mLogin;
    private WorkoutDetailsDAO mWorkout;

    private List<CardioTypeEntity> allCardioWorkouts;
    private List<CustomerEntity> allCustomers;
    private List<SessionEntity> allSessions;
    private List<StrengthTypeEntity> allStrengthWorkouts;
    private List<TrainerEntity> allTrainers;
    private List<LoginEntity> allLogins;
    private List<WorkoutDetailsEntity> allWorkouts;

    public Repository(Application application) {
        DatabaseBuilder databaseBuilder = DatabaseBuilder.getDatabase(application);
        mCardio = databaseBuilder.cardioDAO();
        mCustomer = databaseBuilder.customerDAO();
        mSession = databaseBuilder.sessionDAO();
        mStrength = databaseBuilder.strengthDAO();
        mTrainer = databaseBuilder.trainerDAO();
        mLogin = databaseBuilder.loginDAO();
        mWorkout = databaseBuilder.workoutDetailsDAO();
    }

    public List<CardioTypeEntity> getAllCardioWorkouts() {
        allCardioWorkouts = mCardio.getAllWorkouts();
        return allCardioWorkouts;
    }

    public List<CustomerEntity> getAllCustomers() {
        allCustomers = mCustomer.getAllCustomers();
        return allCustomers;
    }

    public List<SessionEntity>  getAllSessions() {
        allSessions = mSession.getAllSessions();
        return allSessions;
    }

    public List<StrengthTypeEntity> getAllStrengthWorkouts() {
        allStrengthWorkouts = mStrength.getAllWorkouts();
        return allStrengthWorkouts;
    }

    public List<TrainerEntity>  getAllTrainers() {
        allTrainers = mTrainer.getAllTrainers();
        return allTrainers;
    }

    public List<LoginEntity>  getAllLogins() {
        allLogins = mLogin.getAllLogins();
        return allLogins;
    }

    public List<WorkoutDetailsEntity> getAllWorkouts() {
        allWorkouts = mWorkout.getAllWorkouts();
        return allWorkouts;
    }


    //Inserts
    public void insert(CardioTypeEntity cardioTypeEntity) {
        mCardio.insert(cardioTypeEntity);
    }

    public void insert(CustomerEntity customerEntity) {mCustomer.insert(customerEntity);}

    public void insert(SessionEntity sessionEntity) {mSession.insert(sessionEntity);}

    public void insert(StrengthTypeEntity strengthTypeEntity) {mStrength.insert(strengthTypeEntity);}

    public void insert(TrainerEntity trainerEntity) {mTrainer.insert(trainerEntity);}

    public void insert(LoginEntity loginEntity) {mLogin.insert(loginEntity);}

    public void insert(WorkoutDetailsEntity workoutDetailsEntity) {mWorkout.insert(workoutDetailsEntity);}


    //Updates
    public void update(CardioTypeEntity cardioTypeEntity) {mCardio.update(cardioTypeEntity);}

    public void update(CustomerEntity customerEntity) {mCustomer.update(customerEntity);}

    public void update(SessionEntity sessionEntity) {mSession.update(sessionEntity);}

    public void update(StrengthTypeEntity strengthTypeEntity) {mStrength.update(strengthTypeEntity);}

    public void update(TrainerEntity trainerEntity) {mTrainer.update(trainerEntity);}

    public void update(LoginEntity loginEntity) {mLogin.update(loginEntity);}

    public void update(WorkoutDetailsEntity workoutDetailsEntity) {mWorkout.update(workoutDetailsEntity);}

    //Deletes
    public void delete(CardioTypeEntity cardioTypeEntity)  {mCardio.delete(cardioTypeEntity);}

    public void delete(CustomerEntity customerEntity)  {mCustomer.delete(customerEntity);}

    public void delete(SessionEntity sessionEntity) {mSession.delete(sessionEntity);}

    public void delete(StrengthTypeEntity strengthTypeEntity)   {mStrength.delete(strengthTypeEntity);}

    public void delete(TrainerEntity trainerEntity) {mTrainer.delete(trainerEntity);}

    public void delete(LoginEntity loginEntity) {mLogin.delete(loginEntity);}

    public void delete(WorkoutDetailsEntity workoutDetailsEntity) {mWorkout.delete(workoutDetailsEntity);}
}
