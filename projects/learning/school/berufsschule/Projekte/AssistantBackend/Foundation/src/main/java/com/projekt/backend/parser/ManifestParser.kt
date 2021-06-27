package com.projekt.backend.parser

import java.io.IOException
import java.util.jar.Manifest
import javax.servlet.ServletContext

class ManifestParser {
    companion object {
        fun getManifestFromContext(context: ServletContext) : Manifest {
            val resourceAsStream = context.getResourceAsStream("/META-INF/MANIFEST.MF")
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