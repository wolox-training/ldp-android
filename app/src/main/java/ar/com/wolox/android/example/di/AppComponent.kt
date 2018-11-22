package ar.com.wolox.android.example.di

import android.app.Application

import ar.com.wolox.android.example.NewsApplication
import ar.com.wolox.android.example.di.module.MiscModule
import ar.com.wolox.android.example.ui.viewpager.ViewPagerActivityModule
import ar.com.wolox.wolmo.core.di.modules.ContextModule
import ar.com.wolox.wolmo.core.di.modules.DefaultModule
import ar.com.wolox.wolmo.core.di.scopes.ApplicationScope
import ar.com.wolox.wolmo.networking.di.NetworkingComponent

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(dependencies = [NetworkingComponent::class],
        modules = [AndroidSupportInjectionModule::class, DefaultModule::class, ContextModule::class,
            AppModule::class, ViewPagerActivityModule::class, MiscModule::class])
interface AppComponent : AndroidInjector<NewsApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<NewsApplication>() {

        @BindsInstance
        abstract fun application(application: Application): Builder

        @BindsInstance
        abstract fun sharedPreferencesName(sharedPrefName: String): Builder

        abstract fun networkingComponent(networkingComponent: NetworkingComponent): Builder
    }
}
