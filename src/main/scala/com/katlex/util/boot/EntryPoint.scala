package com.katlex.util.boot

import xsbti.{AppConfiguration, AppMain}

trait EntryPoint {
  def run(args:Array[String]): Int
  def main(args:Array[String]) { System.exit(run(args)) }
}

case class LauncherAdapter(ep:EntryPoint) extends AppMain {
  case class Exit(val code: Int) extends xsbti.Exit
  def run(config: AppConfiguration) = Exit(ep.run(config.arguments))
}