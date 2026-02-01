package com.example.alwanyapp.DI;
import com.example.alwanyapp.Data.AuthRepo.LoginRepo;
import com.example.alwanyapp.Data.AuthRepo.RegistrationRepo;
import com.example.alwanyapp.Data.SaveImageRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    LoginRepo getLoginRepo(FirebaseAuth auth, DatabaseReference database)
    {
        return new LoginRepo(auth,database);
    }

    @Provides
    @Singleton
    RegistrationRepo getRegistrationRepo(FirebaseAuth auth, DatabaseReference database)
    {
        return new RegistrationRepo(auth,database);
    }

    @Provides
    @Singleton
    SaveImageRepo getSaveImageRepo()
    {
        return new SaveImageRepo();
    }

    @Singleton
    @Provides
    DatabaseReference getDatabaseReference()
    {
        return FirebaseDatabase.getInstance().getReference();
    }
    @Singleton
    @Provides
    FirebaseAuth getFirebaseAuth()
    {
        return FirebaseAuth.getInstance();
    }
}
