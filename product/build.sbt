name := """product"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "net.sf.barcode4j" % "barcode4j" % "2.0"
libraryDependencies ++= Seq(evolutions, jdbc)
libraryDependencies += "com.typesafe.play" %% "anorm" % "2.5.3"
libraryDependencies += "com.h2database" % "h2" % "1.4.192"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
