package ru.ailabs.kontinuous.application.todo.model

import java.util.HashSet

/**
 * User: andrew
 * Date: 27.02.13
 * Time: 8:19
 */

data class User {
    public var name : String? = null
    public var password : String? = null
    public var theirsBoards : java.util.Set<Board> = HashSet<Board>() as java.util.Set<Board>
    public var boards : java.util.Set<Board> = HashSet<Board>() as java.util.Set<Board>
}