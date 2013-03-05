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

                get("/", TaskController.root)

                get("/boards/:bid/tasks", TaskController.list)
                post("/boards/:bid/tasks", TaskController.create)
                post("/boards/:bid/tasks/:tid", TaskController.update)

                //user
                get("/users/new", UserController.new)
                get("/users", UserController.list)
                get("/users/:uid", UserController.show)
                get("/users/:uid/edit", UserController.edit)
                post("/users/:uid", UserController.update)
                post("/users", UserController.create)

                get("/boards", BoardController.list)
                get("/boards/:bid", BoardController.show)
                get("/boards/:bid/shared_with", BoardController.shared)
                post("/boards/:bid", BoardController.update)
                post("/boards", BoardController.create)

            }
            get("/logout", LoginController.logout)
            get("/login", LoginController.loginGet)
            post("/login", LoginController.loginPost)
        }
    }
}

fun main(args: Array<String>) {
    val server = NettyServer()
    server.start()
}
