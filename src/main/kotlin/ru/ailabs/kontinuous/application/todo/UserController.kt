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
import ru.ailabs.kontinuous.controller.helper.render_json
import ru.ailabs.kontinuous.auth.getUserId

/**
 * User: andrew
 * Date: 27.02.13
 * Time: 11:07
 */


object UserController {

    val list = Action({ ctx ->
        val query = ctx.session.createQuery("from User")
        val list = query?.list() as List<User>
        Ok(render_json(list))
    })

    val profile = Action({ context ->
        val user = context.session.get(javaClass<User>(), context.userSession.getUserId() as Serializable) as User
        Ok(render_json(user))
    })


    val listShared = Action ({ context ->
        val user = context.session.get(javaClass<User>(), context.userSession.getUserId() as Serializable) as User
        Ok(render_json(user.theirsBoards))
    })

    val show = Action({ ctx ->
        val user = ctx.session.get(javaClass<User>(), ctx.namedParameters["uid"] as Serializable) as User
        Ok(render("user/show.tmpl.html", hashMapOf("user" to user)))
    })

    val update = AuthorizeOwnerOrAdmin({ ctx ->
        val user = ctx.session.get(javaClass<User>(), ctx.namedParameters["uid"] as Serializable) as User
        val form = ctx.body.asMap()
        user.name = form["user"];
        user.password(form["pass"]!!);
        ctx.session.save(user);
        Redirect("/users/${user.name}")
    })

    val edit = AuthorizeOwnerOrAdmin({ ctx ->
        val user = ctx.session.get(javaClass<User>(), ctx.namedParameters["uid"] as Serializable) as User
        Ok(render("user/edit.tmpl.html", hashMapOf("user" to user)))
    })

    val new = AuthorizeAdmin({
        Ok(render("user/new.tmpl.html"))
    })

    val create = AuthorizeAdmin({ ctx ->
        val user = User()
        val form = ctx.body.asMap()
        user.name = form["user"];
        user.password(form["pass"]!!);
        ctx.session.save(user)
        Redirect("/users/${user.name}")
    })
}