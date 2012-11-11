package com.katlex.util

import java.io.{IOException, FileOutputStream, File, InputStream}
import java.util.jar.JarFile
import java.util.zip.ZipEntry
import util.control.Exception.{catching, noCatch, allCatch}

object StreamImplicits {
  case class InputStreamOps(input:InputStream) {
    def ==> (sink: File):Option[InputStream] = catching(classOf[IOException]).opt {
      val buffer = Array.ofDim[Byte](4092)
      sink.getParentFile.mkdirs()
      val output = new FileOutputStream(sink)

      def copyNext: Unit = {
        val read = input.read(buffer)
        if (read != -1) {
          output.write(buffer, 0, read)
          copyNext
        }
      }

      (noCatch andFinally output.close())(copyNext)
      input
    }
  }

  case class JarFileOps(jarFile:JarFile) {
    def unzipEntry(entry:ZipEntry, sink:File):Option[Unit] =
      (jarFile.getInputStream(entry) ==> sink).flatMap(is => allCatch.opt(is.close()))
  }

  implicit def inputStreamOps(input:InputStream):InputStreamOps = InputStreamOps(input)
  implicit def jarFileOps(jarFile:JarFile):JarFileOps = JarFileOps(jarFile)
}