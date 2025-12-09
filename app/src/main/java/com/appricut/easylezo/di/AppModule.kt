package com.appricut.easylezo.di

import com.appricut.easylezo.data.repo.AuthRepository
import com.appricut.easylezo.data.repo.CategoryRepository
import com.appricut.easylezo.data.repo.SentenceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ) = AuthRepository(auth, db)

    @Singleton
    @Provides
    fun provideCategoryRepository(
        db: FirebaseFirestore
    ) = CategoryRepository(db)

    @Singleton
    @Provides
    fun provideSentenceRepository(
        db: FirebaseFirestore
    ) = SentenceRepository(db)
}
