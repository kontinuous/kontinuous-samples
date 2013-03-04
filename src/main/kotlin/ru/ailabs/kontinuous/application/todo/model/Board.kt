package ru.ailabs.kontinuous.application.todo.model

/**
 * User: andrew
 * Date: 04.03.13
 * Time: 10:38
 */

data class Board {
    public var id: Long? = null
    public var name: String? = null
    public var owner: User? = null
    public var sharedUsers : Set<User> = setOf()

    public fun isOwner(user : User): Boolean {
        return user.name.equals(owner?.name)
    }
}