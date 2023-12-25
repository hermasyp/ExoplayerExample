package com.catnip.exoplayerexample.di

import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.FileDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSink
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheEvictor
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import com.catnip.exoplayerexample.data.datasource.VideosDataSource
import com.catnip.exoplayerexample.data.datasource.VideosDataSourceImpl
import com.catnip.exoplayerexample.presentation.main.MainViewModel
import com.catnip.exoplayerexample.presentation.player.ExoPlayerManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object AppModules {
    fun getModules(): List<Module> = listOf(exoplayerModule, viewModelModule, dataSourceModule)

    private val exoplayerModule = module {
        single<File> { File(androidContext().cacheDir, "exo_cache") }
        single<CacheEvictor> { NoOpCacheEvictor() }
        single<DatabaseProvider> { StandaloneDatabaseProvider(androidContext()) }
        single<Cache> {
            SimpleCache(get(), get(), get())
        }

        single<DataSource.Factory> {
            CacheDataSource.Factory()
                .setCache(get())
                .setCacheWriteDataSinkFactory(CacheDataSink.Factory().setCache(get()))
                .setCacheReadDataSourceFactory(FileDataSource.Factory())
                .setUpstreamDataSourceFactory(
                    DefaultDataSource.Factory(
                        androidContext(),
                        DefaultHttpDataSource.Factory()
                    )
                )
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        }

        single<MediaSource.Factory> {
            DefaultMediaSourceFactory(androidContext()).setDataSourceFactory(get())
        }
        factory { ExoPlayer.Builder(androidContext()).build() }
        factory { ExoPlayerManager(get(),get()) }

    }

    private val viewModelModule = module {
        viewModelOf(::MainViewModel)
    }

    private val dataSourceModule = module {
        single<VideosDataSource> { VideosDataSourceImpl() }
    }

}