package com.withABow.freelancePlatform.telegram

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.withABow.freelancePlatform.services.OrderService
import com.withABow.freelancePlatform.services.UserService
import jakarta.annotation.PostConstruct
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class TelegramBot(
    private val userService: UserService,
    private val orderService: OrderService
) {

    data class OrderFlow(
        var university: String = "",
        var professor: String = "",
        var jobTitle: String = "",
        var notes: String = ""
    )

    enum class OrderState {
        AWAITING_UNI,
        AWAITING_PROF,
        AWAITING_TITLE,
        AWAITING_NOTES,
        AWAITING
    }

    private val activeFlows = mutableMapOf<Long, Pair<OrderState, OrderFlow>>()

    @PostConstruct
    fun init() {
        Executors.newSingleThreadExecutor().submit { botAction() } //TODO REVIEW
    }

    fun botAction() {

        val bot = bot {
            token = System.getenv("TELEGRAM_BOT_TOKEN")

            dispatch {
                command("start") {
                    bot.sendMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        text = "👋 Welcome! Use /register to sign up, or /order to create a request if you have registered before"
                    )
                }

                command("register") {
                    try {
                        userService.createUser(message.from?.id.toString(), message.from?.username)
                        bot.sendMessage(
                            chatId = ChatId.fromId(message.chat.id),
                            text = "✅ You are now registered"
                        )
                    } catch (_: DataIntegrityViolationException) {
                        bot.sendMessage(
                            chatId = ChatId.fromId(message.chat.id),
                            text = "⚠️ You are already registered!"
                        )
                    }
                }

                command("order") {

                    val chatId = message.chat.id

                    if (userService.getUserByName(message.from!!.id.toString()) == null) {
                        bot.sendMessage(
                            chatId = ChatId.fromId(chatId),
                            text = "❌ You are not registered, register with /register before placing an order"
                        )
                        return@command
                    }

                    val count = orderService.getOrders().count { order ->
                        order.createdBy == userService.getUserByName(message.from?.id.toString()) && order.takenBy == null
                    }

                    if (count >= 3) {
                        bot.sendMessage(
                            chatId = ChatId.fromId(chatId),
                            text = "❌ You have already placed 3 orders, please wait before placing another"
                        )
                        return@command
                    }

                    activeFlows[chatId] = OrderState.AWAITING to OrderFlow()
                    bot.sendMessage(
                        chatId = ChatId.fromId(chatId),
                        text = "🎓 Type your university:"
                    )
                }

                command("cancel") {
                    val chatId = message.chat.id
                    activeFlows.remove(chatId)
                    bot.sendMessage(
                        chatId = ChatId.fromId(chatId),
                        text = "❌ Order cancelled."
                    )
                }

                text {
                    val chatId = message.chat.id
                    val inputText = message.text?.trim() ?: return@text

                    // Если у пользователя нет активного заказа — игнорируем сообщения
                    val currentStatePair = activeFlows[chatId] ?: return@text
                    val (state, flow) = currentStatePair

                    when (state) {
                        OrderState.AWAITING ->
                        {
                            flow.university = inputText
                            activeFlows[chatId] = OrderState.AWAITING_UNI to flow

                        }

                        OrderState.AWAITING_UNI -> {

                            flow.university = inputText

                            bot.sendMessage(
                                chatId = ChatId.fromId(chatId),
                                text = "👨‍🏫 Type your professor's name:"
                            )

                            activeFlows[chatId] = OrderState.AWAITING_PROF to flow
                        }

                        OrderState.AWAITING_PROF -> {
                            flow.professor = inputText

                            activeFlows[chatId] = OrderState.AWAITING_TITLE to flow

                            bot.sendMessage(
                                chatId = ChatId.fromId(chatId),
                                text = "📝 What's the job title or task?"
                            )
                        }

                        OrderState.AWAITING_TITLE -> {
                            flow.jobTitle = inputText

                            activeFlows[chatId] = OrderState.AWAITING_NOTES to flow

                            bot.sendMessage(
                                chatId = ChatId.fromId(chatId),
                                text = "🗒️ Any extra notes? (Type 'skip' to ignore)"
                            )
                        }

                        OrderState.AWAITING_NOTES -> {
                            flow.notes = if (inputText.equals("skip", ignoreCase = true)) "" else inputText

                            try {
                                orderService.createOrder(
                                    createdBy = userService.getUserByName(message.from!!.id.toString())!!,
                                    title = flow.jobTitle,
                                    uni = flow.university,
                                    professor = flow.professor,
                                    notes = flow.notes
                                )
                                bot.sendMessage(
                                    chatId = ChatId.fromId(chatId),
                                    text = "✅ Request submitted successfully!"
                                )
                            } catch (e: Exception) {
                                bot.sendMessage(
                                    chatId = ChatId.fromId(chatId),
                                    text = "❌ Error creating order: ${e.message}"
                                )
                                return@text
                            }

                            activeFlows.remove(chatId)
                        }

                    }
                }

            }
        }
        bot.startPolling()
    }
}
