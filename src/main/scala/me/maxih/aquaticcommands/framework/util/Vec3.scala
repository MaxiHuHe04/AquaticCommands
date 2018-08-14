package me.maxih.aquaticcommands.framework.util

import me.maxih.aquaticcommands.framework.util.Vec3.Coordinate
import org.bukkit.Location

/**
  * Created by Maxi H. on 27.07.2018
  */
object Vec3 {

  case class Coordinate(coord: Double, relative: Boolean = false) {
    def getAbsolute(current: Double): Double = if (relative) current + coord else coord
  }

}

case class Vec3(x: Coordinate, y: Coordinate, z: Coordinate) {
  def toLocation(current: Location): Location = new Location(current.getWorld, x.getAbsolute(current.getX), y.getAbsolute(current.getY), z.getAbsolute(current.getZ))
}
