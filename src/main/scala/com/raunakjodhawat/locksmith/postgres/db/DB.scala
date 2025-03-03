package com.raunakjodhawat.locksmith.postgres.db

import cats.effect.IO
import cats.effect.unsafe.IORuntime
import com.raunakjodhawat.locksmith.postgres.config.{DBConfig, LockConfig, LocksmithConfig}
import com.raunakjodhawat.locksmith.postgres.db.DB.lockConfig

import scala.concurrent.duration.FiniteDuration

object DB {
  private val config: IO[LocksmithConfig] = IO.fromOption(DBConfig())(new Exception("Unable to load config"))
  private val lockConfig: IO[LockConfig] = IO.fromOption(LockConfig())(new Exception("Unable to load lock config"))

  private def createTable(): Unit = {}
  def lock(key: String, instanceId: String, duration: FiniteDuration)(implicit runtime: IORuntime): Boolean = IO
    .parSequenceN(2)(List(config, lockConfig))
    .map { case List(locksmithConfig, lockConfig) =>
      (locksmithConfig, lockConfig)
    }
    .unsafeRunSync() match {
    case (Some(locksmithConfig), Some(lockConfig)) => true
    case _                                         => false
  }
  // get the two config in parallel

  // create a table if it does not exist
  // id: PrimaryKey, AutoInc
  // key: String, Unique
  // instanceId: String
  // createdAt: Timestamp
  // auto delete expired locks

  // attempt to write to DB with key, if successful return true else false

}
