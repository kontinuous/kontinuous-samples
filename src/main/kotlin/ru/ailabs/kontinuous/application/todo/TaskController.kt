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

/**
 * User: andrew
 * Date: 18.02.13
 * Time: 11:45
 */

object TaskController {

//    val st = Action({ context ->
//        println(context.userSession.get("user_id"))
//        context.userSession.set("user_id", "khamutov")
//        Ok("cookie setted").withCookies(Cookie("mu_cookie", "12345"))
//    })

    val root = Action ({
        Redirect("/assets/index.html")
    })

    val create = Action ({ context ->
        val task = context.body.asJson(javaClass<Task>())!!
        val boardId = context.namedParameters["bid"]!!.toLong()
        val board = context.session.get(javaClass<Board>(), boardId as Serializable) as Board
        task.board = board
//        task!!.user = context.session.get(javaClass<User>(), context.userSession.getUserId() as Serializable) as User
        context.session.save(task)
        Ok(render_json(task))
    })

    val update = Action ({ context ->
        var task = context.body.asJson(javaClass<Task>())
        context.session.saveOrUpdate(task)
        Ok(render_json(task!!))
    })

    val list =  Action ({ context ->
        val query : Query = context.session.createQuery("from Task task where task.board.id = :board_id")!!
        query.setLong("board_id", context.namedParameters["bid"]!!.toLong())
        //val map = HashMap<Task.Status, HashSet<Task>>()
        val list = query?.list() as List<out Any>
        /*list forEach {(it: Any) ->
            val task = it as Task
            val tasks = map.getOrPut(task.status, {HashSet<Task>()})
            tasks.add(task)
        }*/
        Ok(render_json(list))
    })
}
