package com.example.aiins.util

import android.content.Context
import android.util.Log
import java.io.File

object FileUtil {

    private lateinit var filePath: String
    private lateinit var cachePath: String
    private const val TAG = "FileUtil"

    fun init(context: Context) {
        filePath = "${context.filesDir.absolutePath}/"
        cachePath = "${context.cacheDir.absolutePath}/"
        Log.i(TAG, "init: $filePath  $cachePath")
    }

    private fun getPathInFile(string: String): String {
        return filePath + string
    }

    private fun getPathInCache(string: String): String {
        return cachePath + string
    }

    fun readFileInFiles(name: String): ByteArray {
        val path = getPathInFile(name)
        val file = File(path)
        return if (!file.exists()) {
            Log.e(TAG, "readFile: $path")
            ByteArray(0)
        } else {
            file.readBytes()
        }
    }

    private fun writeToFile(data: ByteArray, path: String) {
        val file = File(path)
        if (!file.exists()) {
            val parent = file.parentFile
            if (parent != null && !parent.exists()) {
                parent.mkdirs()
            }
            file.createNewFile()
        }
        file.writeBytes(data)
    }

    fun writeFileInFiles(data: ByteArray, name: String) {
        val path = getPathInFile(name)
        writeToFile(data, path)
    }

    fun writeFileInCache(data: ByteArray, name: String) {
        val path = getPathInCache(name)
        writeToFile(data, path)
    }

    fun checkExistInFiles(name: String): Boolean {
        val path = getPathInFile(name)
        return File(path).exists()
    }
}