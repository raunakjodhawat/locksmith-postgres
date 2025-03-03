package com.raunakjodhawat.locksmith.postgres.config

import pureconfig.{ConfigReader, ConfigSource}

case class Properties(
  serverName: String,
  portNumber: String,
  databaseName: String,
  user: String,
  password: String
)

case class PostgresConfig(
  connectionPool: String,
  dataSourceClass: String,
  properties: Properties,
  numThreads: Int
)

case class DBConfig(
  postgres: PostgresConfig
) derives ConfigReader

// to Make Config singleton
object DBConfig {
  def apply(): Option[DBConfig] = ConfigSource.default.at("locksmith").load[DBConfig].toOption
}
