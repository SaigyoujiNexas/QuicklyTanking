package com.example.modulespublic.common.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileUtil {
    companion object{
        @JvmStatic
        fun ImageFileToMultpartBody(file: File): MultipartBody.Part {
            val requestFile =
                file.asRequestBody("image".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData("image", file.name, requestFile)
        }
    }
}