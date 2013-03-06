package ru.ailabs.kontinuous.application.todo.model

import java.util.HashSet
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.UUID
import ru.ailabs.kontinuous.auth.SecuredUser

/**
 * User: andrew
 * Date: 27.02.13
 * Time: 8:19
 */

data class User : SecuredUser() {
    public var name : String? = null
    JsonIgnore public var password : String? = null
    JsonIgnore public var salt : String? = null
    JsonIgnore public var isAdmin : Boolean? = false
    JsonIgnore public var theirsBoards : java.util.Set<Board> = HashSet<Board>() as java.util.Set<Board>
    JsonIgnore public var boards : java.util.Set<Board> = HashSet<Board>() as java.util.Set<Board>

    override fun passwordAndKey(password: String, key: String) {
        this.password = password
        this.salt = key
    }
    override fun key(): String? = salt
    override fun password(): String = password!!
}