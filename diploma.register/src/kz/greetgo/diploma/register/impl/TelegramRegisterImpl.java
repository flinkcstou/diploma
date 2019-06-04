package kz.greetgo.diploma.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.diploma.controller.model.TelegramMenu;
import kz.greetgo.diploma.controller.register.TelegramRegister;
import kz.greetgo.diploma.register.dao.TelegramDao;
import kz.greetgo.diploma.register.model.PersonLogin;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Bean
public class TelegramRegisterImpl extends TelegramLongPollingBot implements TelegramRegister {

	public BeanGetter<TelegramDao> telegramDao;

	public Map<String, String> mapInlineText = new HashMap<>();

	@Override
	public void setButtons(SendMessage sendMessage) {

		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboardRowList = new ArrayList<>();
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(new KeyboardButton("/help"));

		keyboardFirstRow.add(new KeyboardButton("/using"));
		KeyboardRow keyboardSecondRow = new KeyboardRow();
		keyboardSecondRow.add(new KeyboardButton("/info"));
		keyboardSecondRow.add(new KeyboardButton("/website"));
		keyboardRowList.add(keyboardFirstRow);
		keyboardRowList.add(keyboardSecondRow);
		replyKeyboardMarkup.setKeyboard(keyboardRowList);

	}

	@Override
	public void sendMsg(Message message, String text) {

		SendMessage sendMessage = new SendMessage();

		sendMessage.enableMarkdown(true);

		sendMessage.setChatId(message.getChatId());

		sendMessage.setReplyToMessageId(message.getMessageId());

		sendMessage.setText(text);
		try
			{

				setButtons(sendMessage);
				sendMessage(sendMessage);

			} catch(TelegramApiException e)
			{
				e.printStackTrace();
			}
	}

	@Override
	public String getNameFromDb(String name) throws IOException {

		name = name.toLowerCase();
		PersonLogin selectByUsername = telegramDao.get().selectByUsername(name);
		System.out.println("telegram bot uses postgres");
		if(selectByUsername == null)
			{
				return "there is no such user";
			}
		return selectByUsername.toString();
	}

	public String getTelegramMenuDay(String dayAndMonth) throws IOException, ParseException {

		ArrayList<TelegramMenu> telegramMenus = new ArrayList<>();

		String year = "2019-";
		String fullDate = year + dayAndMonth;


		if(isDateValid(fullDate))
			{
				System.out.println("telegram bot uses postgres");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(fullDate);
				telegramMenus = telegramDao.get().selectTelegramMenuDay(new Timestamp(date.getTime()));
				mapInlineText = SortTypeAndNameInlineText(telegramMenus);
				return null;
//				return SortTypeAndName(telegramMenus);
			} else
			{
				return "Неправильный формат даты.\nПример формат даты: 	\nДля первого апреля : \n'04-01'.";
			}

	}

