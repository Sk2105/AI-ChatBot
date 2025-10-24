package com.composeapp.aibotapp.di

import android.content.Context
import androidx.room.Room
import com.composeapp.aibotapp.data.auth.AuthImplementation
import com.composeapp.aibotapp.data.db.MessageDB
import com.composeapp.aibotapp.data.db.dao.MessageDao
import com.composeapp.aibotapp.domain.auth.AuthRepository
import com.composeapp.aibotapp.domain.auth.usecase.GoogleSignInUseCase
import com.composeapp.aibotapp.domain.db.repository.MessageRepository
import com.composeapp.aibotapp.utils.BASE_API_KEY
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MessageDB {
        return Room.databaseBuilder(
            context.applicationContext,
            MessageDB::class.java,
            "message_db"
        ).build()
    }

    @Provides
    fun provideMessageDao(db: MessageDB) = db.messageDao()

    @Provides
    fun provideMessageRepository(dao: MessageDao) = MessageRepository(dao)


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun providesAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthImplementation(auth)
    }

    @Provides
    fun providesAuthUseCase(authRepository: AuthRepository): GoogleSignInUseCase {
        return GoogleSignInUseCase(authRepository)
    }


    @Provides
    fun providesGeminiAI(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = BASE_API_KEY
        )
    }




}