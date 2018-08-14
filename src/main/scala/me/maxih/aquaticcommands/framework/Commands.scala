package me.maxih.aquaticcommands.framework

import me.maxih.aquaticcommands.framework.util.Implicits.RichOption
import org.bukkit.Bukkit
import org.bukkit.command.{CommandExecutor, CommandSender, TabCompleter, Command => BukkitCommand}

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
  * Created by Maxi H. on 11.08.2018
  */
object Commands extends CommandExecutor with TabCompleter {
  val commands: ListBuffer[Command] = new ListBuffer

  def register(commands: Command*): Unit = {
    commands.withFilter(cmd => getCommand(cmd.rootNode.name).isDefined).map(_.rootNode.name).foreach(cmd => throw new RuntimeException(s"Command $cmd is already registered!"))
    commands.map(_.rootNode.name).map(Bukkit.getPluginCommand).foreach(cmd => {
      cmd.setExecutor(this)
      cmd.setTabCompleter(this)
    })
    this.commands.appendAll(commands)
  }

  def getCommand(label: String): Option[Command] = this.commands.find(_.rootNode.name == label)

  override def onCommand(sender: CommandSender, command: BukkitCommand, alias: String, args: Array[String]): Boolean = getCommand(command.getName).mapOrElse(_.onCommand(sender, args), false)

  override def onTabComplete(sender: CommandSender, command: BukkitCommand, alias: String, args: Array[String]): java.util.List[String] = getCommand(command.getName).mapOrElse(_.onTabComplete(sender, args), Seq()).asJava
}
