package me.maxih.aquaticcommands.framework

/**
  * Created by Maxi H. on 11.08.2018
  */
case class CommandException(message: String) extends RuntimeException(message)