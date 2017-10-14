package com.company

class EventBus private constructor(){
    private val events = mutableListOf<Event>()

    fun subscribe(event: Event) {
        events.add(event)
    }

    fun unsubscribe(event: Event) {
        events.forEach {
            if (it.equals(event)){
                events.remove(event)
            }
        }
    }

    fun post(event: Event) {
        for (e in events) {
            e.OnEvent(event)
        }
    }

    //
    // Singleton
    //


    private object holder { val INSTANCE = EventBus() }

    companion object {
        val instance: EventBus by lazy { holder.INSTANCE }
    }

}