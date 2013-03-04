package ru.ailabs.kontinuous.application.todo.model

/**
 * User: andrew
 * Date: 18.02.13
 * Time: 10:35
 */

data class Task {
    public enum class Status {
        pending
        inProgress
        done
    }
    public var id: Long? = null
    public var name: String? = null
    public var status: Status = Status.pending
    public var board: Board? = null
}