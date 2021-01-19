package com.example.ulessondemo.util

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.Registry
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.signature.ObjectKey
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

data class VideoThumbnail(val path: String)

/**
 * Adopted from https://gist.github.com/stepango/5edcbdb408b0ba87f8383f868961c257
 */
@com.bumptech.glide.annotation.GlideModule
class VideoThumbnailModule : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(
            VideoThumbnail::class.java,
            InputStream::class.java,
            VideoThumbnailLoader.Factory()
        )
    }
}

internal class VideoThumbnailLoader : ModelLoader<VideoThumbnail, InputStream> {
//    override fun getResourceFetcher(model: VideoThumbnail, width: Int, height: Int) = VideoThumbnailFetcher(model)

    internal class Factory : ModelLoaderFactory<VideoThumbnail, InputStream> {
        override fun teardown() = Unit
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<VideoThumbnail, InputStream> =
            VideoThumbnailLoader()
    }

    override fun buildLoadData(
        model: VideoThumbnail,
        width: Int,
        height: Int,
        options: Options
    ): LoadData<InputStream>? {
        return LoadData(ObjectKey(model), VideoThumbnailFetcher(model))
    }

    override fun handles(model: VideoThumbnail): Boolean = true
}

internal class VideoThumbnailFetcher(val model: VideoThumbnail) : DataFetcher<InputStream> {
    var stream: InputStream? = null
    var retriever: MediaMetadataRetriever? = null

    @Volatile
    var cancelled = false

//    override fun getId(): String = model.path

    override fun cleanup() = try {
        retriever?.release()
        stream?.close()
    } catch (e: Exception) {
        // Just Ignore it
    } ?: Unit

    override fun cancel() {
        cancelled = true
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        retriever = MediaMetadataRetriever()
        try {
            retriever!!.setDataSource(model.path, emptyMap())
            if (cancelled) return
            val picture = retriever!!.frameAtTime
            if (cancelled) return
            if (picture != null) {
                val bitmapData = ByteArrayOutputStream().use { bos ->
                    picture.compress(Bitmap.CompressFormat.JPEG, 90, bos)
                    bos.toByteArray()
                }
                if (cancelled) return
                stream = ByteArrayInputStream(bitmapData)
                return callback.onDataReady(stream)
            }
        } finally {
            retriever?.release()
        }
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }
}