package ru.ailabs.kontinuous.application.todo

import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Redirect
import ru.ailabs.kontinuous.controller.helper.asJson
import ru.ailabs.kontinuous.application.todo.model.Task
import ru.ailabs.kontinuous.controller.helper.render_json
import ru.ailabs.kontinuous.controller.Ok
import org.hibernate.Query
import ru.ailabs.kontinuous.auth.getUserId
import ru.ailabs.kontinuous.controller.helper.asMap
import ru.ailabs.kontinuous.application.todo.model.Board
import ru.ailabs.kontinuous.application.todo.model.User
import java.io.Serializable
import ru.ailabs.kontinuous.controller.helper.render

/**
 * User: andrew
 * Date: 04.03.13
 * Time: 11:16
 */

object BoardController {

    val create = Action ({ context ->
        val form = context.body.asMap()
        val board = Board()
        board.name = form["name"]
        board.owner = context.session.get(javaClass<User>(), context.userSession.getUserId() as Serializable) as User
        context.session.save(board)
        Redirect("/users/${board.owner!!.name}")
    })

    val show = Action ({ context ->
        val board = context.session.get(javaClass<Board>(), context.namedParameters["bid"]!!.toLong() as Serializable) as Board
        Ok(render("board/show.tmpl.html", hashMapOf("board" to board)))
    })

    val share = Action ({ context ->
        val form = context.body.asMap()
        val board = context.session.get(javaClass<Board>(), context.namedParameters["bid"]!!.toLong() as Serializable) as Board
        val user = context.session.get(javaClass<User>(), form["user"] as Serializable) as User
        user.theirsBoards.add(board)
        context.session.save(user)
        Ok(render("board/show.tmpl.html", hashMapOf("board" to board)))
    })
}
