import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId

class TelegramBot
{
    fun botAction() {
        val bot = bot {
            token = System.getenv("TELEGRAM_BOT_TOKEN") // Get from @BotFather

            dispatch {
                // Handle /start command
                command("start") {
                    bot.sendMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        text = "👋 Hello! Send me any text and I'll echo it back."
                    )
                }

                // Echo any text message
                message {
                    when
                    {
                        message.from?.username == "Aiz0r" ->
                            bot.sendMessage(
                                chatId = ChatId.fromId(message.chat.id),
                                text = "Если правильно работает, то ты вроде Кирюшка"
                            )
                        message.from?.username == "Hhhhpgoe" ->
                            bot.sendMessage(
                                chatId = ChatId.fromId(message.chat.id),
                                text = "Если правильно работает, то ты вроде толян"
                            )
                        message.from?.username == "WithABow" ->
                            bot.sendMessage(
                                chatId = ChatId.fromId(message.chat.id),
                                text = "Если правильно работает, то это я   "
                            )
                        message.from?.username == "ThatGuyFromBar" ->
                            bot.sendMessage(
                                chatId = ChatId.fromId(message.chat.id),
                                text = "Если правильно работает, то ты крутой скелет (всмысле кир Смирнов)   "
                            )
                        message.from?.username == "Paperfigurine" ->
                            bot.sendMessage(
                                chatId = ChatId.fromId(message.chat.id),
                                text = "Если правильно работает, то ты пеперфигурин   "
                            )
                    }
                }
            }
        }

        bot.startPolling() // Start listening for updates
    }
}