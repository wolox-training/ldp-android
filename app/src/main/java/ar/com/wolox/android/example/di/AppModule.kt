package ar.com.wolox.android.example.di

import ar.com.wolox.android.example.ui.detail.NewFullScreenActivity
import ar.com.wolox.android.example.ui.detail.NewsDetailActivity
import ar.com.wolox.android.example.ui.detail.NewsDetailFragment
import ar.com.wolox.android.example.ui.example.ExampleActivity
import ar.com.wolox.android.example.ui.example.ExampleFragment
import ar.com.wolox.android.example.ui.home.HomeActivity
import ar.com.wolox.android.example.ui.home.news.NewsFragment
import ar.com.wolox.android.example.ui.home.profile.ProfileFragment
import ar.com.wolox.android.example.ui.login.LoginActivity
import ar.com.wolox.android.example.ui.login.LoginFragment
import ar.com.wolox.android.example.ui.signup.SignUpActivity
import ar.com.wolox.android.example.ui.signup.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ContributesAndroidInjector
    internal abstract fun exampleActivity(): ExampleActivity

    @ContributesAndroidInjector
    internal abstract fun exampleFragment(): ExampleFragment

    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun signUpActivity(): SignUpActivity

    @ContributesAndroidInjector
    internal abstract fun signUpFragment(): SignUpFragment

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun newsFragment(): NewsFragment

    @ContributesAndroidInjector
    internal abstract fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector
    internal abstract fun newsDetailActivity(): NewsDetailActivity

    @ContributesAndroidInjector
    internal abstract fun newsDetailFragment(): NewsDetailFragment

    @ContributesAndroidInjector
    internal abstract fun newsFullScreenActivity(): NewFullScreenActivity
}
