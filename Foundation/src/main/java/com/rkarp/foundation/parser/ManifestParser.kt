package com.rkarp.foundation.parser

import org.springframework.context.ApplicationContext
import java.io.IOException
import java.util.jar.Manifest

class ManifestParser {
    companion object {
        fun getManifestFromContext(context: ApplicationContext) : Manifest {
            val resourceAsStream = null //context.getResourceAsStream("/META-INF/MANIFEST.MF")
            val mf = Manifest()
            try {
                mf.read(resourceAsStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return mf
        }

        fun readManifestProperty(manifest: Manifest, key: String) : String? {
            return if (manifest.mainAttributes != null) {
                manifest.mainAttributes.getValue(key)
            } else {
                null
            }
        }
    }
}