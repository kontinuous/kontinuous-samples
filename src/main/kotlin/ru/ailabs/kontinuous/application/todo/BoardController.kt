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
        val newBoard = context.body.asJson(javaClass<Board>())!!
        val oldBoard = context.session.get(javaClass<Board>(), newBoard.id as Serializable) as Board
        oldBoard.name = newBoard.name

        for(sharedUser in oldBoard.sharedUsers) {
            val find = newBoard.sharedUsers.filter { user -> user.name == sharedUser.name }
            if(find.count() == 0) {
                oldBoard.sharedUsers.remove(sharedUser)
            }
        }
        context.session.flush()
        context.session.refresh(oldBoard)

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
    })

    val show = Action ({ context ->
        val board = context.session.get(javaClass<Board>(), context.namedParameters["bid"]!!.toLong() as Serializable) as Board
        Ok(render_json(board))
    })

    val shared = Action ({ context ->
        val board = context.session.get(javaClass<Board>(), context.namedParameters["bid"]!!.toLong() as Serializable) as Board
        val sharedWith = board.sharedUsers
        Ok(render_json(sharedWith))
    })
}
