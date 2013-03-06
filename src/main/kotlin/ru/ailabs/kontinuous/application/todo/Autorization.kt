package ru.ailabs.kontinuous.application.todo

import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.SimpleResult
import ru.ailabs.kontinuous.controller.Context
import ru.ailabs.kontinuous.controller.Forbidden
import ru.ailabs.kontinuous.application.todo.model.Board
import ru.ailabs.kontinuous.application.todo.model.User
import java.io.Serializable
import ru.ailabs.kontinuous.auth.getUserId

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 06.03.13
 * Time: 15:17
 */
class AuthorizeOwnerAndShared(val businessHandler: (Context) -> SimpleResult) : Action(businessHandler) {
    override public val handler: ((Context) -> SimpleResult) = { context ->
        val board = context.session.get(javaClass<Board>(), context.namedParameters["bid"]!!.toLong() as Serializable) as Board
        val user = context.session.get(javaClass<User>(), context.userSession.getUserId() as Serializable) as User
        val canProcess = board.owner?.name == user.name || board.sharedUsers.filter({ sharedUser -> sharedUser.name == user.name }).notEmpty()

        if(canProcess) {
            businessHandler(context)
        } else {
            Forbidden()
        }
    }
}

class AuthorizeOwnerOrAdmin(val businessHandler: (Context) -> SimpleResult) : Action(businessHandler) {
    override public val handler: ((Context) -> SimpleResult) = { context ->
        val currentUser = context.session.get(javaClass<User>(), context.userSession.getUserId() as Serializable) as User
        val uid = context.namedParameters["uid"]

        var canProcess = false
        val isAdmin = if(currentUser.isAdmin == null) false else currentUser.isAdmin!!
        if(isAdmin) {
            canProcess = true
        } else {
            if (uid != null){
                val user = context.session.get(javaClass<User>(), uid as Serializable) as User
                if (currentUser.name == user.name) {
                    canProcess = true
                }
            }
        }

        if(canProcess) {
            businessHandler(context)
        } else {
            Forbidden()
        }
    }
}

class AuthorizeAdmin(val businessHandler: (Context) -> SimpleResult) : Action(businessHandler) {
    override public val handler: ((Context) -> SimpleResult) = { context ->
        val currentUser = context.session.get(javaClass<User>(), context.userSession.getUserId() as Serializable) as User

        var canProcess = if(currentUser.isAdmin == null) false else currentUser.isAdmin!!

        if(canProcess) {
            businessHandler(context)
        } else {
            Forbidden()
        }
    }
}