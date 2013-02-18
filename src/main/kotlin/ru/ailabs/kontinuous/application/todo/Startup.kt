package ru.ailabs.kontinuous.application.todo

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 05.02.13
 * Time: 18:14
 */
import ru.ailabs.kontinuous.NettyServer

fun main(args: Array<String>) {
    val server = NettyServer()
    server.start()
}