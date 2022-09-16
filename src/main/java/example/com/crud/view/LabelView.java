package example.com.crud.view;

import example.com.crud.controller.LabelController;
import example.com.crud.model.Label;
import java.sql.SQLException;
import java.util.Scanner;

public class LabelView {
    private final Scanner scanner = new Scanner(System.in);
    private final LabelController labelController;

    public LabelView() {
        this.labelController = new LabelController();
    }

    public void show() {
        int select;

        while (true) {
            System.out.println("===========================================");
            System.out.println("Меню управления тэгами:");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Посмотреть список всех тэгов");
            System.out.println("2. Добавить новый тэг");
            System.out.println("3. Найти тэг по id");
            System.out.println("4. Изменить тэг");
            System.out.println("5. Удалить тэг");
            System.out.println("6. Выход");
            System.out.println();
            System.out.println("===========================================");
            System.out.println("Выберите пункт меню:");
            System.out.println("===========================================");
            select = scanner.nextInt();
            System.out.println("\n");

            switch (select) {
                case 1:
                    eventShowAllLabels();
                    break;

                case 2:
                    eventCreateNewLabel();
                    break;

                case 3:
                    eventGetLabelById();
                    break;

                case 4:
                    eventUpdateLabelById();
                    break;
                case 5:
                    eventDeleteLabelById();
                    break;
                case 6:
                    System.out.println("********************Работа приложения завершена!********************");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неправильно выбран пункт! Пожалуйста, повторите ввод");
                    break;
            }
        }

    }

    public void eventShowAllLabels() {
        System.out.println("==========================");
        System.out.println("Список существующих тэгов:");
        System.out.println("==========================");

        for (Label label : labelController.onShowAll()) {
            System.out.println(label.toString());
        }

        System.out.println();
        System.out.println("=======================================================================");
    }

    public void eventCreateNewLabel() {
        String nameLabel = "";

        System.out.println("=======================");
        System.out.println("Добавление нового тэга:");
        System.out.println("=======================");

        do {
            System.out.println("Введите название нового тэга: ");
            scanner.nextLine();
            nameLabel = scanner.nextLine();

            if (nameLabel.equals("")) {
                System.out.println();
                System.out.println("==============================================================");
                System.out.println("WARNING! Название тэга не может быть пустым, повторите попытку");
                System.out.println("==============================================================");
                System.out.println();
            }
        } while (nameLabel.equals(""));

        labelController.onCreate(nameLabel);
    }

    public void eventGetLabelById() {
        Long id = 0L;

        System.out.println("==========================");
        System.out.println("Введите id искомого тэга: ");
        System.out.println("==========================");

        Label getLabel;
        while (true) {
            id = scanner.nextLong();
            try {
                getLabel = labelController.getById(id);
                break;
            } catch (SQLException e) {
                System.out.println("Тэга с указанным id не существует! Повторите ввод.");

            }
        }
        System.out.println('\n');
        System.out.println("=============");
        System.out.println("ИСКОМЫЙ ТЭГ: ");
        System.out.println("======================");
        System.out.println(getLabel.toString());
        System.out.println("======================");
        System.out.println('\n');
    }

    public void eventUpdateLabelById() {
        Long id = 0L;
        String nameLabel = "";

        System.out.println("==========================================");
        System.out.println("Введите id тэга, который хотите изменить: ");
        System.out.println("==========================================");
        Label getLabel;

        while (true) {
            id = scanner.nextLong();
            try {
                getLabel = labelController.getById(id);
                break;
            } catch (SQLException e) {
                System.out.println("Тэга с указанным id не существует! Повторите ввод.");

            }
        }

        System.out.println("===========================");
        System.out.println("Тэг, который будет изменён:");
        System.out.println("===========================================================");
        System.out.println(getLabel.toString());
        System.out.println("===========================================================");
        System.out.println();
        System.out.println("=========================================");
        System.out.println("Введите новые данные для выбранного тэга:");
        System.out.println("=========================================");
        System.out.println();

        do {
            System.out.println("Введите новое название тэга: ");
            nameLabel = scanner.next();
            if (nameLabel.equals("")) {
                System.out.println();
                System.out.println("==============================================================");
                System.out.println("WARNING! Название тэга не может быть пустым, повторите попытку");
                System.out.println("==============================================================");
                System.out.println();
            }
        } while (nameLabel.equals(""));

        Label editLabel = labelController.onUpdate(id, nameLabel);

        System.out.println("=======================================");
        System.out.println("Тэг с id = " + id + " был изменён:");
        System.out.println("========================================================");
        System.out.println(editLabel.toString());
        System.out.println("========================================================");

    }

    public void eventDeleteLabelById() {
        Long id = 0L;

        System.out.println("=========================================");
        System.out.println("Введите id тэга, который хотите удалить: ");
        System.out.println("=========================================");

        id = scanner.nextLong();

        boolean deleteLabel = labelController.onDelete(id);

        if (deleteLabel) {
            System.out.println("======================================");
            System.out.println("Тэг с id = " + id + " был удален!");
            System.out.println("======================================");
            System.out.println('\n');
        } else {
            System.out.println('\n');
            System.out.println("=============================================================================================================");
            System.out.println("WARNING! Тэга с указанным id не существует! Повторите попытку");
            System.out.println("=============================================================================================================");
            System.out.println('\n');
            show();
        }
    }
}
