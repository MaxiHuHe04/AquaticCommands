package me.maxih.aquaticcommands.framework.util

import org.bukkit.entity.Player

/**
  * Created by Maxi H. on 11.08.2018
  */
case class ParsedArgument(parsed: Any) {

  def vec3: Option[Vec3] = this.as[Vec3]

  def player: Option[Player] = this.as[Player]

  def string: Option[String] = this.as[String]

  def int: Option[Int] = this.as[Int]

  def long: Option[Long] = this.as[Long]

  def short: Option[Short] = this.as[Short]

  def byte: Option[Byte] = this.as[Byte]

  def double: Option[Double] = this.as[Double]

  def float: Option[Float] = this.as[Float]

  def as[T: Manifest]: Option[T] = parsed match {
    case t: T => Some(t)
    case _ => None
  }

}
