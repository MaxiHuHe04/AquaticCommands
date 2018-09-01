package me.maxih.aquaticcommands.framework

import me.maxih.aquaticcommands.framework.util.ParsedArgument
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.command.CommandSender

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by Maxi H. on 27.07.2018
  */
trait Command {
  val rootNode: LiteralNode

  @throws[CommandException]
  def execute(sender: CommandSender, arguments: Map[String, ParsedArgument], literals: Seq[String]): Unit

  def onCommand(sender: CommandSender, args: Seq[String]): Boolean = {
    val parsedArguments = new mutable.HashMap[String, ParsedArgument]()
    val literals = new ListBuffer[String]
    val lastArgumentPlaceholder: String = "\u0000"

    try {
      val lastNodes = parseCommand(sender, args, rootNode.children, parsedArguments, literals)
      if (!lastNodes.exists(_._2.executable) && !(args.isEmpty && rootNode.executable)) throw new ParseException("Unknown command", lastArgumentPlaceholder)

      this.execute(sender, parsedArguments.toMap, literals)
    } catch {
      case ex: ParseException =>
        val msg = new TextComponent(ex.getMessage)
        msg.setColor(ChatColor.RED)
        sender.spigot().sendMessage(msg)

        val commandLine = Seq(rootNode.name) ++ args
        val trace = commandLine.slice(0, Math.max(if (ex.argument.equals(lastArgumentPlaceholder)) commandLine.length else commandLine.indexOf(ex.argument), 1))
        val traceComponent = new TextComponent(trace.slice(trace.length - 6, trace.length).mkString(" "))
        traceComponent.setColor(ChatColor.GRAY)
        val traceError = new TextComponent((if (ex.argument.nonEmpty && ex.argument != lastArgumentPlaceholder) " " + ex.argument else "") + "<--[HERE]")
        traceError.setColor(ChatColor.RED)

        sender.spigot().sendMessage(new TextComponent(traceComponent, traceError))

      case CommandException(message) =>
        val msg = new TextComponent(message)
        msg.setColor(ChatColor.RED)

        sender.spigot().sendMessage(msg)

      case ex: Throwable => ex.printStackTrace()
    }
    true
  }

  def onTabComplete(sender: CommandSender, arguments: Seq[String]): Seq[String] = {
    val lastNodes = getLastNodes(sender, arguments, rootNode.children)
    lastNodes.flatMap {
      case (arg, node) => node.complete(sender, arg)
    }
  }


  private def parseCommand(sender: CommandSender, arguments: Seq[String], currentNodes: Seq[CommandNode], parsed: mutable.HashMap[String, ParsedArgument], literals: ListBuffer[String]): Seq[(String, CommandNode)] = {
    val nodes = currentNodes
      .filter(node => arguments.slice(0, node.positions).nonEmpty)
      .filter(node => node.applicable(sender, arguments.slice(0, node.positions).mkString(" ")))
      .map(node => (arguments.slice(0, node.positions).mkString(" "), node))

    for ((arg, node) <- nodes if node.isInstanceOf[ArgumentNode]) {
      val result = node.asInstanceOf[ArgumentNode].parser.parse(sender, arg)
      parsed(node.name) = ParsedArgument(result)
    }

    for ((arg, node) <- nodes if node.isInstanceOf[LiteralNode]) {
      if (!node.name.equals(arg)) throw new ParseException("Incorrect argument for command", arg)
      literals += node.name
    }

    val nextNodes = new ListBuffer[(String, CommandNode)]

    nextNodes.appendAll(nodes.filter({
      case (_, node) => arguments.length <= node.positions
    }))

    nextNodes.appendAll(nodes.filter({
      case (_, node) => arguments.length > node.positions
    }).flatMap({
      case (_, node) => parseCommand(sender, arguments.slice(node.positions, arguments.length), node.children, parsed, literals)
    }))

    nextNodes
  }


  private def getLastNodes(sender: CommandSender, arguments: Seq[String], currentNodes: Seq[CommandNode]): Seq[(String, CommandNode)] = {
    val nodes = currentNodes
      .filter(node => node.applicable(sender, arguments.slice(0, node.positions).mkString(" ")))
      .map(node => (arguments.slice(0, node.positions).mkString(" "), node))

    val nextNodes = new ListBuffer[(String, CommandNode)]

    nextNodes.appendAll(nodes.filter({
      case (_, node) => arguments.length <= node.positions
    }))

    nextNodes.appendAll(nodes.filter({
      case (_, node) => arguments.length > node.positions
    }).flatMap({
      case (_, node) => getLastNodes(sender, arguments.slice(node.positions, arguments.length), node.children)
    }))

    nextNodes
  }

}
