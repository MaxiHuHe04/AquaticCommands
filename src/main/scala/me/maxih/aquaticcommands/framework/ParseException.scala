package me.maxih.aquaticcommands.framework

/**
  * Created by Maxi H. on 27.07.2018
  */
class ParseException(message: String, val argument: String) extends RuntimeException(message)
