/*
 * Copyright (c) 2012 Alexey Lunacharsky
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

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