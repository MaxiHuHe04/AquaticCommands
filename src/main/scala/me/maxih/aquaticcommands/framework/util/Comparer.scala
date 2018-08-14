package me.maxih.aquaticcommands.framework.util

/**
  * Created by Maxi H. on 27.07.2018
  */
object Comparer {

  /**
    * @param full   The full string
    * @param string The string which `full` should begin with
    * @return `true` when `full` begins with or is `string`
    */
  def partialMatch(full: String, string: String, matchCase: Boolean = true): Boolean = {
    if (string.length > full.length) return false

    for (i <- string.indices)
      if ((matchCase && string.charAt(i) != full.charAt(i)) || (!matchCase && string.charAt(i).toLower != full.charAt(i).toLower)) return false

    true
  }

}
