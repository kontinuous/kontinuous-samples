package ru.ailabs.kontinuous.example

import ru.ailabs.kontinuous.annotation.path
import ru.ailabs.kontinuous.annotation.routes
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.Redirect

object Controller {
    val index =  Action ({
        Ok("index view")
    })

    val post = Action ({
        Redirect("/")
    })

    val named = Action({ context ->
        Ok("named parameter ${context.namedParameters["name"]}")
    })
}

routes class Routes {

    path("/")  val index = Controller.index
    path("/post")  val post = Controller.post
    path("/post/:name") val named = Controller.named
}
