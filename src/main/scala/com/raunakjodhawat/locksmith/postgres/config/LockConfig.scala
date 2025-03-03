package com.raunakjodhawat.locksmith.postgres.config

import pureconfig.{ConfigReader, ConfigSource}

case class LockConfig(
  tableName: String,
  probability: Double
) extends ConfigReader

object LockConfig {
  def apply(): Option[LockConfig] = ConfigSource.default.at("locksmith").load[LockConfig].toOption
}
