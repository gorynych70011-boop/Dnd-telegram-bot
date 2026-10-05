package ru.dndbot.command;

public class AuthorCommand implements ru.dndbot.command.Command {
    @Override
    public String name() {
        return "author";
    }
    @Override
    public String shortDescription() {
        return "информация об авторах";
    }
    @Override
    public String usage(){
        return "/author - выводит список авторов бота и их контакты.";
    }
    @Override
    public String execute(String args){
        return """
                Авторы бота:
                • Фамилия Имя — @AGPT
                • Фамилия Имя — @budulayhvh""";
    }
}
