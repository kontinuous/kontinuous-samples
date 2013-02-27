package ru.ailabs.kontinuous.application.todo

import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.helper.render
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.application.todo.model.User
import ru.ailabs.kontinuous.controller.helper.asMap
import ru.ailabs.kontinuous.controller.Redirect
import java.io.Serializable
import ru.ailabs.kontinuous.auth.authenticate
import ru.ailabs.kontinuous.auth.unauthenticate

/**
 * User: andrew
 * Date: 27.02.13
 * Time: 11:07
 */


object UserController {

    val new = Action({
        Ok(render("user/new.tmpl.html"))
    })

    val create = Action({ ctx ->
        val user = User()
        val form = ctx.body.asMap()
        user.name = form["user"];
        user.password = form["pass"];
        ctx.session.save(user)
        Redirect("/login")
    })

    val list = Action({ ctx ->
        val query = ctx.session.createQuery("from User")
        val list = query?.list() as List<out User>
        Ok(render("user/list.tmpl.html", hashMapOf("users" to list)))
    })

    val loginGet = Action({ ctx ->
        Ok(render("user/login.tmpl.html"))
    })

    val loginPost = Action({ ctx ->
        val form = ctx.body.asMap()
        val user = ctx.session.get(javaClass<User>(), form["user"] as Serializable) as User
        if(user == null) {
            Redirect("/login")
        } else {
            if (form["pass"] == user.password) {
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