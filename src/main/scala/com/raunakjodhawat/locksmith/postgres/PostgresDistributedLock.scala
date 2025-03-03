package com.raunakjodhawat.locksmith.postgres

import cats.effect.unsafe.IORuntime
import com.raunakjodhawat.locksmith.core.{toHash, DistributedLock, InputLockKey}
import com.raunakjodhawat.locksmith.postgres.db.DB

import scala.concurrent.duration.{DurationInt, FiniteDuration}

private object PostgresDistributedLock {
  private lazy val init: Unit = DB.createTable()
  def apply() = init
}
class PostgresDistributedLock(instanceId: String)(implicit IORuntime: IORuntime) extends DistributedLock {
  PostgresDistributedLock() // Ensure the table is created when an instance is created

  override def lock(input: InputLockKey, duration: FiniteDuration = 1.minute): Boolean =
    DB.lock(input.toHash, instanceId, duration)
}
