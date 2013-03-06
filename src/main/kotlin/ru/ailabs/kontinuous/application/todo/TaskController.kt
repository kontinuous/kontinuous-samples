package ru.ailabs.kontinuous.application.todo

import org.hibernate.Query
import ru.ailabs.kontinuous.application.todo.model.Task
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Cookie
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.Redirect
import ru.ailabs.kontinuous.controller.helper.asJson
import ru.ailabs.kontinuous.controller.helper.render_json
import ru.ailabs.kontinuous.auth.getUserId
import ru.ailabs.kontinuous.application.todo.model.User
import java.io.Serializable
import ru.ailabs.kontinuous.application.todo.model.Board
import ru.ailabs.kontinuous.controller.Forbidden

/**
 * User: andrew
 * Date: 18.02.13
 * Time: 11:45
 */

object TaskController {

    val root = Action ({
        Redirect("/assets/index.html")
    })

    val create = AuthorizeOwnerAndShared ({ context ->
        val board = context.session.get(javaClass<Board>(), context.namedParameters["bid"]!!.toLong() as Serializable) as Board
        val task = context.body.asJson(javaClass<Task>())!!
        task.board = board
        context.session.save(task)
        Ok(render_json(task))
    })

    val update = AuthorizeOwnerAndShared ({ context ->
        var task = context.body.asJson(javaClass<Task>())
        context.session.saveOrUpdate(task)
        Ok(render_json(task!!))
    })

    val list =  AuthorizeOwnerAndShared ({ context ->
        val query : Query = context.session.createQuery("from Task task where task.board.id = :board_id")!!
        query.setLong("board_id", context.namedParameters["bid"]!!.toLong())
        val list = query?.list() as List<out Any>
        Ok(render_json(list))
    })
}
