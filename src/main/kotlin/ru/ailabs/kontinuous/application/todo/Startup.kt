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
import ru.ailabs.kontinuous.auth.authenticated

class SampleApplication: Application() {

    {
        add {
            authenticated("/login") {
//                get("/st", TaskController.st)
                get("/tasks", TaskController.list)
                post("/tasks", TaskController.create)
                post("/tasks/:id", TaskController.update)
                get("/", TaskController.root)
            }
            get("/logout", UserController.logout)
            get("/login", UserController.loginGet)
            post("/login", UserController.loginPost)
            get("/users", UserController.list)
            get("/users/new", UserController.new)
            post("/users", UserController.create)
        }
    }
}

fun main(args: Array<String>) {
    val server = NettyServer()
    server.start()
}
