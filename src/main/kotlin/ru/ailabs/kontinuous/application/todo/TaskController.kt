package ru.ailabs.kontinuous.application.todo

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.HashMap
import java.util.HashSet
import org.hibernate.Query
import ru.ailabs.kontinuous.application.todo.model.Task
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.helper.render_json

/**
 * User: andrew
 * Date: 18.02.13
 * Time: 11:45
 */

object TaskController {
    val create = Action ({ context ->
        val mapper = ObjectMapper()
        var task = mapper.readValue(context.body, javaClass<Task>())
        context.session.save(task)
        Ok(render_json(task!!))
    })

    val update = Action ({ context ->
        val mapper = ObjectMapper()
        var task = mapper.readValue(context.body, javaClass<Task>())
        context.session.saveOrUpdate(task)
        Ok(render_json(task!!))
    })

    val list =  Action ({ context ->
        val query : Query? = context.session.createQuery("from Task")
        val map = HashMap<Task.Status, HashSet<Task>>()
        val list = query?.list() as List<out Any>
        list forEach {(it: Any) ->
            val task = it as Task
            val tasks = map.getOrPut(task.status, {HashSet<Task>()})
            tasks.add(task)
        }
        Ok(render_json(map))
    })
}
