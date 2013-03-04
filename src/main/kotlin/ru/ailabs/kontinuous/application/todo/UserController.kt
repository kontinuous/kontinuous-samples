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

    val list = Action({ ctx ->
        val query = ctx.session.createQuery("from User")
        val list = query?.list() as List<out User>
        Ok(render("user/list.tmpl.html", hashMapOf("users" to list)))
    })

    val show = Action({ ctx ->
        val user = ctx.session.get(javaClass<User>(), ctx.namedParameters["uid"] as Serializable) as User
        Ok(render("user/show.tmpl.html", hashMapOf("user" to user)))
    })

    val update = Action({ ctx ->
        val user = ctx.session.get(javaClass<User>(), ctx.namedParameters["uid"] as Serializable) as User
        val form = ctx.body.asMap()
        user.name = form["user"];
        user.password = form["pass"];
        ctx.session.save(user);
        Redirect("/users/${user.name}")
    })

    val edit = Action({ ctx ->
        val user = ctx.session.get(javaClass<User>(), ctx.namedParameters["uid"] as Serializable) as User
        Ok(render("user/new.tmpl.html", hashMapOf("users" to list)))
    })

    val new = Action({
        Ok(render("user/new.tmpl.html"))
    })

    val create = Action({ ctx ->
        val user = User()
        val form = ctx.body.asMap()
        user.name = form["user"];
        user.password = form["pass"];
        ctx.session.save(user)
        Redirect("/users/${user.name}")
    })
}