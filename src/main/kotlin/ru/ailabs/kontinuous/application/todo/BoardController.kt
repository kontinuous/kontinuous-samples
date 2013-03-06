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
import java.util.HashMap
import ru.ailabs.kontinuous.controller.Forbidden

/**
 * User: andrew
 * Date: 04.03.13
 * Time: 11:16
 */

object BoardController {

    val list =  Action ({ context ->
        val query : Query = context.session.createQuery("from Board board where board.owner.name = :user_id")!!
        query.setString("user_id", context.userSession.getUserId())
        //val map = HashMap<Task.Status, HashSet<Task>>()
        val list = query?.list() as List<out Any>
        Ok(render_json(list))
    })

    val create = Action ({ context ->
        val board = context.body.asJson(javaClass<Board>())!!
        val user = context.session.get(javaClass<User>(), context.userSession.getUserId() as Serializable) as User
        board.owner = user
        context.session.save(board)
        Ok(render_json(board))
    })

    val update = Action ({ context ->
        // TODO: refactor it to autorization
        val board = context.session.get(javaClass<Board>(), context.namedParameters["bid"]!!.toLong() as Serializable) as Board
        val user = context.session.get(javaClass<User>(), context.userSession.getUserId() as Serializable) as User
        var canProcess = board.owner?.name == user.name

        if(canProcess) {
            val newBoard = context.body.asJson(javaClass<Board>())!!
            context.session.evict(newBoard)
            val oldBoard = context.session.get(javaClass<Board>(), newBoard.id as Serializable) as Board
            oldBoard.name = newBoard.name

            val finded = oldBoard.sharedUsers.filter { oldUser ->
                val find = newBoard.sharedUsers.filter { newUser -> newUser.name == oldUser.name }
                find.count() == 0
            }
            for(sharedUser in finded) {
                oldBoard.sharedUsers.remove(sharedUser)
            }

            for(sharedUser in newBoard.sharedUsers) {
                val find = oldBoard.sharedUsers.filter { user -> user.name == sharedUser.name }
                if(find.count() == 0) {
                    val userName = sharedUser.name
                    val user = context.session.get(javaClass<User>(), userName as Serializable) as User
                    oldBoard.sharedUsers.add(user)
                }
            }

            context.session.save(oldBoard)
            context.session.flush()
            Ok(render_json(oldBoard))
        } else {
            Forbidden()
        }
    })

    val show = AuthorizeOwnerAndShared ({ context ->
        val board = context.session.get(javaClass<Board>(), context.namedParameters["bid"]!!.toLong() as Serializable) as Board
        Ok(render_json(board))
    })
}
