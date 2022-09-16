package example.com.crud.view;

import java.util.Scanner;

public class BaseView {
    private final Scanner scanner = new Scanner(System.in);
    private final WriterView writerView;
    private final PostView postView;
    private final LabelView labelView;


    public BaseView() {
        writerView = new WriterView();
        postView = new PostView();
        labelView = new LabelView();
    }

    public void show() {
        int select;

        while (true) {
            System.out.println("===========================================");
            System.out.println("ГЛАВНОЕ МЕНЮ:");
            System.out.println("===========================================");
            System.out.println("1. Меню управления писателями");
            System.out.println("2. Меню управления статьями");
            System.out.println("3. Меню управления тэгами");
            System.out.println("4. Выход");

            System.out.println("===========================================");
            System.out.println("Выберите пункт меню:");
            System.out.println("===========================================");
            select = scanner.nextInt();
            System.out.println("\n");

            switch (select) {
                case 1:
                    writerView.show();
                    break;

                case 2:
                    postView.show();
                    break;

                case 3:
                    labelView.show();
                    break;

                case 4:
                    System.out.println("********************Работа приложения завершена!********************");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Неправильно выбран пункт! Пожалуйста, повторите ввод");
                    break;
            }

        }
    }
}
