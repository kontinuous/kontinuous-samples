package ru.ailabs.kontinuous.application.todo

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 05.02.13
 * Time: 18:14
 */
import ru.ailabs.kontinuous.NettyServer
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.configuration.Configuration

class SampleApplication: Application() {

    {
        add {
            get("/tasks", TaskController.list)
            post("/tasks", TaskController.create)
            post("/tasks/:id", TaskController.update)
            get("/", TaskController.root)
        }
    }
}

fun main(args: Array<String>) {
    val server = NettyServer()
    server.start()
}
