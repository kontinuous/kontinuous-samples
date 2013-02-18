package ru.ailabs.kontinuous.application.todo

import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.helper.render
import ru.ailabs.kontinuous.application.todo.model.Task
import org.hibernate.LockMode
import java.io.Serializable

/**
 * User: andrew
 * Date: 18.02.13
 * Time: 11:45
 */

object TaskController {
    val index =  Action ({ context ->


        val task = Task()
        task.id = "1"
        task.name = "Task"
        context.session.save(task)
        val saved = context.session.get(javaClass<Task>(), "1" as Serializable? ) as Task
        context.session.flush()
        Ok(render("ru/ailabs/kontinuous/application/todo/index.tmpl.html", hashMapOf(Pair("name", saved.name!!))))
    })
}
