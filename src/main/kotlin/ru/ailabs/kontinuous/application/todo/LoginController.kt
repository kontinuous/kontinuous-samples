package ru.ailabs.kontinuous.application.todo

import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.helper.render
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.helper.asMap
import ru.ailabs.kontinuous.application.todo.model.User
import java.io.Serializable
import ru.ailabs.kontinuous.controller.Redirect
import ru.ailabs.kontinuous.auth.authenticate
import ru.ailabs.kontinuous.auth.unauthenticate

/**
 * User: andrew
 * Date: 04.03.13
 * Time: 12:13
 */

object LoginController {
    val loginGet = Action({ ctx ->
        Ok(render("user/login.tmpl.html"))
    })

    val loginPost = Action({ ctx ->
        val form = ctx.body.asMap()
        val user = ctx.session.get(javaClass<User>(), form["user"] as Serializable) as User
        if(user == null) {
            Redirect("/login")
        } else {
            if (user.authenticate(form["pass"]!!)) {
                ctx.userSession.authenticate(user.name!!)
                Redirect("/")
            } else {
                Redirect("/login")
            }
        }
    })

    val logout = Action({ ctx ->
        ctx.userSession.unauthenticate()
        Redirect("/login")
    })
}