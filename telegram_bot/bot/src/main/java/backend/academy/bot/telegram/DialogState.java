package backend.academy.bot.telegram;

public enum DialogState {
    IDLE, // Без активного диалога
    WAITING_FOR_TAGS, // Ожидание тегов
    WAITING_FOR_FILTERS // Ожидание фильтров
}
