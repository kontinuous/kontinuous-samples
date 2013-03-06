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
import ru.ailabs.kontinuous.persistance.HibernateSession
import ru.ailabs.kontinuous.application.todo.model.User
import java.io.Serializable

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
                get("/users/profile", UserController.profile)
                get("/users/shared", UserController.listShared)
                get("/users", UserController.list)
                get("/users/:uid", UserController.show)
                get("/users/:uid/edit", UserController.edit)
                post("/users/:uid", UserController.update)
                post("/users", UserController.create)

                get("/boards", BoardController.list)
                get("/boards/:bid", BoardController.show)
                post("/boards/:bid", BoardController.update)
                post("/boards", BoardController.create)

            }
            get("/logout", LoginController.logout)
            get("/login", LoginController.loginGet)
            post("/login", LoginController.loginPost)
            initialize {
                HibernateSession.wrap {
                    val admin = session.get(javaClass<User>(), "admin" as Serializable)
                    if (admin == null) {
                        val user = User()
                        user.name = "admin"
                        user.password("admin")
                        session.save(user)
                    }
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    val server = NettyServer()
    server.start()
}