	boolean isDateValid(String date) throws ParseException {

		if(!date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"))
			return false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try
			{
				sdf.parse(date);
				return true;
			} catch(ParseException e)
			{
				return false;
			}
	}

	public String SortTypeAndName(List<TelegramMenu> telegramMenus) {

		Map<String, String> asd = new HashMap<>();


		for(TelegramMenu telegramMenu : telegramMenus)
			{
				if(!asd.containsKey(telegramMenu.foodType))
					{
						asd.put(telegramMenu.foodType, "");
					}

				String s = asd.get(telegramMenu.foodType);

				String value = s + " " + telegramMenu.foodName + ".\n";

				asd.put(telegramMenu.foodType, value);

			}


		StringBuilder result = new StringBuilder();


		for(Map.Entry<String, String> entry : asd.entrySet())
			{
				result.append(entry.getKey()).append(" :  \n").append(entry.getValue()).append("\n");
			}

		if(result.length() == 0)
			{
				result.append("К сожалению на этот день не составили меню дня.");
			}

		return result.toString();
	}

	public Map<String, String> SortTypeAndNameInlineText(List<TelegramMenu> telegramMenus) {

		Map<String, String> asd = new HashMap<>();


		for(TelegramMenu telegramMenu : telegramMenus)
			{
				if(!asd.containsKey(telegramMenu.foodType))
					{
						asd.put(telegramMenu.foodType, "");
					}

				String s = asd.get(telegramMenu.foodType);

				String value = s + " " + telegramMenu.foodName + ": \t" + telegramMenu.price + ".\n";

				asd.put(telegramMenu.foodType, value);

			}

		return asd;
	}

	@Override
	public String startBot(Message message) {

		String fullName = message.getFrom().getFirstName() + " " + message.getFrom().getLastName();
		String firstMessage = "Добро пожаловать " + fullName + ". Вас приветсвует бот: " + getBotUsername();
		return firstMessage;
	}


	@Override
	public void setInlineButtons(Message message, String text) {


		SendMessage sendMessage = new SendMessage();


		sendMessage.setChatId(message.getChatId());

//		sendMessage.setReplyToMessageId(message.getMessageId());


		if(mapInlineText.size() != 0)
			{
				if(text != null)
					{
						sendMessage.setText(text);
					}
				if(text == null)
					{
						sendMessage.setText("menu-day");
						InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
						sendMessage.setReplyMarkup(markupInline);
						List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

						for(Map.Entry<String, String> entry : mapInlineText.entrySet())
							{
								List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
								rowInline1.add(new InlineKeyboardButton().setText(entry.getKey()).setCallbackData(entry.getKey()).setUrl(""));
								rowsInline.add(rowInline1);
							}

						markupInline.setKeyboard(rowsInline);
					}

				try
					{
						sendMessage(sendMessage);

					} catch(TelegramApiException e)
					{
						e.printStackTrace();
					}

			} else
			{
				sendMessage.enableMarkdown(true);
				sendMessage.setText(text);
				try
					{

						setButtons(sendMessage);
						sendMessage(sendMessage);

					} catch(TelegramApiException e)
					{
						e.printStackTrace();
					}
			}


	}

	@Override
	public void setLinkPage(Message message, String text) {


		SendMessage sendMessage = new SendMessage();


		sendMessage.setChatId(message.getChatId());

		sendMessage.setText(text);
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		sendMessage.setReplyMarkup(markupInline);
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		rowInline1.add(new InlineKeyboardButton().setText("E-Order").setUrl("https://diplomaproject.info/e-order"));
		rowsInline.add(rowInline1);
		markupInline.setKeyboard(rowsInline);
		try
			{
				sendMessage(sendMessage);

			} catch(TelegramApiException e)
			{
				e.printStackTrace();
			}


	}

	public void sendMsgFromCallBack(CallbackQuery callbackQuery, String text) {


		SendMessage sendMessage = new SendMessage();

		sendMessage.enableMarkdown(true);

		sendMessage.setChatId(callbackQuery.getMessage().getChatId());

//		sendMessage.setReplyToMessageId(callbackQuery.getMessage().getMessageId());

		sendMessage.setText(text);


		try
			{

				setButtons(sendMessage);
				sendMessage(sendMessage);

			} catch(TelegramApiException e)
			{
				e.printStackTrace();
			}


	}


	@Override
	public void onUpdateReceived(Update update) {

		Message message = update.getMessage();
		if(message != null && message.hasText())
			{
				switch(message.getText())
					{
						case "/start":
							sendMsg(message, startBot(message));
							break;
						case "/help":
							sendMsg(message, "Как пользоватся этим ботом ? \n" +
								"/help -Помощь Пользователю \n" +
								"/using - Что делает бот\n" +
								"/info - Информация о боте\n" +
								"/website - Сайт где вы можете в режиме реального времени делать свой заказ не ожидая официанта, \nзаранее забронировать место и сделать предзаказ, \nуслуга заказа еды на вынос");
							break;
						case "/using":
							sendMsg(message,
								/*"1) Что делает бот? \nНужно просто написать ваше имя или имя друга \n" +
									"1.1) возвращает ваш уникальный id\n" +
									"1.2) возвращает ваше зашифрованное письмо" +*/
								"1) напишите день и месяц в формате месяц-день, и вы можете получить меню дня");
							break;
						case "/info":
							sendMsg(message, "Бот разработан в 2019 году 10 марта. \nдля Дипломной Работы.");
							break;
						case "/website":
							setLinkPage(message, "Сайт где вы можете сделать заказ в режиме реального времени");
							break;
						default:
							try
								{
//									sendMsg(message, getNameFromDb(message.getText()));
									try
										{
//											sendMsg(message, getTelegramMenuDay(message.getText()));
											setInlineButtons(message, getTelegramMenuDay(message.getText()));
										} catch(ParseException e)
										{
											e.printStackTrace();
										}
								} catch(IOException e)
								{
									e.printStackTrace();
								}
					}
			} else
			{
				CallbackQuery callbackQuery = update.getCallbackQuery();

				String text = "Старые данные удалены.\n Пожулуйста, заново выберите день";
				if(mapInlineText.get(callbackQuery.getData()) != null)
					{
						text = "`" + callbackQuery.getData() + "`" + ": \n" + mapInlineText.get(callbackQuery.getData());
					}
				sendMsgFromCallBack(callbackQuery, text);

			}


	}

	@Override
	public void onUpdatesReceived(List<Update> updates) {

		for(Update update : updates)
			{
				onUpdateReceived(update);
			}
	}

	@Override
	public String getBotUsername() {

		return "booking_diploma_bot";
	}

	@Override
	public String getBotToken() {

		return "710526393:AAErOMh1RCoHX47Sz8U09XJEM6IbcInaTh8";
	}


}

